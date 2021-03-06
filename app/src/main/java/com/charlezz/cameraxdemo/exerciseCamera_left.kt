package com.charlezz.cameraxdemo

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Base64
import android.util.Log
import android.util.Rational
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
var left_cnt = 0;
var right_cnt = 0;
var blink_cnt = 0;
var center_cnt = 0;

class exerciseCamera_left : AppCompatActivity() {

    private lateinit var viewFinder: TextureView

    init {
        instance = this
    }

    companion object {
        private var instance: exerciseCamera_left? = null

        fun context(): Context {
            return instance!!.applicationContext
        }
    }

    private fun startCamera() {
        //미리보기 설정 시작
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setTargetResolution(Size(1280, 720))
            setLensFacing(CameraX.LensFacing.FRONT)
            //setTargetRotation(Surface.ROTATION_270);

        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }
        //미리보기 설정 끝

        //사진찍기 설정 시작
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setTargetAspectRatio(Rational(1, 1))
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                setLensFacing(CameraX.LensFacing.FRONT)
                //setTargetRotation(Surface.ROTATION_270);
            }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)
        findViewById<ImageButton>(R.id.capture_button).setOnClickListener {
            val file = File(
                externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg"
            )
            imageCapture.takePicture(file,
                object : ImageCapture.OnImageSavedListener {
                    override fun onError(
                        error: ImageCapture.UseCaseError,
                        message: String, exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        Log.e("CameraXApp", msg)
                        exc?.printStackTrace()
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "사진 경로 : ${file.absolutePath}"
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        Log.d("CameraXApp", msg)
                    }
                })
        }
        //사진찍기 설정 끝

        //이미지 프로세싱 설정 시작
        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.FRONT)
            //setTargetRotation(Surface.ROTATION_270);
            // 이미지 분석을 위한 쓰레드를 하나 생성합니다.
            val analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            // 하나도 빠짐없이 프레임 전부를 분석하기보다는 매순간 가장 최근 프레임만을 가져와 분석하도록 합니다
            setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)

        }.build()

        // 커스텀 이미지 프로세싱 객체 생성
        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = LuminosityAnalyzer()

        }
        //이미지 프로세싱 설정 끝

        //유즈케이스들을 바인딩함
        CameraX.bindToLifecycle(this, preview, imageCapture, analyzerUseCase)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        viewFinder.setTransform(matrix)
    }


    var xmlTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.eyes_layout)
        val textView: TextView = TextView(this)
        viewFinder = findViewById(R.id.view_finder)
        xmlTextView = findViewById(R.id.title_btn)

