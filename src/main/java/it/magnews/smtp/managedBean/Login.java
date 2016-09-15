/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.magnews.smtp.managedBean;

import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.UserAppRepository;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author sbenayed
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {


    @ManagedProperty(value = "#{userAppRepository}")
    private UserAppRepository userAppRepository;
    private SMTPUser ConnectedUser;

    @PostConstruct
    public void init() {
        try {
            resolveConnectedUser();
        } catch (Exception ex) {
            // logger.error(ex);
        }
    }

    public Boolean isConnectedUserAllowed() {
        Boolean isAllowed = false;
        List<String> roles = userAppRepository.getUserRoles(ConnectedUser);
        if (!roles.contains("ADMIN")) {
            isAllowed = true;
        } else if (!roles.contains("USER")) {
            isAllowed = true;
        }
        return isAllowed;
    }

    public void resolveConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if (userName != null && !userName.isEmpty()) {
            ConnectedUser = userAppRepository.findUserByUsername(userName);
           
        }
    }

    public String encodePassword(String password) {
        PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(password, null);
    }

    public void updateProfil() throws IOException {

       

    }

    public SMTPUser getConnectedUser() {
        return ConnectedUser;
    }

    public void setConnectedUser(SMTPUser ConnectedUser) {
        this.ConnectedUser = ConnectedUser;
    }


    public UserAppRepository getUserAppRepository() {
        return userAppRepository;
    }

    public void setUserAppRepository(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

}
