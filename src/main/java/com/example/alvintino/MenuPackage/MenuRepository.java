package com.example.alvintino.MenuPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Optional<Menu> findByName(String Name);
}