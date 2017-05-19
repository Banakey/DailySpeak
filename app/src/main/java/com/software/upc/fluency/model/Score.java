package com.software.upc.fluency.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by jmy on 2017/5/19.
 */

public class Score extends BmobObject {
    //用户名
    private String userName;
    //分数
    private int score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
