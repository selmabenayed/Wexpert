package it.magnews.smtp.repository;

import it.magnews.smtp.entities.SMTPUser;
import java.util.List;

/**
 *
 * @author sbenayed
 */
public interface UserAppRepository {

    /**
     * Find SMTPUser by a given username
     *
     * @param username
     *
     * @return The map that contains the size and the elapsed time of the sent
     * message
     */
    public SMTPUser findUserByUsername(String username);

    /**
     * find all the list of roles of  SMTPUser , here the list setted staticly
     *
     * @param userEntity
     *
     *
     * @return list of roles
     */
    public List<String> getUserRoles(SMTPUser userEntity);

    /**
     * Update a  SMTPUser
     *
     *  @param userEntity
     *
     * @
     */
    public void updateUser(SMTPUser userEntity);

}
