package com.software.upc.fluency.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by jmy on 2017/5/18.
 */

public class Question extends BmobObject{
    /**
     * 解析JsonArray，创建题目对象
     */
    public static List<Question> createFromCursor(JSONArray array) {
        if (array.length() > 0 && array != null) {
            List<Question> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                Question question = new Question();
                JSONObject object = null;
                try {
                    object = (JSONObject) array.get(i);
                    question.setQuestionName(object.getString("questionName"));
//                    question.setAnswer(object.getString("answer"));
                    question.setObjectId(object.getString("objectId"));
                    list.add(question);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return list;
        }
        return null;
    }
    private int questionNum;

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    //试题题目
    private String questionName;
    //题目答案
    private String answer;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
