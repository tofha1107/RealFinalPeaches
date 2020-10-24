package com.charlezz.cameraxdemo

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.graphics.*
import android.media.Image
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.util.Base64
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import androidx.appcompat.widget.AppCompatButton
import androidx.camera.core.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import kotlinx.android.synthetic.main.activity_main.*

var count = 0;
var blink = 0;
//var intent = Intent(this, MyService::class.java)

class MainService : LifecycleService(){
    private lateinit var viewFinder: TextureView

    val TAG = "MyService"

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        TODO("Return the communication channel to the service.")
        return null
    }

    override  fun onCreate(){

        ShowLog("onCreate")
        super.onCreate()
        startCameraService()
    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : {
//        ShowLog("onStartCommand")
//
//        for(i in 1..10){
//            ShowLog("Service doing something." + i.toString())
//            Thread.sleep(1000)
//        }
//        return super.onStartCommand(intent, flags, startId)
//    }

    override fun onDestroy(){
        ShowLog("onDestroy")
        super.onDestroy()
    }

    fun ShowLog(message : String){
        Log.d(TAG, message)
    }

    private fun startCameraService() {

        //이미지 프로세싱 설정 시작
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.FRONT)
            // 이미지 분석을 위한 쓰레드를 하나 생성합니다.
            val analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            // 하나도 빠짐없이 프레임 전부를 분석하기보다는 매순간 가장 최근 프레임만을 가져와 분석하도록 합니다
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
        }.build()

        // 커스텀 이미지 프로세싱 객체 생성
        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = MainService.LuminosityAnalyzer()
        }
        //이미지 프로세싱 설정 끝
        CameraX.unbindAll()
        //유즈케이스들을 바인딩함
        CameraX.bindToLifecycle(this, analyzerUseCase)

        timeResetClasss.blink_static_cnt = blink;
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        viewFinder.setTransform(matrix)
    }

    private class LuminosityAnalyzer : ImageAnalysis.Analyzer {
        private var lastAnalyzedTimestamp = 0L

        /**
         * 이미지 버퍼를 바이트 배열로 추출하기 위한 익스텐션
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // 버퍼의 포지션을 0으로 되돌림
            val data = ByteArray(remaining())
            get(data)   // 바이트 버퍼를 바이트 배열로 복사함
            return data // 바이트 배열 반환함
        }

        private fun Image.toBitmap(): Bitmap {
            val yBuffer = planes[0].buffer // Y
            val uBuffer = planes[1].buffer // U
            val vBuffer = planes[2].buffer // V

            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()

            val nv21 = ByteArray(ySize + uSize + vSize)

            //U and V are swapped
            yBuffer.get(nv21, 0, ySize)
            vBuffer.get(nv21, ySize, vSize)
            uBuffer.get(nv21, ySize + vSize, uSize)

            val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
            val imageBytes = out.toByteArray()
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }

        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            val currentTimestamp = System.currentTimeMillis()
            // 매프레임을 계산하진 않고 1초마다 한번씩 정도 계산
            // if (currentTimestamp - lastAnalyzedTimestamp >= TimeUnit.SECONDS.toMillis(1)) {
            if (currentTimestamp - lastAnalyzedTimestamp >= 500) {
                Log.d("hhd", "hhd")
                var imgFormat = image.format
                Log.d("CameraXApp", "imgFormat: $imgFormat")

                var imgBitmap: Bitmap = image.image!!.toBitmap()

                var out: ByteArrayOutputStream = ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG,90,out)
                var b:ByteArray = out.toByteArray()
                var imageEncoded:String = Base64.encodeToString(b, Base64.DEFAULT)

                val queue = Volley.newRequestQueue(MainActivity.context())
                val url = "http://172.30.1.22:9000/re"

                // Request a string response from the provided URL.
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        // Display the first 500 characters of the response string
                        val jsonStr = "{\"s\":0}"

                        try
                        {
                            obj = JSONObject(response)
//                            Log.d("CameraXApp","json출력완료")

                            //blink 값이 1일때 count 하기
//                            Log.d("CameraXApp","텍스트뷰출력완료")
                            val blink1 = obj.getString("blink")

                            if(blink1.equals("1")) {
                                blink += 1;
                            }

                           // blink +=1;
                            Log.d("CameraXApp","실행부터 "+blink+"회 누적")

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        Log.d("CameraXApp", "Response is: ${response}")
                    },
                    Response.ErrorListener { Log.d("CameraXApp", "That didn't work!") })
                {
                    @Throws(AuthFailureError::class)
                    override fun getParams() : Map<String,String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["img"] = imageEncoded
                        return params
                    }
                }


                // Add the request to the RequestQueue.
                queue.add(stringRequest)


                Log.d("CameraXApp", "height: ${imgBitmap.height}")
                Log.d("CameraXApp", "width: ${imgBitmap.width}")

                // 이미지 포맷이 YUV이므로 image.planes[0]으로 Y값을 구할수 있다.
                val buffer = image.planes[0].buffer
                // 이미지 데이터를 바이트배열로 추출
                val data:ByteArray = buffer.toByteArray()
                // 픽셀 하나하나를 유의미한 데이터리스트로 만든다
                val pixels:List<Int> = data.map { it.toInt() and 0xFF }
                // 이미지의 평균 휘도를 구한다
                val luma:Double = pixels.average()
                // 로그에 휘도 출력
                Log.d("CameraXApp", "Average luminosity: $luma")
                // 마지막 분석한 프레임의 타임스탬프로 업데이트한다.
                lastAnalyzedTimestamp = currentTimestamp
            }
        }
    }


}

