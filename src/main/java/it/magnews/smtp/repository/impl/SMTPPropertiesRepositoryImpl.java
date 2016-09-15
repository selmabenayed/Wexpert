/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.magnews.smtp.repository.impl;

import it.magnews.smtp.entities.SMTPProperties;
import it.magnews.smtp.repository.SMTPPropertiesRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author selma.benayed
 */
@Repository("sMTPPropertiesRepository")
@Transactional
public class SMTPPropertiesRepositoryImpl implements SMTPPropertiesRepository {
    
    @PersistenceContext(unitName = "smtpPU")
    private EntityManager em;
    
    @Override
    public void createSMTPProperties(SMTPProperties sMTPProperties) {
        em.merge(sMTPProperties);
    }
    
}
