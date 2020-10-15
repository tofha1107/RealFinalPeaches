package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity implements View.OnClickListener, Serializable {

    private Button join_button, login_button;
    private RequestQueue queue;
    private EditText edt_name,edt_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        join_button = (Button)findViewById(R.id.join_button);
        login_button = (Button)findViewById(R.id.login_button);

        join_button.setOnClickListener(this);
        login_button.setOnClickListener(this);

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_pw = (EditText)findViewById(R.id.edt_pw);


        queue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.join_button){
            Intent intent = new Intent(getApplicationContext(), JoinPage.class);
            startActivity(intent);
        }else if(v.getId() == R.id.login_button){
            sendRequest();

            final Handler delayHandler = new Handler();
            delayHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                   final Intent  intent = new Intent(LoginPage.this, JoinPage.class);
                    startActivity(intent);
                }
            }, 500);

        }

    }

    public void sendRequest(){
        String url = "http://172.30.1.45:8081/Project/LoginService";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 때 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        try {
                            if(!response.equals("nologin")) {
                                JSONObject jsonObject = new JSONObject(response);
                                String name = jsonObject.getString("name");
                                String pw = jsonObject.getString("pw");
                                String gender = jsonObject.getString("gender");
                                String birth = jsonObject.getString("birth");
                                String email = jsonObject.getString("email");

                                Log.v("login_info", name + "/" + pw + "/" + gender + "/" + birth + "/" + email);
                                LoginInfo.info = new MemberDTO(name, pw, gender, birth, email);
                            }else{
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였다.",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("myError", error.getMessage());
                    }
                }
        ){
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            //만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            //이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            //POST방식으로 안할거면 없어도 되는거같다.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name",edt_name.getText().toString());
                params.put("pw", edt_pw.getText().toString());

                return params;
            }
        };

        //아래 add코드처럼 넣어줄때 Volley라고하는게 내부에서 캐싱을 해준다, 즉, 한번 보내고 받은 응답결과가 있으면
        //그 다음에 보냈을 떄 이전 게 있으면 그냥 이전거를 보여줄수도  있다.
        //따라서 이렇게 하지말고 매번 받은 결과를 그대로 보여주기 위해 다음과같이 setShouldCache를 false로한다.
        //결과적으로 이전 결과가 있어도 새로 요청한 응답을 보여줌
        request.setShouldCache(false);
        queue.add(request);
    }


}