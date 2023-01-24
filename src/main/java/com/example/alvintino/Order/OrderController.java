package com.example.alvintino.Order;

import com.example.alvintino.MenuPackage.Menu;
import com.example.alvintino.MenuPackage.MenuRepository;
import com.example.alvintino.User.UserInfo;
import com.example.alvintino.User.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class OrderController {
    @Autowired MenuRepository menuRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired UserInfoRepository userInfoRepository;

//    order menu
    @RolesAllowed({"ROLE_SELLER","ROLE_CUSTOMER"})
    @PostMapping("order")
    public ResponseEntity<Optional<Menu>> order(@RequestBody @Valid tempOrderInfo tempOrderInfo){
        if (menuRepository.findByName(tempOrderInfo.getNamamenu()).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if (userInfoRepository.findByUsername(tempOrderInfo.getUsername()).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        try {
            Menu menu = menuRepository.findByName(tempOrderInfo.getNamamenu()).get();
            UserInfo userInfo = userInfoRepository.findByUsername(tempOrderInfo.getUsername()).get();
            OrderInfo orderInfo1 = new OrderInfo();
            orderInfo1.setJumlah(tempOrderInfo.getJumlah());
            orderInfo1.setUserInfo(userInfo);
            orderInfo1.setMenu(menu);
            orderRepository.save(orderInfo1);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
