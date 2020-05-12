package com.televisivo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        // String senha = passwordEncoder().encode("123456");
		// System.out.println("senha: [" + senha + "]");
		authentication.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		// authentication.inMemoryAuthentication().withUser("admin").password(senha).roles("USUARIO");
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/resources/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/fontawesome/**").permitAll()
            .antMatchers("/images/**").permitAll()
            .antMatchers("/login/**").permitAll() // tirar
            .antMatchers("/roles/**").permitAll() // tirar
            .antMatchers("/usuarios/**").permitAll() // .hasAnyRole("ADMINISTRADOR", "USUARIO")
            .anyRequest().authenticated();
        http.formLogin()
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("senha")
            .defaultSuccessUrl("/home", true)
            .failureUrl("/login?error=true")
            .permitAll();
        http.logout()
            .logoutSuccessUrl("/login")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .clearAuthentication(true);
        http.csrf().disable();
    }
    
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}