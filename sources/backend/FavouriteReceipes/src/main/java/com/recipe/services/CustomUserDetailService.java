package com.recipe.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe.db.Login;
import com.recipe.db.LoginRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Autowired
    public CustomUserDetailService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = Optional.ofNullable(loginRepository.findByUsername(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

        return new org.springframework.security.core.userdetails.User(login.getUsername(), login.getPassword(), authorityListUser);
    }
}
