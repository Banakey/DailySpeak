package com.software.upc.fluency.model;

import java.io.File;

/**
 * Created by jmy on 2017/4/20.
 */

public class Plan {

    private String userName;

    private String studyTheme;//题目
    private String studyText;//正文
    private File studyFile;//视频

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public File getStudyFile() {
        return studyFile;
    }

    public void setStudyFile(File studyFile) {
        this.studyFile = studyFile;
    }
}
