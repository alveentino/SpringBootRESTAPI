package com.example.alvintino.MenuPackage;

import com.example.alvintino.User.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    private MenuRepository menurepository;

    @Autowired
    private UserInfo userInfo;

//    find all menu
    @RolesAllowed({"ROLE_SELLER","ROLE_CUSTOMER"})
    @GetMapping("list")
    public List<Menu> list() {
        return menurepository.findAll();
    }

//    find menu by id
    @RolesAllowed({"ROLE_SELLER","ROLE_CUSTOMER"})
    @GetMapping("{id}")
    public ResponseEntity<Optional<Menu>> Id(@PathVariable("id") Integer id) {
        if (menurepository.findById(id).isPresent()) {
            return ResponseEntity.ok(menurepository.findById(id));
        }
        return ResponseEntity.notFound().build();
    }
//    create menu
    @RolesAllowed("ROLE_SELLER")
    @PostMapping("create")
    public ResponseEntity<Menu> responseEntity(@RequestBody @Valid Menu menu) {
        Menu newmenu = menurepository.save(menu);
        URI uri = URI.create("/product/" + newmenu.getId());
        return ResponseEntity.created(uri).body(newmenu);
    }

//    delete menu
    @RolesAllowed("ROLE_SELLER")
    @DeleteMapping("{id}")
    public ResponseEntity<Optional<Menu>> delete(@PathVariable("id") Integer id){
        if (menurepository.findById(id).isPresent()){
            menurepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

//    update menu
    @RolesAllowed("ROLE_SELLER")
    @PatchMapping("{id}")
    public ResponseEntity<Optional<Menu>> update(@RequestBody @Valid Menu menu ,@PathVariable("id") Integer id){
        if (menurepository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Menu menu1 = menurepository.findById(id).get();
        menu1.setName(menu.getName());
        menu1.setPrice(menu.getPrice());
        menurepository.save(menu1);
        return ResponseEntity.ok().build();
    }
}