package com.topicdemo.entity;

import java.io.Serializable;

/**
 * Created by THL on 2017/4/12.
 */

public class AnswerItem implements Serializable {
    private String answerItem;
    private boolean isRight;
    private boolean isUserClick;

    public boolean isUserClick() {
        return isUserClick;
    }

    public void setUserClick(boolean userClick) {
        isUserClick = userClick;
    }

    public String getAnswerItem() {
        return answerItem;
    }

    public void setAnswerItem(String answerItem) {
        this.answerItem = answerItem;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
