package com.example.talentmicro.viewpageradapter;

import java.io.Serializable;
import java.util.ArrayList;

class MainListDto implements Serializable {

    private String name;

    private ArrayList<String> urlList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }
}