//        val nextIntent = Intent(this, exerciseCamera::class.java)
//        startActivity(nextIntent)

        if (allPermissionsGranted()) {

            CameraX.unbindAll()
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(
                    this,
                    "권한이 허용되지 않았습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this, permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
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
            if (currentTimestamp - lastAnalyzedTimestamp >= 1500) {
                var imgFormat = image.format
                Log.d("CameraXApp", "imgFormat: $imgFormat")

                var imgBitmap: Bitmap = image.image!!.toBitmap()

                var out: ByteArrayOutputStream = ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                var b: ByteArray = out.toByteArray()
                var imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)

                val queue = Volley.newRequestQueue(exerciseCamera_left.context())
                val url = "http://172.30.1.15:9000/re"

                // Request a string response from the provided URL.
                val stringRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        // Display the first 500 characters of the response string.
                        val jsonStr = "{\"s\":0}"

                        try {
                            val obj = JSONObject(response)
                            val gaze_state = obj.getString("gaze_state")

//                            Log.d("CameraXApp", "겟스트링 완료")
                            Log.d("CameraXApp", "gaze_state: $gaze_state")
//                            Log.d("CameraXApp", "json출력완료")


                            if(left_cnt <= 3) {
                                var cnt = looking_left(gaze_state);
                            }else{
                                looking_right(gaze_state);
                            }

                            blinking(gaze_state);
//                            if (gaze_state.equals("Looking right")) {
//                                right_cnt += 1;
//                                Log.d("CameraXApp", " " + right_cnt);
//                                Log.d("CameraXApp", "Right 출력완료")
//
//                                if (right_cnt >= 3) {
//                                    exerciseCamera_left.instance?.xmlTextView?.setText("눈을 10번 깜빡이세요")
//                                }
//                            }

//                            if (gaze_state.equals("Looking center")) {
//
//                                center_cnt += 1;
//                                Log.d("CameraXApp", " " + center_cnt);
//                                Log.d("CameraXApp", "center_cnt 출력완료")
//                                if (center_cnt >= 3) {
//
//                                    val nextIntent = Intent(exerciseCamera_left.context(), ExecutionPage::class.java)
//                                    nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                    startActivity(exerciseCamera_left.context(), nextIntent, null)
////
////                                    val intent = Intent(exerciseCamera_left.context(), ExecutionPage::class.java)
////                                    val pendingIntent:PendingIntent = PendingIntent.getActivity(exerciseCamera_left.context(), 0, intent, 0);
////                                    pendingIntent.send();
//                                    System.exit(0)
//                                }
//
//
//                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        Log.d("CameraXApp", "Response is: ${response}")
                    },
                    Response.ErrorListener { Log.d("CameraXApp", "That didn't work!") }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()
                        params["img"] = imageEncoded
                        return params
                    }
                }


                // Add the request to the RequestQueue.
                queue.add(stringRequest)


//                Log.d("CameraXApp", "height: ${imgBitmap.height}")
//                Log.d("CameraXApp", "width: ${imgBitmap.width}")

                // 이미지 포맷이 YUV이므로 image.planes[0]으로 Y값을 구할수 있다.
                val buffer = image.planes[0].buffer
                // 이미지 데이터를 바이트배열로 추출
                val data: ByteArray = buffer.toByteArray()
                // 픽셀 하나하나를 유의미한 데이터리스트로 만든다
                val pixels: List<Int> = data.map { it.toInt() and 0xFF }
                // 이미지의 평균 휘도를 구한다
                val luma: Double = pixels.average()
                // 로그에 휘도 출력
//                Log.d("CameraXApp", "Average luminosity: $luma")
                // 마지막 분석한 프레임의 타임스탬프로 업데이트한다.
                lastAnalyzedTimestamp = currentTimestamp


            }
        }
        private  fun looking_left(gaze_state:String):Int{
            if (gaze_state.equals("Looking left")) {
                left_cnt += 1;
                Log.d("CameraXApp", " " + left_cnt);
                Log.d("CameraXApp", "Left 출력완료")

                if (left_cnt >= 3) {
                    exerciseCamera_left.instance?.xmlTextView?.setText("시선을 오른쪽으로 \n5초 동안 유지하세요")
                }
            }

            return left_cnt;
        }

        private fun looking_right(gaze_state: String){
            if (gaze_state.equals("Looking right")) {
                right_cnt += 1;
                Log.d("CameraXApp", " " + right_cnt);
                Log.d("CameraXApp", "Right 출력완료")

                if (right_cnt >= 3) {
                    exerciseCamera_left.instance?.xmlTextView?.setText("눈을 10번 깜빡이세요")
                }
            }
        }

        private fun blinking(gaze_state: String){
            if (gaze_state.equals("Looking center")) {
                blink_cnt += 1;
                Log.d("CameraXApp", " " + blink_cnt);
                Log.d("CameraXApp", "blinking 출력완료")

                if (blink_cnt >= 2) {
                    //exerciseCamera_left.instance?.xmlTextView?.setText("운동완료")
                    val nextIntent = Intent(exerciseCamera_left.context(), ExecutionPage::class.java)
                    nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    nextIntent.putExtra("cam", "1")
                    startActivity(exerciseCamera_left.context(), nextIntent, null)
                }
            }
        }



    }
}