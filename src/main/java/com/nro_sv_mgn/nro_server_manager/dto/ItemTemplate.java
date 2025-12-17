package com.nro_sv_mgn.nro_server_manager.dto;

public class ItemTemplate {
    private int id;
    private String name;
    private byte type;
    private byte gender;
    private String description;
    private short iconID;
    private short head;
    private short body;
    private short leg;
    private long gold;

    public ItemTemplate() {
    }

    public ItemTemplate(int id, String name, byte type, byte gender, String description, short iconID, short head, short body, short leg, long gold) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.description = description;
        this.iconID = iconID;
        this.head = head;
        this.body = body;
        this.leg = leg;
        this.gold = gold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getIconID() {
        return iconID;
    }

    public void setIconID(short iconID) {
        this.iconID = iconID;
    }

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public short getBody() {
        return body;
    }

    public void setBody(short body) {
        this.body = body;
    }

    public short getLeg() {
        return leg;
    }

    public void setLeg(short leg) {
        this.leg = leg;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }
}
