/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.magnews.smtp.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author sbenayed
 */
@Entity
@Table(name = "SMTPMessage")
public class SMTPMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSMTPMessage")
    private Integer idSMTPMessage;

    @Column(name = "dateMessage", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date dateMessage;

    @Size(max = 45)
    @Column(name = "counter")
    private String counter;

    @ManyToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    private SMTPUser idUser;

    public Integer getIdSMTPMessage() {
        return idSMTPMessage;
    }

    public void setIdSMTPMessage(Integer idSMTPMessage) {
        this.idSMTPMessage = idSMTPMessage;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public SMTPUser getIdUser() {
        return idUser;
    }

    public void setIdUser(SMTPUser idUser) {
        this.idUser = idUser;
    }

   

   

}
