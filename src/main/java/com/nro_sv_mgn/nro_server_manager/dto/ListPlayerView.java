package com.nro_sv_mgn.nro_server_manager.dto;

public class ListPlayerView {
    private int id;
    private String name;
    private int dlLv;
    private long gold;
    private long ruby;
    private long dn;
    private long cd;

    public ListPlayerView() {
    }

    public ListPlayerView(int id, String name, int dlLv, long gold, long ruby, long dn, long cd) {
        this.id = id;
        this.name = name;
        this.dlLv = dlLv;
        this.gold = gold;
        this.ruby = ruby;
        this.dn = dn;
        this.cd = cd;
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

    public int getDlLv() {
        return dlLv;
    }

    public void setDlLv(int dlLv) {
        this.dlLv = dlLv;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getRuby() {
        return ruby;
    }

    public void setRuby(long ruby) {
        this.ruby = ruby;
    }

    public long getDn() {
        return dn;
    }

    public void setDn(long dn) {
        this.dn = dn;
    }

    public long getCd() {
        return cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }
}
