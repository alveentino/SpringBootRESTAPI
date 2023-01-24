package com.example.alvintino.Role;

import javax.persistence.*;
@Entity
@Table(name = "roles")
public class RoleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    public RoleInfo() { }

    public RoleInfo(String name) {
        this.name = name;
    }

    public RoleInfo(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
