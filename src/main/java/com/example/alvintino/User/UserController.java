package com.example.alvintino.User;

import com.example.alvintino.Jwt.JwtTokenUtility;
import com.example.alvintino.Role.RoleInfo;
import com.example.alvintino.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtility jwtTokenUtility;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginInfo loginInfo) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword())
        );
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        String accesstoken = jwtTokenUtility.generateAccessToken(userInfo);
        LoginResponse loginResponse = new LoginResponse(userInfo.getUsername(), accesstoken);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("registrasi")
    public ResponseEntity<?> registrasi(@RequestBody @Valid RegistrationInfo registrationInfo) {
        if (userInfoRepository.findByUsername(registrationInfo.getUsername()).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registrationInfo.getUsername());
        userInfo.setPassword(passwordEncoder.encode(registrationInfo.getPassword()));
        userInfo.setAddress(registrationInfo.getAddress());
        userInfo.setName(registrationInfo.getName());
        userInfo.addRole(new RoleInfo(2));
        userInfoRepository.save(userInfo);
        return ResponseEntity.ok().build();
    }
}