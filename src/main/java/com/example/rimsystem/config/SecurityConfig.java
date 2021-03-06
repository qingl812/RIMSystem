package com.example.rimsystem.config;
import com.example.rimsystem.filter.JwtTokenAuthenticationFilter;
import com.example.rimsystem.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author luyu
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final static String[] URL_WHITE_LIST = {
        "/api/login", "/api/logout"
    };

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    @Autowired
    MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    MyLogOutHandler myLogOutHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtTokenAuthenticationFilter JwtTokenAuthenticationFilter() throws Exception {
        JwtTokenAuthenticationFilter jwtTokenFilter = new JwtTokenAuthenticationFilter(authenticationManager());
        return jwtTokenFilter;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //?????????????????????
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        ????????????
        http.cors().and().csrf().disable()
//                ??????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

//                ????????????
                .formLogin()
                .loginProcessingUrl("/api/login")
                .failureHandler(myAuthenticationFailureHandler)
                .successHandler(myAuthenticationSuccessHandler)
                .and()

                .logout()
                .logoutSuccessHandler(myLogOutHandler)

//                ???????????????????????????????????????
                .and()
                .authorizeRequests()
                .antMatchers(URL_WHITE_LIST).permitAll()
                .antMatchers("/webjars/**").permitAll()
                // swagger??????????????????
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/profile/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()

                .anyRequest()
                .authenticated()
                .and()
//                ???????????????
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                //        ????????????????????????
                .and()
                .addFilter(JwtTokenAuthenticationFilter());
    }
}
