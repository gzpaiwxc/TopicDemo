package com.topicdemo.entity;

/**
 * Created by THL on 2017/4/15.
 */

public class OptionTab {
    /**选项*/
    public String option;
    /**该选项是否点击*/
    public boolean isClick;

    @Override
    public String toString() {
        return "OptionTab{" +
                "option='" + option + '\'' +
                ", isClick=" + isClick +
                '}';
    }
}
