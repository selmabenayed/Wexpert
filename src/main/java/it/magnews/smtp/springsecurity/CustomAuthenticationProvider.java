
package it.magnews.smtp.springsecurity;

import it.magnews.smtp.services.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

/**
 *
 * @author sbenayed
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired   
    private UserService userService;
   /* @Autowired   
    private SMTPservice sMTPservice;*/

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = userService.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Not found User");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
