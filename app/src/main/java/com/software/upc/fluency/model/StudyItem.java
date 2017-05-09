package com.software.upc.fluency.model;

/**
 * Created by jmy on 2017/4/20.
 */

public class StudyItem {

    //private int studyImage;//图片
    private String studyTheme;//题目
    private String studyText;//正文
    //private File studyFile;//视频

    /*
    定义构造器，创建对象时定义学习项的信息。
     */
    public StudyItem(String theme, String text,int imag){
        this.studyTheme = theme;
        this.studyText = text;
        //this.studyImage = imag;
    }

//    public int getStudyImage() {
//        return studyImage;
//    }
//
//    public void setStudyImage(int studyImage) {
//        this.studyImage = studyImage;
//    }

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
