package com.fandm.saad.hackerquiz.models;

import org.parceler.Parcel;

@Parcel
public class User {

    private String full_name;
    private String android_device_id;

    private String python_score_easy;
    private String python_score_medium;
    private String python_score_hard;

    private String java_score_easy;
    private String java_score_medium;
    private String java_score_hard;

    private String cpp_score_easy;
    private String cpp_score_medium;
    private String cpp_score_hard;

    private String oop_score_easy;
    private String oop_score_medium;
    private String oop_score_hard;

    public User(String full_name, String android_device_id, String python_score_easy, String python_score_medium, String python_score_hard, String java_score_easy, String java_score_medium, String java_score_hard, String cpp_score_easy, String cpp_score_medium, String cpp_score_hard, String oop_score_easy, String oop_score_medium, String oop_score_hard) {
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
        this.python_score_easy = "0";this.python_score_medium = "0";this.python_score_hard = "0";
        this.java_score_easy = "0";this.java_score_medium = "0";this.java_score_hard = "0";
        this.cpp_score_easy = "0";this.cpp_score_medium = "0";this.cpp_score_hard = "0";
        this.oop_score_easy = "0";this.oop_score_medium = "0";this.oop_score_hard = "0";
    }

    public User(){
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAndroid_device_id() {
        return android_device_id;
    }

    public void setAndroid_device_id(String android_device_id) {
        this.android_device_id = android_device_id;
    }

    public String getPython_score_easy() {
        return python_score_easy;
    }

    public void setPython_score_easy(String python_score_easy) {
        this.python_score_easy = python_score_easy;
    }

    public String getPython_score_medium() {
        return python_score_medium;
    }

    public void setPython_score_medium(String python_score_medium) {
        this.python_score_medium = python_score_medium;
    }

    public String getPython_score_hard() {
        return python_score_hard;
    }

    public void setPython_score_hard(String python_score_hard) {
        this.python_score_hard = python_score_hard;
    }

    public String getJava_score_easy() {
        return java_score_easy;
    }

    public void setJava_score_easy(String java_score_easy) {
        this.java_score_easy = java_score_easy;
    }

    public String getJava_score_medium() {
        return java_score_medium;
    }

    public void setJava_score_medium(String java_score_medium) {
        this.java_score_medium = java_score_medium;
    }

    public String getJava_score_hard() {
        return java_score_hard;
    }

    public void setJava_score_hard(String java_score_hard) {
        this.java_score_hard = java_score_hard;
    }

    public String getCpp_score_easy() {
        return cpp_score_easy;
    }

    public void setCpp_score_easy(String cpp_score_easy) {
        this.cpp_score_easy = cpp_score_easy;
    }

    public String getCpp_score_medium() {
        return cpp_score_medium;
    }

    public void setCpp_score_medium(String cpp_score_medium) {
        this.cpp_score_medium = cpp_score_medium;
    }

    public String getCpp_score_hard() {
        return cpp_score_hard;
    }

    public void setCpp_score_hard(String cpp_score_hard) {
        this.cpp_score_hard = cpp_score_hard;
    }

    public String getOop_score_easy() {
        return oop_score_easy;
    }

    public void setOop_score_easy(String oop_score_easy) {
        this.oop_score_easy = oop_score_easy;
    }

    public String getOop_score_medium() {
        return oop_score_medium;
    }

    public void setOop_score_medium(String oop_score_medium) {
        this.oop_score_medium = oop_score_medium;
    }

    public String getOop_score_hard() {
        return oop_score_hard;
    }

    public void setOop_score_hard(String oop_score_hard) {
        this.oop_score_hard = oop_score_hard;
    }

    public String printUser(){
        return this.full_name + " " + this.android_device_id + "  SCORES:   PYTHON EASY:" + this.python_score_easy + " MEDIUM:" + this.python_score_medium + " HARD:" + this.python_score_hard + " SCORES:   JAVA EASY:" + this.java_score_easy + " MEDIUM:" + this.java_score_medium + " HARD:" + this.java_score_hard + " SCORES:   CPP EASY:" + this.cpp_score_easy + " MEDIUM:" + this.cpp_score_medium + " HARD:" + this.cpp_score_hard;
    }
}
