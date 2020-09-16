package com.bitgallon.oauthserver.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bitgallon.oauthserver.repository.UserAccountRepository;

@Service
public class UserAccountDetailService implements UserDetailsService {

    private UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountDetailService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	return userAccountRepository
                .findByUsername(username, getClientId())
                .map(account -> new User(account.getUsername(), account.getPassword(), AuthorityUtils.
                		NO_AUTHORITIES))
                .orElseThrow(() -> new UsernameNotFoundException("Could not find " + username));
    }
    
    private  String getClientId(){
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getParameter("client_id");
    }
}
