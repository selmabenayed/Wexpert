package it.magnews.smtp.repository;

import it.magnews.smtp.entities.SMTPMessage;
import it.magnews.smtp.entities.SMTPUser;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sbenayed
 */
public interface SMTPMessageRepository {

    /**
     * Retrieves the SMTPMessage with a given date
     *
     * @param date
     *
     * @return The entity SMTPMessage
     */
    SMTPMessage findSMTPMessageByDate(Date date);

    /**
     * Store the SMTPMessage
     *
     * @param sMTPMessage
     *
     *
     */
    void addSMTPMessage(SMTPMessage sMTPMessage);

    /**
     * Update the SMTPMessage 
     *
     * @param sMTPMessage
     * 
     *
     * 
     */
    void updateSMTPMessage(SMTPMessage sMTPMessage);

     /**
     * Retrieves All SMTPMessage with a given SMTPUser
     *
     * @param user
     *
     * @return The list of SMTPMessage
     */
    
    List<SMTPMessage> findSMTPMessageByUser(SMTPUser user);

    
    /**
     * Retrieves the min max and avg of delivery message throw SMTP
     *
     * @param listSMTPMessage
     *
     * @return The map that contains min, max and avg of delivery message for different date of day
     */
    Map<String, String> getMinMaxAvgMessageDelivery(List<SMTPMessage> listSMTPMessage);
}
