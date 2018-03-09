package com.example.dhrumil.healthcare.dataBase;

/**
 * Created by Nilesh on 2/22/2018.
 */

public class User {

    private int id;
    private String mobile;
    private String email;
    private String password;
    private String name;
    private String dob;
    private int age;
    private int height;
    private int weight;
    private String addr;
    private String reg_id;
    private String caddr;
    private String cname;
    private String pra;
    private String higher;
    private String usertype;
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile = mobile;
    }

    public String getEmail(){
        return email;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getBirthDate(){return dob;}

    public void setBirthDate(String dob){this.dob = dob;}

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public String getAddr(){
        return addr;
    }

    public void setAddr(String addr){
        this.addr = addr;
    }

    public String getRegistrationId(){return reg_id;}

    public void setRegistrationId(String reg_id){this.reg_id = reg_id;}

    public String getClinicAddr(){
        return caddr;
    }

    public void setClinicAddr(String caddr){
        this.caddr = caddr;
    }

    public String getClinicName(){
        return cname;
    }

    public void setClinicName(String cname){
        this.cname = cname;
    }

    public String getHigher(){
        return higher;
    }

    public void setHigher(String higher){
        this.higher = higher;
    }

    public String getPractice(){
        return pra;
    }

    public void setpractice(String pra){
        this.pra = pra;
    }

    public void setusertype(String usertype){ this.usertype = usertype;}

    public String getusertype(){
        return usertype;
    }
}
