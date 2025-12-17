package com.nro_sv_mgn.nro_server_manager.dto;

public class PlayerPoint {
    public int avatarId;
    private double hp;
    private double mp;
    private double attack;
    private int crits;
    private long power;
    private long tn;
    private int def;
    private int tlNeDon;

    private double currentHp;
    private double currentMp;

    private double hpMax;
    private double mpMax;

    private double dame;

    private short critMax;

    private int defMax;

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getMp() {
        return mp;
    }

    public void setMp(double mp) {
        this.mp = mp;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public int getCrits() {
        return crits;
    }

    public void setCrits(int crits) {
        this.crits = crits;
    }

    public long getPower() {
        return power;
    }

    public void setPower(long power) {
        this.power = power;
    }

    public long getTn() {
        return tn;
    }

    public void setTn(long tn) {
        this.tn = tn;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getTlNeDon() {
        return tlNeDon;
    }

    public void setTlNeDon(int tlNeDon) {
        this.tlNeDon = tlNeDon;
    }

    public double getHpMax() {
        return hpMax;
    }

    public double getMpMax() {
        return mpMax;
    }

    public double getDame() {
        return dame;
    }

    public short getCritMax() {
        return critMax;
    }

    public int getDefMax() {
        return defMax;
    }

    public PlayerPoint() {
    }

    public void setHpMax(double hpMax) {
        this.hpMax = hpMax;
    }

    public void setMpMax(double mpMax) {
        this.mpMax = mpMax;
    }

    public void setDame(double dame) {
        this.dame = dame;
    }

    public void setCritMax(short critMax) {
        this.critMax = critMax;
    }

    public void setDefMax(double defMax) {
        this.defMax = (int) defMax;
    }

    public double getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }

    public double getCurrentMp() {
        return currentMp;
    }

    public void setCurrentMp(double currentMp) {
        this.currentMp = currentMp;
    }

    public void setDefMax(int defMax) {
        this.defMax = defMax;
    }

    public PlayerPoint(double hp, double mp, double attack, int crits, long power, long tn, int def, int tlNeDon, double hpMax, double mpMax, double dame, short critMax, int defMax) {
        this.hp = hp;
        this.mp = mp;
        this.attack = attack;
        this.crits = crits;
        this.power = power;
        this.tn = tn;
        this.def = def;
        this.tlNeDon = tlNeDon;
        this.hpMax = hpMax;
        this.mpMax = mpMax;
        this.dame = dame;
        this.critMax = critMax;
        this.defMax = defMax;
    }
}
