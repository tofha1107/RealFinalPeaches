package com.charlezz.cameraxdemo;

public class MemberDTO {
    private String name;
    private String pw;
    private String gender;
    private String birth;
    private String email;

    public MemberDTO(String name, String pw, String gender, String birth, String email) {
        this.name = name;
        this.pw = pw;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
