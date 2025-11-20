package com.nro_sv_mgn.nro_server_manager.dto;

public class ItemOption {
    private int id;
    private String name;
    private int param;

    public ItemOption() {

    }

    public ItemOption(int id, String name, int param) {
        this.id = id;
        this.name = name;
        this.param = param;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParam() {
        return param;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParam(int param) {
        this.param = param;
    }
}
