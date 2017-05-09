package com.software.upc.fluency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_username;
    private EditText et_password;
    private Button login;
    private Button go_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //Bmob.initialize(this, "bbd2e91e683828d227b28a9ef69683e9");
        setContentView(R.layout.activity_login);
        initialize();
    }

    private void initialize(){
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        //et_password.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        login = (Button) findViewById(R.id.bt_login);
        login.setOnClickListener(this);
        go_register = (Button) findViewById(R.id.bt_go_regist);
        go_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                final String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    final BmobUser bmobUser = new BmobUser();
                    bmobUser.setUsername(username);
                    bmobUser.setPassword(password);

                    bmobUser.login(LoginActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            //获取到当前用户的信息
                            User user = BmobUser.getCurrentUser(LoginActivity.this,User.class);
                            Intent intent = new Intent(LoginActivity.this,PlanActivity.class);
                            intent.putExtra("user",user);
                            startActivity(intent);
                            //登录成功
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            //Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "报错了" + s.toString());
                        }
                    });

                }else{
                }
                break;
            case R.id.bt_go_regist:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }


}
