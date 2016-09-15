package it.magnews.smtp.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author sbenayed
 */
@Entity
@Table(name = "SMTPProperties")
public class SMTPProperties implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsmtp_properties")
    private Integer idsmtpProperties;
   
    
    @Size(max = 45)
    @Column(name = "libelle")
    private String libelle;
    
    @Column(name = "valueSMTP")
    private String valueSMTP;
   

    public SMTPProperties() {
    }

    public SMTPProperties(Integer idsmtpProperties) {
        this.idsmtpProperties = idsmtpProperties;
    }

    public Integer getIdsmtpProperties() {
        return idsmtpProperties;
    }

    public void setIdsmtpProperties(Integer idsmtpProperties) {
        this.idsmtpProperties = idsmtpProperties;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getValueSMTP() {
        return valueSMTP;
    }

    public void setValueSMTP(String valueSMTP) {
        this.valueSMTP = valueSMTP;
    }

    
}
