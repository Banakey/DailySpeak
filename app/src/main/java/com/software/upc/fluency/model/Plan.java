package com.software.upc.fluency.model;

import java.util.Date;

/**
 * Created by jmy on 2017/4/20.
 */

public class Plan {

    private String userName;

    private String planName;
    private Date planCreateTime;

    public String getPlanName() {
        return planName;
    }

    public Date getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setPlanCreateTime(Date planCreateTime) {
        this.planCreateTime = planCreateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
