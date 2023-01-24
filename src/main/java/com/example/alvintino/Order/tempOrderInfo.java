package com.example.alvintino.Order;

import javax.validation.constraints.NotNull;

public class tempOrderInfo {
    @NotNull
    private String namamenu;

    @NotNull
    private String username;

    @NotNull
    private Integer jumlah;

    public tempOrderInfo() {
    }

    public tempOrderInfo(String namamenu, String username, Integer jumlah) {
        this.namamenu = namamenu;
        this.username = username;
        this.jumlah = jumlah;
    }

    public String getNamamenu() {
        return namamenu;
    }

    public void setNamamenu(String namamenu) {
        this.namamenu = namamenu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}
