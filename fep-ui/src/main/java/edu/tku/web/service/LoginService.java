package edu.tku.web.service;

import edu.tku.db.model.User;
import edu.tku.db.repository.UserRepository;
import edu.tku.web.entity.CustomUserDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
        Optional<User> optional = userRepository.findById(username);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return new CustomUserDetails(optional.get());
    }
}
