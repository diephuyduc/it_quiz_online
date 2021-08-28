package com.example.myquiz.models;

import java.io.Serializable;

public class Category implements Serializable {
    public String name;
    public String id;
    public String img;

    public Category(String id, String name, String img) {
        this.id = id;

        this.name = name;
        this.img = img;
    }


}
