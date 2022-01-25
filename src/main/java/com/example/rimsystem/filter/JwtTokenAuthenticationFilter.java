package com.example.rimsystem.filter;

import com.example.rimsystem.service.UserService;
import com.example.rimsystem.tool.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @auther luyu
 */

public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    @Autowired
    UserService userService;
    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader("authentication");
        if(jwt==null||jwt.equals("")){
            chain.doFilter(request,response);
            return;
        }
        Claims claim = jwtTokenUtils.JWTParse(jwt);
        if(claim==null){
            throw new JwtException("token 异常");
        }
        Date date = claim.getExpiration();
        if(date.before(new Date(System.currentTimeMillis()))){
            throw new JwtException("token 已过期");
        }
        String username = claim.getSubject();
        String rolesAndPer = userService.selectRolesAndPerByUsername(username);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(rolesAndPer);
        //获取用户的权限信息等信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,null,grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        chain.doFilter(request,response);
    }
}
