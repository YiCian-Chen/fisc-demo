package edu.tku.web.entity;

import edu.tku.db.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private User user;
    public CustomUserDetails(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(user.getRoleId());
    }

    @Override
    public String getPassword() {
        // BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        // String a = bcryptPasswordEncoder.encode("1qaz@WSX");
        // String b = bcryptPasswordEncoder.encode("1qaz@WSX");
        // boolean c1 = bcryptPasswordEncoder.matches("1qaz@WSX",a);
        // boolean c2 = bcryptPasswordEncoder.matches("1qaz@WSX",b);
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

}
