package com.charlezz.cameraxdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class JoinPage extends AppCompatActivity implements View.OnClickListener  {

    private Button join_button2, cancel_button;
    private EditText edt_name, edt_pw, edt_birth, edt_email;
    private RadioGroup rg;

    private RequestQueue queue;
    private String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_page);

        join_button2 = (Button)findViewById(R.id.join_button2);
        cancel_button = (Button)findViewById(R.id.cancel_button);

        join_button2.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_pw = (EditText)findViewById(R.id.edt_pw);
        edt_birth = (EditText)findViewById(R.id.edt_birth);
        edt_email = (EditText)findViewById(R.id.edt_email);

        rg = (RadioGroup)findViewById(R.id.radio_gender);

        queue = Volley.newRequestQueue(getApplicationContext());

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId() == R.id.join_button2){
            RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            gender = rd.getText().toString();
            boolean isCheck = edt_name.length() > 0 && gender.length() > 0 && edt_pw.length() > 0 && edt_birth.length() > 0 && edt_email.length() > 0;
            if(isCheck) {
                sendRequest();
                intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"빈틈없이 채워주십쇼",Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId() == R.id.cancel_button){
            intent = new Intent(getApplicationContext(), LoginPage.class);
            startActivity(intent);
        }

    }


    public void sendRequest(){
        String url = "http://172.30.1.41:8081/Project/JoinService";
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {  //응답을 문자열로 받아서 여기다 넣어달란말임(응답을 성공적으로 받았을 떄 이메소드가 자동으로 호출됨
                    @Override
                    public void onResponse(String response) {
                        Log.v("myValue",response);
                    }
                },
                new Response.ErrorListener(){ //에러발생시 호출될 리스너 객체
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("myError", error.getMessage());
                    }
                }
        ){
            //만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            //이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            //POST방식으로 안할거면 없어도 되는거같다.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name",edt_name.getText().toString());
                params.put("pw", edt_pw.getText().toString());
                params.put("gender",gender);
                params.put("birth", edt_birth.getText().toString());
                params.put("email", edt_email.getText().toString());

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