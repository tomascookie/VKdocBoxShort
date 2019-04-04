package ru.brainix.ept.vkbox.data;

public class SettingModel {

    private int id;
    private String name;
    private int state;

    public SettingModel(int id, String name, int year){
        this.id = id;
        this.name = name;
        this.state = year;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.state;
    }

}
