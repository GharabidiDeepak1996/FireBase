package com.example.firebasedatabasedemo;

public class Modelclass {
    private String Name;
    private String Email;
    private String Phone;
    private String UserName;
    private String Password;

public Modelclass(){

}
    public Modelclass(String name, String email, String phone, String userName, String password) {
        Name = name;
        Email = email;
        Phone = phone;
        UserName = userName;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
