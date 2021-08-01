package com.melon.unity.function.password;

public class Password {
    private int id;
    private String title;
    private String username;
    private String pwd;
    private String remark;

    public Password(int id, String title, String username, String pwd, String remark) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.pwd = pwd;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
