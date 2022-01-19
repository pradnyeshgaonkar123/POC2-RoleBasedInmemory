
package com.studentProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{


	//create 2 users for demo (Authentication)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//{noop} to your password in order for the DeligatingPasswordEncoder
		
		//with hard coded values
		auth.inMemoryAuthentication().withUser("user").password("{noop}user123").roles("USER")
		.and().withUser("admin").password("{noop}admin123").roles("USER","ADMIN");
//		auth.userDetailsService(userDetailsService)
//		.passwordEncoder(NoOpPasswordEncoder.getInstance());
		
	}
	
	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/project/add/**").hasAnyRole("USER", "ADMIN")
		.antMatchers("/**").hasRole("ADMIN") //** for all end points with POST and only admin can do it
		.and()
		.csrf().disable();
	}
}
