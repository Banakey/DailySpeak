package com.software.upc.fluency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.Plan;
import com.software.upc.fluency.model.StudyItem;
import com.software.upc.fluency.model.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class AddPlanActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button addPlan;
    private List<StudyItem> studyItems;
    private String spName;
    private Handler handler;
    private List<String> handleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        spinner = (Spinner)findViewById(R.id.spinner_study);
        addPlan = (Button)findViewById(R.id.add_parter);
        //handler
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 1:
                        handleList = (List<String>) msg.obj;
                        //String[] test = (String[]) handleList.toArray();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,handleList);
                        spinner.setAdapter(adapter);
                }
            }
        };
        /*
        * 查询可加入课程
        * */
        BmobQuery<StudyItem> query = new BmobQuery<StudyItem>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.addWhereEqualTo("studyTheme",spName);
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<StudyItem>() {
            @Override
            public void done(List<StudyItem> list, BmobException e) {
                if(e==null){
                    for (StudyItem studyItem : list) {
                        studyItem.getStudyTheme();
                    }

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        query.findObjects(new FindListener<StudyItem>() {
            @Override
            public void done(List<StudyItem> list, BmobException e) {
                if(e==null){
                    List<String> testList = new ArrayList<String>();
                    for (StudyItem studyItem : list) {
                        testList.add(studyItem.getStudyTheme());
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = testList;
                    handler.sendMessage(msg);
                    //创建自定义Adapter的对象

                    Toast.makeText(AddPlanActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddPlanActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        //加入课程按钮
        addPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String un = app.getName();
                //User user = BmobUser.getCurrentUser()
                User user = BmobUser.getCurrentUser(User.class);
                String username = user.getUsername();

                String studyTheme = spinner.getSelectedItem().toString();
                Log.e("studyTheme",studyTheme);
                Plan plan = new Plan();
                //plan.setUserName("admin");
                plan.setUserName(username);
                plan.setStudyTheme(studyTheme);
                plan.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null){
                            Toast.makeText(AddPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddPlanActivity.this, PlanActivity.class);
                            intent.putExtra("id",2);
                            startActivity(intent);
                        }else{
                            Toast.makeText(AddPlanActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });
    }
}
