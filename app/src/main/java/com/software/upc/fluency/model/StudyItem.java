package com.software.upc.fluency.model;

import java.io.File;

import cn.bmob.v3.BmobObject;

/**
 * Created by jmy on 2017/4/20.
 */

public class StudyItem extends BmobObject{

    //private int studyImage;//图片
    private String studyTheme;//题目
    private String studyText;//正文
    private File studyFile;//视频

    /*
    定义构造器，创建对象时定义学习项的信息。
     */
    public StudyItem(String theme, String text){
        this.studyTheme = theme;
        this.studyText = text;
    }

    public File getStudyFile() {
        return studyFile;
    }

    public void setStudyFile(File studyFile) {
        this.studyFile = studyFile;
    }

    public String getStudyTheme() {
        return studyTheme;
    }

    public void setStudyTheme(String studyTheme) {
        this.studyTheme = studyTheme;
    }

    public String getStudyText() {
        return studyText;
    }

    public void setStudyText(String studyText) {
        this.studyText = studyText;
    }
}
