package com.example.alvintino.Jwt;

import com.example.alvintino.Role.RoleInfo;
import com.example.alvintino.User.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired private JwtTokenUtility jwtTokenUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (hasAuthorizationBearer(request)){
            String accesstoken= getAccessToken(request);
            if (jwtTokenUtility.validateAccessToken(accesstoken)){
                setAuthenticationContext(accesstoken,request);
            }
        }
        chain.doFilter(request,response);
    }

    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }

    private boolean hasAuthorizationBearer(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }

    private void setAuthenticationContext(String token, HttpServletRequest request){
        UserDetails userDetails = getUserDetails(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserDetails getUserDetails(String token){
        UserInfo userInfo = new UserInfo();
        Jws<Claims> claimsJws = jwtTokenUtility.parseClaim(token);
        Claims claims = claimsJws.getBody();
        String roles = (String) claims.get("roles");
        roles = roles.replace("[", "").replace("]", "");
        String[] roleNames = roles.split(",");
        for (String aRoleName : roleNames) {
            userInfo.addRole(new RoleInfo(aRoleName));
        }
        String username = jwtTokenUtility.getUsername(token);
        userInfo.setUsername(username);
        return userInfo;
    }
}