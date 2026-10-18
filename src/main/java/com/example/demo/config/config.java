package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class config {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService); //creates a object of daoAuthentiactionProvider and where to find user details
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //basic string match to verify the password
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));        //Bcrypts the password
        return provider;
    }

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http)
    {
        http
                .csrf(Customizer -> Customizer.disable())  // disables csrf
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/saveUser","/login").permitAll()  // allows "saveUser" endpoint without authorization
                        .anyRequest().authenticated())   // tells springboot to authorize all requests
                .httpBasic(Customizer.withDefaults())   // to declare authentication to basic auth
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // to declare stateless
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService UserDetailsService() {    // method to manually set username and password , not recommended in real projects
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("bhalu")
//                .password("user@0001")
//                .roles("user")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                                .username("rakesh")
//                                .password("admin@0001")
//                                .roles("admin")
//                                .build();
//
//        return new InMemoryUserDetailsManager(user,admin); // manages all the users
//
//    }


}
