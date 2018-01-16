package com.example.ponymusic.bean;

/**
 * Created by acer on 2018/1/8.
 */

public class EventBusaaa {
    private String id;

    public EventBusaaa(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EventBusaaa{" +
                "id='" + id + '\'' +
                '}';
    }
}
