package it.magnews.smtp.repository.impl;

import it.magnews.smtp.entities.SMTPProperties;
import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sbenayed
 */
@Repository("sMTPRepository")
@Transactional
public class SMTPRepositoryImpl implements SMTPRepository {

    private Properties props;
    public static final String MAIL_USER = "mail_user";
    public static final String MAIL_PASSWORD = "mail_password";
    public static final String MAIL_HOST = "mail.smtp.host";
    private String username;
    private String password;
    private String host;

    @PersistenceContext(unitName = "smtpPU")
    private EntityManager em;

    @PostConstruct
    void init() {

        List<SMTPProperties> listSMTPProperties = findSMTPProperties();
        props = getSMTPProps(listSMTPProperties);

    }

    @Override
    public Map<String, String> sendEmail(String emailRecip, String subject, String texte, List<String> listAttachedFile) {

        Map<String, String> map = new HashMap<>();
        String isMsgSent = "0";
        long sizeMessage = 0;
        long elapsedTime = 0;
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            String address = emailRecip;
            InternetAddress[] iAdressArray = InternetAddress.parse(address);

            message.setRecipients(Message.RecipientType.TO, iAdressArray);
            message.setSubject(subject);
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(texte);
            /* MimeBodyPart mbp2 = new MimeBodyPart();
             if (attachedFile != null) {
             FileDataSource fds = new FileDataSource(attachedFile);
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());
             }*/

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            for (String attachedFile : listAttachedFile) {
                if (attachedFile != null) {
                    mp = addAttachment(mp, attachedFile);
                }
            }

            /* if (attachedFile != null) {
             mp.addBodyPart(mbp2);
             }*/
            message.setContent(mp);

            long startTime = System.currentTimeMillis();

            Transport.send(message);

            elapsedTime = System.currentTimeMillis() - startTime;

            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            message.writeTo(bais);

            sizeMessage = bais.size();
            isMsgSent = "1";
        } catch (MessagingException e) {
            isMsgSent = "0";
        } catch (IOException ex) {
            Logger.getLogger(SMTPRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        map.put("isMailSent", isMsgSent);
        map.put("size", sizeMessage + "");
        map.put("elapsedTime", elapsedTime + "");

        return map;
    }

    @Override
    public void updateUserPwdMail(SMTPUser connectedUser) {
        List<SMTPProperties> listPropos = findSMTPProperties();
        for (SMTPProperties pr : listPropos) {

            if (pr.getLibelle().equals(MAIL_USER)) {
                pr.setValueSMTP(connectedUser.getEmail());
                em.merge(pr);
            }
            if (pr.getLibelle().equals(MAIL_PASSWORD)) {
                pr.setValueSMTP(connectedUser.getPassword());
                em.merge(pr);

            }

        }

    }

    @Override
    public Map<String, String> sendEmailRepeatdly(String emailRecip, String subject, String texte, List<String> listAttachedFile) {
        //  Session session = Session.getInstance(props, null);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        Map<String, String> map = new HashMap<>();
        String isMsgSent = "0";
        long sizeMessage = 0;
        long elapsedTime = 0;
        try {
            MimeMessage message = new MimeMessage(session);
            String address = emailRecip;
            InternetAddress[] iAdressArray = InternetAddress.parse(address);

            message.setRecipients(Message.RecipientType.TO, iAdressArray);
            message.setSubject(subject);
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(texte);

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            for (String attachedFile : listAttachedFile) {
                if (attachedFile != null) {
                    addAttachment(mp, attachedFile);
                }
            }
            message.setContent(mp);

            try {
                long startTime = System.currentTimeMillis();
                Transport tr = session.getTransport("smtp");
                tr.connect(host, username, password);
                //  tr.sendMessage(message,message.getAllRecipients() );
                for (Address recipient : message.getAllRecipients()) {
                    tr.sendMessage(message, new Address[]{recipient});
                }

                elapsedTime = System.currentTimeMillis() - startTime;

                ByteArrayOutputStream bais = new ByteArrayOutputStream();
                message.writeTo(bais);

                sizeMessage = bais.size();
                isMsgSent = "1";
                tr.close();

            } catch (SendFailedException sfe) {
                System.out.println(sfe);
            } catch (IOException ex) {
                Logger.getLogger(SMTPRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (MessagingException e) {
        }

        map.put("isMailSent", isMsgSent);
        map.put("size", sizeMessage + "");
        map.put("elapsedTime", elapsedTime + "");

        return map;

    }

    private static Multipart addAttachment(Multipart multipart, String filename) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        return multipart;
    }

    private Properties getSMTPProps(List<SMTPProperties> listSMTPProperties) {

        Properties props = new Properties();
        for (SMTPProperties pr : listSMTPProperties) {
            if (!(pr.getLibelle().equals(MAIL_USER)) && (!pr.getLibelle().equals(MAIL_PASSWORD))) {
                props.put(pr.getLibelle(), pr.getValueSMTP());
            }
            if (pr.getLibelle().equals(MAIL_USER)) {
                username = pr.getValueSMTP();
            }
            if (pr.getLibelle().equals(MAIL_PASSWORD)) {
                password = pr.getValueSMTP();
            }
            if (pr.getLibelle().equals(MAIL_HOST)) {

                host = pr.getValueSMTP();
            }

        }
        return props;

    }

    private List<SMTPProperties> findSMTPProperties() {
        try {
            return em.createQuery("select sp from SMTPProperties sp", SMTPProperties.class)
                    .getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
