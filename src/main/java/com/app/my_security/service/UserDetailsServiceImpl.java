package com.app.my_security.service;

import com.app.my_security.entity.Role;
import com.app.my_security.entity.UserRoles;
import com.app.my_security.entity.AppUsers;
import com.app.my_security.repository.UserApplicationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUsers user = userApplicationRepository.findByEmail(username);
        if (Objects.isNull(user)) {
            log.info("UserDetailsServiceImpl :: loadByUsername :: Message ::-> there is no user with this username.");
            throw new UsernameNotFoundException("There is no User Found with this username");
        }
        List<GrantedAuthority> authorityList = (List<GrantedAuthority>) getAuthorities(user.getRoles());
        return new User(user.getEmail(), user.getPassword(), authorityList);
    }


    //TODO:: create function to get Authorities:
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRoles> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles role : roles) {
            String aAuthority = role.getRole().toString();
            authorities.add(new SimpleGrantedAuthority(aAuthority));

//            role.getPrivileges().stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .forEach(authorities::add);
        }

        return authorities;
    }
}
