package com.user.account.service;

import com.user.account.model.SecurityClient;
import com.user.account.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByClientname(username).map(SecurityClient::new).orElseThrow(()-> new UsernameNotFoundException("Bad credentials"));
    }
}
