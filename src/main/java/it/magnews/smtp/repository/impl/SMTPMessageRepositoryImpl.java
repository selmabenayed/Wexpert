package it.magnews.smtp.repository.impl;

import it.magnews.smtp.entities.SMTPMessage;
import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPMessageRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbenayed
 */
@Repository("sMTPMessageRepository")
@Transactional
public class SMTPMessageRepositoryImpl implements SMTPMessageRepository {

    @PersistenceContext(unitName = "smtpPU")
    private EntityManager em;

    @Override
    public SMTPMessage findSMTPMessageByDate(Date date) {
        try {
            return em.createQuery("select u from SMTPMessage u where u.dateMessage = ?1", SMTPMessage.class)
                    .setParameter(1, date)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void addSMTPMessage(SMTPMessage sMTPMessage) {
        em.persist(sMTPMessage);
    }

    @Override
    public void updateSMTPMessage(SMTPMessage sMTPMessage) {
        em.merge(sMTPMessage);
    }

    @Override
    public List<SMTPMessage> findSMTPMessageByUser(SMTPUser user) {

        try {
            return em.createQuery("select u from SMTPMessage u where u.idUser.idUser = ?1", SMTPMessage.class)
                    .setParameter(1, user.getIdUser())
                    .getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Map<String, String> getMinMaxAvgMessageDelivery(List<SMTPMessage> listSMTPMessage) {

        Map<String, String> map = new HashMap<>();
        List<Integer> listCounter = new ArrayList<>();
        double avg = 0;
        for (SMTPMessage sMTPMessag : listSMTPMessage) {
            listCounter.add(Integer.parseInt(sMTPMessag.getCounter()));
            avg = avg + Integer.parseInt(sMTPMessag.getCounter());
        }
        avg = avg / listSMTPMessage.size();

        int max = Collections.max(listCounter);
        int min = Collections.min(listCounter);

        map.put("max", max + "");
        map.put("min", min + "");
        map.put("avg", avg + "");

        return map;
    }

}
