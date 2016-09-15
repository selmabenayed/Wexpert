package it.magnews.smtp.services;

import it.magnews.smtp.entities.SMTPUser;
import java.util.List;

/**
 *
 * @author sbenayed
 */
public interface SMTPservice {
    public void sendEmail(String emailRecip, String subject, String texte,List<String>  listAttachedFile);
    public void updateUserPwdMail(SMTPUser connectedUser);
}
