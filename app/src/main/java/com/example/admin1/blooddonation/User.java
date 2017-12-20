package com.example.admin1.blooddonation;

/**
 * Created by admin1 on 18/12/17.
 */

public class User {
    public String email;
    public String pass;
    public String contact;
    public String name;
    public int age;
    public String bloodg;
    public double wt;
    public myLocation loc;

    public User()
    {

    }

    public myLocation getLoc() {
        return loc;
    }

    public void setLoc(myLocation loc) {
        this.loc = loc;
    }

    public User(String email, String pass, String name, int age, String bloodg, double wt, String num,myLocation loc)
    {
        setEmail(email);
        setPass(pass);
        setContact(num);
        setName(name);
        setAge(age);
        setBloodg(bloodg);
        setWt(wt);
        setLoc(loc);
    }

    public String getContact() { return contact; }

    public void setContact(String contact) { this.contact = contact; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBloodg(String bloodg) {
        this.bloodg = bloodg;
    }

    public void setWt(double wt) {
        this.wt = wt;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBloodg() {
        return bloodg;
    }

    public double getWt() {
        return wt;
    }
}
