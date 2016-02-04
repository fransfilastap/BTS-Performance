/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huawei.gsm.configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.DelegatingFilterProxy;

/**
 *
 * @author Frans Filasta Pratama <franspratama@mail01.huawei.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
                .loginPage("/login").failureUrl("/login?error").defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
            .and()
            .httpBasic()
                .realmName("/tdc")
            .and()
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
    }

    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(datasource)
                .passwordEncoder( passwordEncoder() )
                .usersByUsernameQuery( QUERY_USER_BY_USERNAME )
                .authoritiesByUsernameQuery( QUERY_AUTHORITIES_BY_USERNAME );
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        return encoder;
    }    
    
    
    @Autowired
    private DataSource datasource;

    @Autowired
    private ServletContext servletContext;
    private final String QUERY_USER_BY_USERNAME = "SELECT username,password, enabled FROM users WHERE username=?";
    private final String QUERY_AUTHORITIES_BY_USERNAME = "SELECT users.username,`roles`.role FROM `user_roles` LEFT JOIN `roles` ON roles.id = user_roles.role_id LEFT JOIN `users` ON  user_roles.user_id = users.id WHERE users.username = ?";
}
