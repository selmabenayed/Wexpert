package it.magnews.smtp.services.impl;

import it.magnews.smtp.dto.UserDTO;
import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPRepository;
import it.magnews.smtp.repository.UserAppRepository;
import it.magnews.smtp.services.UserService;
import it.magnews.smtp.utils.SMTPUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author sbenayed
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserAppRepository userAppRepository;
    @Autowired
    SMTPRepository sMTPRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDto = null;
        try {
            SMTPUser userEntity = userAppRepository.findUserByUsername(username);
            sMTPRepository.updateUserPwdMail(userEntity); 
            userDto = SMTPUtils.convertEntityToDto(userEntity);

        } catch (BadCredentialsException bce) {
            throw new BadCredentialsException("Invalid user");
        } catch (UsernameNotFoundException | NullPointerException bce) {
            throw new UsernameNotFoundException("Not found user");
        }
        Collection<GrantedAuthority> authList = getAuthorities(username);
        userDto.setAuthorities(authList);
        return userDto;
    }

    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(username));
        return authList;
    }

    public List<String> getRoles(String username) {

        List<String> rolesByUsername = userAppRepository.getUserRoles(userAppRepository.findUserByUsername(username));

        return rolesByUsername;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
