package com.example.demo.service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor   //auto injects the final field attributes
public class UserService implements UserDetailsService {  // this class knows how to find the details of user for authentication



    @Autowired
    private final UserRepo userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void addUser(Users user)
    {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        System.out.println("addUser Called");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUsername(username);  //finds if the username exists in database and returns Users object if exists
        if (user == null)
        {
            System.out.println("User is null ");
            throw new UsernameNotFoundException("User not found");
        }


        return new UserPrincipal(user); // userPrincipal implements UserDetails hence new is used and allowed to return UserPrincipal
    }
}
