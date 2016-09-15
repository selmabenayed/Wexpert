package it.magnews.smtp.repository;

import it.magnews.smtp.entities.SMTPUser;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sbenayed
 */
public interface SMTPRepository {

    /**
     * Send a SMPT message on one time
     *
     * @param emailRecip
     * @param subject
     * @param texte
     * @param listAttachedFile
     *
     * @return The map that contains the size and the elapsed time of the sent message 
     */
    public Map<String, String> sendEmail(String emailRecip, String subject, String texte, List<String> listAttachedFile);

      /**
     * Send a SMPT message repeatedly
     *
     * @param  emailRecip
     * @param subject
     * @param texte
     * @param listAttachedFile
     *
     * @return The map that contains the size and the elapsed time of the sent message 
     */
    public Map<String, String> sendEmailRepeatdly(String emailRecip, String subject, String texte, List<String> listAttachedFile);

       /**
     * Send a SMPT message on one time
     *
     * @param connectedUser     
     *
     * 
     */
    public void updateUserPwdMail(SMTPUser connectedUser);

}
