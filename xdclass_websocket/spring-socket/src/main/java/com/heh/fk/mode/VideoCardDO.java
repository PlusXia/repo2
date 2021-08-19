package com.heh.fk.mode;

import java.util.List;

public class VideoCardDO {

    private String title;

    private int id;

    private int weight;

    public List<VideoDO> getList() {
        return list;
    }

    public void setList(List<VideoDO> list) {
        this.list = list;
    }

    List<VideoDO> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
