package com.software.upc.fluency.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jmy on 2017/5/18.
 */

public class Exam extends BmobObject{
    //测试名称
    private String examName;
    //考试时间
    private int examTime;
    //所出自的题库名字
    private String questionName;
    //题目数
    private int examLimit;

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getExamTime() {
        return examTime;
    }

    public void setExamTime(int examTime) {
        this.examTime = examTime;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getExamLimit() {
        return examLimit;
    }

    public void setExamLimit(int examLimit) {
        this.examLimit = examLimit;
    }
}
