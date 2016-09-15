package it.magnews.smtp.services.impl;

import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPRepository;
import it.magnews.smtp.services.SMTPservice;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbenayed
 */
@Service("sMTPservice")
@Transactional
public class SMTPserviceImpl implements SMTPservice {

    @Autowired
    @Qualifier("sMTPRepository")
    private SMTPRepository sMTPRepository;

    @Override
    public void sendEmail(String emailRecip, String subject, String texte,List<String>  listAttachedFile) {
       sMTPRepository.sendEmail(emailRecip, subject, texte,listAttachedFile);
    }

    @Override
    public void updateUserPwdMail(SMTPUser connectedUser) {
      sMTPRepository.updateUserPwdMail(connectedUser); 
    }

}
