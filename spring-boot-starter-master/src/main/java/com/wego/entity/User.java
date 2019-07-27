package com.wego.entity;

public class User {
    private Integer uid;

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", bean=" + bean +
                ", addr='" + addr + '\'' +
                ", sk='" + sk + '\'' +
                ", pk='" + pk + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                '}';
    }

    private String name;

    private String account;

    private String pwd;

    private Integer bean;

    private String addr;

    private String sk;

    private String pk;

    private String email;

    private Integer level;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getBean() {
        return bean;
    }

    public void setBean(Integer bean) {
        this.bean = bean;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

}