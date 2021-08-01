package com.fandm.saad.hackerquiz.models;

import org.parceler.Parcel;

@Parcel
public class User {

    private String full_name;
    private String android_device_id;

    private int python_score_easy = 0;
    private int python_score_medium = 0;
    private int python_score_hard = 0;

    private int java_score_easy = 0;
    private int java_score_medium = 0;
    private int java_score_hard = 0;

    private int cpp_score_easy = 0;
    private int cpp_score_medium = 0;
    private int cpp_score_hard = 0;

    private int oop_score_easy = 0;
    private int oop_score_medium = 0;
    private int oop_score_hard = 0;

    public User(String full_name, String android_device_id, int python_score_easy, int python_score_medium, int python_score_hard, int java_score_easy, int java_score_medium, int java_score_hard, int cpp_score_easy, int cpp_score_medium, int cpp_score_hard, int oop_score_easy, int oop_score_medium, int oop_score_hard) {
        this.full_name = full_name;
        this.android_device_id = android_device_id;
        this.python_score_easy = python_score_easy;
        this.python_score_medium = python_score_medium;
        this.python_score_hard = python_score_hard;
        this.java_score_easy = java_score_easy;
        this.java_score_medium = java_score_medium;
        this.java_score_hard = java_score_hard;
        this.cpp_score_easy = cpp_score_easy;
        this.cpp_score_medium = cpp_score_medium;
        this.cpp_score_hard = cpp_score_hard;
        this.oop_score_easy = oop_score_easy;
        this.oop_score_medium = oop_score_medium;
        this.oop_score_hard = oop_score_hard;
    }

    public User(String full_name, String android_device_id) {
        this.full_name = full_name;
        this.android_device_id = android_device_id;
    }

    public User(){
    }

    public String getAndroid_device_id() {
        return android_device_id;
    }

    public void setAndroid_device_id(String android_device_id) {
        this.android_device_id = android_device_id;
    }

    public int getPython_score_easy() {
        return python_score_easy;
    }

    public void setPython_score_easy(int python_score_easy) {
        this.python_score_easy = python_score_easy;
    }

    public int getPython_score_medium() {
        return python_score_medium;
    }

    public void setPython_score_medium(int python_score_medium) {
        this.python_score_medium = python_score_medium;
    }

    public int getPython_score_hard() {
        return python_score_hard;
    }

    public void setPython_score_hard(int python_score_hard) {
        this.python_score_hard = python_score_hard;
    }

    public int getJava_score_easy() {
        return java_score_easy;
    }

    public void setJava_score_easy(int java_score_easy) {
        this.java_score_easy = java_score_easy;
    }

    public int getJava_score_medium() {
        return java_score_medium;
    }

    public void setJava_score_medium(int java_score_medium) {
        this.java_score_medium = java_score_medium;
    }

    public int getJava_score_hard() {
        return java_score_hard;
    }

    public void setJava_score_hard(int java_score_hard) {
        this.java_score_hard = java_score_hard;
    }

    public int getCpp_score_easy() {
        return cpp_score_easy;
    }

    public void setCpp_score_easy(int cpp_score_easy) {
        this.cpp_score_easy = cpp_score_easy;
    }

    public int getCpp_score_medium() {
        return cpp_score_medium;
    }

    public void setCpp_score_medium(int cpp_score_medium) {
        this.cpp_score_medium = cpp_score_medium;
    }

    public int getCpp_score_hard() {
        return cpp_score_hard;
    }

    public void setCpp_score_hard(int cpp_score_hard) {
        this.cpp_score_hard = cpp_score_hard;
    }

    public int getOop_score_easy() {
        return oop_score_easy;
    }

    public void setOop_score_easy(int oop_score_easy) {
        this.oop_score_easy = oop_score_easy;
    }

    public int getOop_score_medium() {
        return oop_score_medium;
    }

    public void setOop_score_medium(int oop_score_medium) {
        this.oop_score_medium = oop_score_medium;
    }

    public int getOop_score_hard() {
        return oop_score_hard;
    }

    public void setOop_score_hard(int oop_score_hard) {
        this.oop_score_hard = oop_score_hard;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
