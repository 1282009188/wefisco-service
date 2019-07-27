package com.wego.entity;

public class ShowFood {
    private Integer fid;

    private Integer num;
    private String fname;

    private Integer bean;

    private Integer col;

    private String url;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getBean() {
        return bean;
    }

    public void setBean(Integer bean) {
        this.bean = bean;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}