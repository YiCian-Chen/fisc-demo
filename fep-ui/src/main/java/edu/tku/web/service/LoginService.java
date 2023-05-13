package edu.tku.web.service;

import edu.tku.db.repository.UserRepository;
import edu.tku.web.entity.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoginService implements UserDetailsService {
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername: {} ", username);
        return new CustomUserDetails(username);
    }
}
