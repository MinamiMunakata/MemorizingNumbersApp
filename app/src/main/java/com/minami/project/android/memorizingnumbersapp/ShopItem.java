package com.minami.project.android.memorizingnumbersapp;

/**
 * Created by Minami on 2018/08/24.
 */

public class ShopItem {
    private String category;
    private String item;
    private String org;
    private String code;
    private int trial_count;
    private int score;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTrial_count() {
        return trial_count;
    }

    public void setTrial_count(int trial_count) {
        this.trial_count = trial_count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
