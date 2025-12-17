package com.nro_sv_mgn.nro_server_manager.dto;

public class MapTemplate {
    private int id;
    private String name;
    private byte type;
    private byte planetId;
    private byte maxPlayerPerZone;
    private byte zones;

    public MapTemplate() {
    }

    public MapTemplate(int id, String name, byte type, byte planetId, byte maxPlayerPerZone, byte zones) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.planetId = planetId;
        this.maxPlayerPerZone = maxPlayerPerZone;
        this.zones = zones;
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

    public byte getPlanetId() {
        return planetId;
    }

    public void setPlanetId(byte planetId) {
        this.planetId = planetId;
    }

    public byte getMaxPlayerPerZone() {
        return maxPlayerPerZone;
    }

    public void setMaxPlayerPerZone(byte maxPlayerPerZone) {
        this.maxPlayerPerZone = maxPlayerPerZone;
    }

    public byte getZones() {
        return zones;
    }

    public void setZones(byte zones) {
        this.zones = zones;
    }
}
