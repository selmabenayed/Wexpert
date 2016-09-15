package it.magnews.smtp.managedBean;

import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.UserAppRepository;
import it.magnews.smtp.utils.JsfUtils;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author sbenayed
 */
@ManagedBean(name = "registerManagedBean")
@ViewScoped
public class RegisterManagedBean {

    @ManagedProperty(value = "#{userAppRepository}")
    private UserAppRepository userAppRepository;
    private SMTPUser connectedUser;

    @PostConstruct
    public void init() {

        connectedUser = new SMTPUser();
    }

    public void createUser() {

        SMTPUser existantUser = userAppRepository.findUserByUsername(connectedUser.getUserName());
        if (existantUser == null) {
            userAppRepository.updateUser(connectedUser);
            JsfUtils.addSuccessMessage("User created");
        } else {

            JsfUtils.addErrorMessage("User with username=" + connectedUser.getUserName() + " is already exist! ");
        }

    }

    public UserAppRepository getUserAppRepository() {
        return userAppRepository;
    }

    public void setUserAppRepository(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public SMTPUser getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(SMTPUser connectedUser) {
        this.connectedUser = connectedUser;
    }

}
