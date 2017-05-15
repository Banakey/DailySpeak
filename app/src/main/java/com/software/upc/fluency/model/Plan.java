package com.software.upc.fluency.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jmy on 2017/4/20.
 */

public class Plan extends BmobObject {

    private String userName;

    private String studyTheme;//题目

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

}
