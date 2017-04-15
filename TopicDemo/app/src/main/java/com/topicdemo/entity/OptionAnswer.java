package com.topicdemo.entity;

/**
 * 所选择的的答案
 * Created by THL on 2017/4/14.
 */

public class OptionAnswer {
    private String numOption;
    private String optionAnswer;
    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getOptionAnswer() {
        return optionAnswer;
    }

    public void setOptionAnswer(String optionAnswer) {
        this.optionAnswer = optionAnswer;
    }

    public String getNumOption() {
        return numOption;
    }

    public void setNumOption(String numOption) {
        this.numOption = numOption;
    }



    @Override
    public String toString() {
        return "OptionAnswer{" +
                "numOption='" + numOption + '\'' +
                ", optionAnswer='" + optionAnswer + '\'' +
                ", isClick=" + isClick +
                '}';
    }
}
