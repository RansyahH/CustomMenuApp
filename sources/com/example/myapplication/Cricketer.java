package com.example.myapplication;

import java.io.Serializable;

public class Cricketer implements Serializable {
    public String cricketerName;
    public String cricketerPrice;
    public String teamName;

    public Cricketer(String cricketerName2, String teamName2, String cricketerPrice2) {
        this.cricketerName = cricketerName2;
        this.teamName = teamName2;
        this.cricketerPrice = cricketerPrice2;
    }

    public Cricketer() {
    }

    public String getCricketerName() {
        return this.cricketerName;
    }

    public void setCricketerName(String cricketerName2) {
        this.cricketerName = cricketerName2;
    }

    public String getCricketerPrice() {
        return this.cricketerPrice;
    }

    public void setCricketerPrice(String cricketerPrice2) {
        this.cricketerPrice = cricketerPrice2;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName2) {
        this.teamName = teamName2;
    }
}
