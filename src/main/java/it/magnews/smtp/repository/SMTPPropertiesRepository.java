/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.magnews.smtp.repository;

import it.magnews.smtp.entities.SMTPProperties;

/**
 *
 * @author selma.benayed
 */
public interface SMTPPropertiesRepository {

    /**
     * Create the SMTPProperties for the SMTP configurations
     *
     * @param sMTPProperties
     *
     * 
     */
    void createSMTPProperties(SMTPProperties sMTPProperties);
}
