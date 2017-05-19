package com.software.upc.fluency.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.software.upc.fluency.R;
import com.software.upc.fluency.model.Exam;
import com.software.upc.fluency.model.Grade;
import com.software.upc.fluency.model.Question;
import com.software.upc.fluency.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView qutionTitle;
    private EditText answer;
    private ImageButton preQution;
    private ImageButton nxtQution;
    private TextView qutionNum;
    private List<Question> questions;
    //传递的exam对象
    private Exam exam;
    //题库名
    private String qName;
    //题目数
    private int qNum;
    //提交按钮
    private Button btnCommit;
    private String qutionAnswer;
    //记录错题数
    private int errorNum[] = new int[50];
    //分数
    private int score = 0;

    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        preQution = (ImageButton) findViewById(R.id.btn_pre);
        preQution.setOnClickListener(this);
        nxtQution = (ImageButton) findViewById(R.id.btn_next);
        nxtQution.setOnClickListener(this);
        qutionNum = (TextView) findViewById(R.id.tv_question_num);
        qutionTitle = (TextView) findViewById(R.id.question_name);
        answer = (EditText) findViewById(R.id.et_answer);
        btnCommit = (Button)findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);

        Bundle b=getIntent().getExtras();
        exam =(Exam) b.getSerializable("exam");
        qName = exam.getQuestionName();
        qNum = exam.getExamLimit();
        for(int i = 0;i < qNum;i++){
            errorNum[i] = 1;
        }
        Log.e("bmob",qName+"         "+qNum);
        BmobQuery query = new BmobQuery(qName);
//        BmobQuery<Question> query = new BmobQuery<Question>();
//        query.findObjectsByTable(qu)
        //设置缓存
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if(e == null){
                    for(Question question:list) {
                    }
                    qutionNum.setText("1/" + qNum);
                    questions = list;
                    startTest(count);
                    Log.e("bmob","----------------"+count);
                } else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

    }
    private void startTest(int position) {
        Question question = questions.get(position);
        qutionTitle.setText(question.getQuestionName());
        qutionAnswer = question.getAnswer();
    }

    private void goNext(){
        if (count < questions.size() - 1) {
            count++;
            startTest(count);
            //初始化多选数组，可优化
        } else if (count == questions.size() - 1) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("答题完成");
            builder.setMessage("确定要上交题目吗？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    endTest();
                }
            });
            builder.show();
        }
        //题目计数器
        qutionNum.setText((count + 1) + "/" + qNum);
    }
    private void goPrevious() {
        if (count > 0) {
            count--;
            startTest(count);
        }
        //题目计数器
        qutionNum.setText((count + 1) + "/" + qNum);
    }

    private void endTest() {
        //计算分数
       for(int i = 0 ;i < qNum; i++){
           if(errorNum[i] == 0){
               score = score+100;
           }
       }
       Log.e("bmob","-----------------"+score);
        //提交数据
        User user = BmobUser.getCurrentUser(User.class);
        final String username = user.getUsername();
        BmobQuery<Grade> query= new BmobQuery<Grade>();
        query.addWhereEqualTo("userName",username);
        query.findObjects(new FindListener<Grade>() {
            @Override
            public void done(List<Grade> list, BmobException e) {
                if(e==null) {
                    for (Grade grade : list) {
                        grade.setScore(score);
                        String id = grade.getObjectId();
                        grade.update(id, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Log.i("bmob", "更新成功");
                                    Intent intent = new Intent(QuestionActivity.this,PlanActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(QuestionActivity.this,"不存在用户",Toast.LENGTH_SHORT).show();
                    Log.e("bmob",e.getMessage()+"----------"+e.getErrorCode());
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                    Grade gradeNew = new Grade();
//                    gradeNew.setUserName(username);
//                    gradeNew.setScore(score);
//                    gradeNew.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String s, BmobException e) {
//                            if(e==null){
//                                Toast.makeText(QuestionActivity.this,"不存在用户",Toast.LENGTH_SHORT).show();
//                            }else{
//                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
//                            }
//                        }
//                    });
                }
//                Toast.makeText(QuestionActivity.this,"找到用户",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                goPrevious();
                break;
            case R.id.btn_next:
                goNext();
                break;
            case R.id.btn_commit:
                String qAnswer = answer.getText().toString();
                if(qAnswer.equals(qutionAnswer)){
                    errorNum[count] = 0;
                    Toast.makeText(QuestionActivity.this,"答案正确",Toast.LENGTH_SHORT).show();
                } else {
                    errorNum[count] = 1;
                    Toast.makeText(QuestionActivity.this,"答案错误",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
