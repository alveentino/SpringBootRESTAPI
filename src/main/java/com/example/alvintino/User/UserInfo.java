package com.example.alvintino.User;

import com.example.alvintino.Role.RoleInfo;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "user")
public class UserInfo implements UserDetails{
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleInfo> roles = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50,unique = true)
    @NotNull
    @Length(min = 5, max = 50)
    private String username;

    @Column(nullable = false, length = 64)
    @NotNull
    @Length(min = 8, max = 64)
    private String password;

    @Nullable
    @Length(max = 50)
    private String name;

    @Nullable
    @Length(max = 50)
    private String address;
    public UserInfo(){

    }

    public UserInfo(Integer id, String username, String password, @Nullable String name, @Nullable String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleInfo role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public Set<RoleInfo> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleInfo> roles) {
        this.roles = roles;
    }

    public void addRole(RoleInfo role) {
        this.roles.add(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
