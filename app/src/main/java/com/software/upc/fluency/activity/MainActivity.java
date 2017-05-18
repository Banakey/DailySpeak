package com.software.upc.fluency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private View view;
    private EditText et_username;
    private EditText et_password;
    private EditText et_email;
    //private EditText et_icon;
    private Button register;
    private Button go_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize(){
        et_email = (EditText) findViewById(R.id.ed_email);
        et_username = (EditText) findViewById(R.id.ed_regist_username);
        et_password = (EditText) findViewById(R.id.ed_regist_password);

        register = (Button) findViewById(R.id.bt_register);
        register.setOnClickListener(this);
        go_login = (Button) findViewById(R.id.bt_go_login);
        go_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_register:
                registerData();
                break;
            case R.id.bt_go_login:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 注册
     */
    private void registerData() {

        final String name = et_username.getText().toString().trim();
        final String password = et_password.getText().toString();
        final String email = et_email.getText().toString();

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                    //Log.e("注册失败"+e.getMessage());
                }
            }


        });
    }
}
