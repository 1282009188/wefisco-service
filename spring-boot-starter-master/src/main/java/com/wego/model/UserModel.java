package com.wego.model;

/**
 * @author elizayuan
 * 用于显示用户首页信息
 */
public class UserModel {
    //用户等级
    private int level;
    //企鹅卡路里
    private int col;
    //健康豆
    private int bean;
    //企鹅名字
    private String petname;
    //企鹅皮肤
    private String url;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
