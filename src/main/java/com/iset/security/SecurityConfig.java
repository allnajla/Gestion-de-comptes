package com.iset.security;

import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter{
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	System.out.println("Security enabled");
	auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN","USER").and().withUser("user").password("user").roles("USER");
}

@Override

	protected void configure(HttpSecurity http) throws Exception {
	http.formLogin();
	http.authorizeRequests().antMatchers("/operations","/consultercompte").hasRole("USER");
	http.authorizeRequests().antMatchers("/saveOperation","/operation","/consultercompte").hasRole("ADMIN");
	}
}
