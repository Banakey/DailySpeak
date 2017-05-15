package com.software.upc.fluency.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.software.upc.fluency.MyApplication;
import com.software.upc.fluency.R;
import com.software.upc.fluency.adapter.StudyAdapter;
import com.software.upc.fluency.model.StudyItem;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class AddPlanActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button addPlan;
    private List<StudyItem> studyItems;
    private MyApplication app;
    private String spName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        spinner = (Spinner)findViewById(R.id.spinner_study);
        addPlan = (Button)findViewById(R.id.add_parter);
        /*
        * 查询可加入课程
        * */
        BmobQuery<StudyItem> query = new BmobQuery<StudyItem>();
        query.order("-createdAt");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        //query.addWhereEqualTo("studyTheme",spName);
        //query.setLimit(50);
        //执行查询方法
        query.findObjects(AddPlanActivity.this,new FindListener<StudyItem>() {
            @Override
            public void onSuccess(List<StudyItem> list) {
                studyItems = list;
                Toast.makeText(AddPlanActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                System.out.println("添加数据成功");
                //创建自定义Adapter的对象
                StudyAdapter adapter = new StudyAdapter(AddPlanActivity.this,studyItems);
                //将布局添加到spinner中
                spinner.setAdapter(adapter);
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(AddPlanActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                System.out.println("添加数据失败");
            }
        });

        //加入课程按钮
//        addPlan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String un = app.getName();
//                String studyTheme = spinner.getSelectedItem().toString();
//                Plan plan = new Plan();
//                plan.setUserName(un);
//                plan.setStudyTheme(studyTheme);
//                plan.save(AddPlanActivity.this,new SaveListener(){
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(AddPlanActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        Toast.makeText(AddPlanActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }
}
