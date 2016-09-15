package it.magnews.smtp.repository.impl;

import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.UserAppRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("userAppRepository")
@Transactional
public class UserAppRepositoryImpl implements UserAppRepository {

    @PersistenceContext(unitName = "smtpPU")
    private EntityManager em;

    @Override
    public SMTPUser findUserByUsername(String username) {
        try {
            return em.createQuery("select u from SMTPUser u where u.userName = ?1", SMTPUser.class)
                    .setParameter(1, username)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<String> getUserRoles(SMTPUser user) {

        List<String> listRole = new ArrayList<>(Arrays.asList("ADMIN", "USER"));
        return listRole;
    }

    @Override
    public void updateUser(SMTPUser userEntity) {
        em.merge(userEntity);
    }

}
