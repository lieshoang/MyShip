package com.example.huuph.myship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private EditText edLoginUser;
    private EditText edLoginPass;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    String email, name, id_facebook; //mail va name facebook

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        //xin quyền facebook
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        setLogin_Button();
    }

    private void Anhxa() {
        Log.d("anhxa","anhxathanhcong");
        edLoginUser = (EditText) findViewById(R.id.edLoginUser);
        edLoginPass = (EditText) findViewById(R.id.edLoginPass);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_facebook);
        //đăng nhập lại mỗi khi vào ứng dụng
        onStart();
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //TODO đăng nhập fb thành công
                result();//lấy thông tin người dùng về

            }

            @Override
            public void onCancel() {
                //TODO Cancel fb
            }

            @Override
            public void onError(FacebookException error) {
                //TODO lỗi
            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                //xuất ra log thông tin id, name, mail khi đăng nhập thành công
                Log.d("JSON", response.getJSONObject().toString());
                //thấy thông tin
                try {
                    email = object.getString("email");
                    name = object.getString("name");
                    id_facebook = object.getString("id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();

    }

    public void onclickLogin(View view) {
        //TODO gửi lên firrebase và check acc (Phước)
    }


    public void onClickSignUp(View view) {
        //TODO Chuyển sang activity đăng kí
        Intent intent = new Intent(this,Signup.class);
        startActivity(intent);
    }

    public void onClickFogotpass(View view) {
        //TODO Chuyển sang layout quên mật khẩu
    }
}
