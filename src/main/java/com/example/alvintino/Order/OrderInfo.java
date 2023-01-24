package com.example.alvintino.Order;

import com.example.alvintino.MenuPackage.Menu;
import com.example.alvintino.User.UserInfo;

import javax.persistence.*;
@Entity
@Table(name = "orders")
public class OrderInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo userInfo;

    @Column(nullable = false)
    private Integer jumlah;

    public OrderInfo() {
    }

    public OrderInfo(Menu menu, UserInfo userInfo, Integer jumlah) {
        this.menu = menu;
        this.userInfo = userInfo;
        this.jumlah = jumlah;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }
}