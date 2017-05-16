package com.software.upc.fluency.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_username;
    private EditText et_password;
    private Button login;
    private Button go_register;
    private CheckBox checkBox;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        Bmob.initialize(this, "bbd2e91e683828d227b28a9ef69683e9");
        setContentView(R.layout.activity_login);
        initialize();
        sp = getSharedPreferences("userInfo", 0);
        String name=sp.getString("USER_NAME", "");
        String pass =sp.getString("PASSWORD", "");


        boolean choseRemember =sp.getBoolean("remember", false);

        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            et_username.setText(name);
            et_password.setText(pass);
            checkBox.setChecked(true);
        }
    }

    private void initialize(){
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        checkBox = (CheckBox) findViewById(R.id.cb_auto);
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
                final String password = et_password.getText().toString();
                final SharedPreferences.Editor editor = sp.edit();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    User user = new User();
                    user.setUsername(username);
                    Log.e("username",username);
                    user.setPassword(password);
                    Log.e("password",password);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if(e==null){
                                //存储当前用户的信息到本地
                                editor.putString("USER_NAME", username);
                                editor.putString("PASSWORD", password);
                                if(checkBox.isChecked()){
                                    editor.putBoolean("remember", true);
                                }else{
                                    editor.putBoolean("remember", false);
                                }
                                editor.commit();

                                Intent intent = new Intent(LoginActivity.this,PlanActivity.class);
                                //intent.putExtra("user",user);
                                startActivity(intent);
                                //登录成功

                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                            }else{
                                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                //Log.e("登录失败"+e.getMessage());
                            }
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
