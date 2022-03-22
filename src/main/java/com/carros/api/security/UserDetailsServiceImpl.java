package com.carros.api.security;

import com.carros.domain.User;
import com.carros.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User myUser = userRep.findUserByLogin(username);

        if(myUser == null){
            throw new UsernameNotFoundException("User not found");
        }

        return myUser;
        //return User.withUsername(username).password(myUser.getSenha()).roles("USER").build();

        /*if(username.equals("user")){
            return User.withUsername(username).password(encoder.encode("user")).roles("USER").build();
        }else{
            return User.withUsername(username).password(encoder.encode("admin")).roles("USER", "ADMIN").build();
        }*/
    }
}
