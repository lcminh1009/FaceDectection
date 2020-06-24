package com.example.model;

import java.io.Serializable;

public class Sticker implements Serializable {
    private String ten;
    private int hinh;
    private int rule;

    public Sticker(String ten, int hinh, int rule) {
        this.ten = ten;
        this.hinh = hinh;
        this.rule = rule;
    }

    public Sticker() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public int getRule() {
        return rule;
    }

    public void setRule(int rule) {
        this.rule = rule;
    }
}
