package com.software.upc.fluency.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.adapter.ExamAdapter;
import com.software.upc.fluency.model.Exam;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TestActivity extends AppCompatActivity {

    private List<Exam> mExam;
    private ListView examList;
    private String questionName;
    private ExamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_test);
        examList = (ListView) findViewById(R.id.list_exam);

        BmobQuery<Exam> query = new BmobQuery<Exam>();
        query.order("-createdAt");
        //执行查询方法
        query.findObjects(new FindListener<Exam>() {
            @Override
            public void done(List<Exam> list, BmobException e) {
                if(e==null){
                    for (Exam exam : list) {
                        exam.getQuestionName();
                    }
                    //创建自定义Adapter的对象
                    adapter = new ExamAdapter(TestActivity.this,list);
                    mExam = list;
                    //将布局添加到ListView中
                    examList.setAdapter(adapter);
                    Toast.makeText(TestActivity.this, "加载成功", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(TestActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        examList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO mp3播放
                Exam exam = mExam.get(position);
//                String examname = exam.getQuestionName();
                Bundle bundle = new Bundle();
                bundle.putSerializable("exam", exam);
                Intent intent = new Intent(TestActivity.this,QuestionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
