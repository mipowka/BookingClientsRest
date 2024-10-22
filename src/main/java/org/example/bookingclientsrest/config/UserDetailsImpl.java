package org.example.bookingclientsrest.config;

import lombok.Builder;
import lombok.Data;
import org.example.bookingclientsrest.model.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Builder
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private Set<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(roles1 -> new SimpleGrantedAuthority("ROLE_" + roles1.name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
