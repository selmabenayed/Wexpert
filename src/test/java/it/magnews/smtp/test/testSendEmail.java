package it.magnews.smtp.test;

import it.magnews.smtp.configurations.AppConfig;
import it.magnews.smtp.entities.SMTPMessage;
import it.magnews.smtp.entities.SMTPProperties;
import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPMessageRepository;
import it.magnews.smtp.repository.SMTPPropertiesRepository;
import it.magnews.smtp.repository.SMTPRepository;
import it.magnews.smtp.repository.UserAppRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author sbenayed
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ FacesContext.class })
public class testSendEmail {

    @Autowired
    public SMTPRepository sMTPRepository;
    @Autowired
    private SMTPMessageRepository sMTPMessageRepository;
    // private SMTPMessage sMTPMessage;

    @Autowired
    private UserAppRepository UserAppRepository;
    @Autowired
    private SMTPPropertiesRepository sMTPPropertiesRepository;

    private SMTPUser connectedUser;

    // the File path must be updated according the local project (Just for the test here)
    private static final String FILE_PATH = "D:/1-SELMA_DATA/MagNEWS/SMTPBenchmarkProject/src/main/webapp/files/MagNewsDeveloperTests.docx";

    // @Mock
    // private FacesContext faceContext;
    @Before
    public void setup() throws Exception {
        //Calendar today = Calendar.getInstance();
        //sMTPMessage = sMTPMessageRepository.findSMTPMessageByDate(today.getTime());
        createUserAccount();
        CreateSMTPProperties();

        connectedUser = new SMTPUser();
        connectedUser.setIdUser(1);

        // PowerMockito.mockStatic(FacesContext.class);
        // PowerMockito.doReturn(this.faceContext).when(FacesContext.class, "getCurrentInstance");
    }

    @Test
    public void sendEmailTest() {

        String attachedFile = null;// faceContext.getExternalContext().getRealPath("") + FILE_PATH;
        List<String> listAttchedFile = new ArrayList<>();
        listAttchedFile.add(attachedFile);
        String emailRecip = "selma.bn.ayed@gmail.com,selma.ensi@gmail.com";
        String subject = "Subject";
        String texte = "messssssggages...";

        Map<String, String> map = sMTPRepository.sendEmail(emailRecip, subject, texte, listAttchedFile);
        boolean isMailSent = false;
        String intMailSent = map.get("isMailSent");
        if (intMailSent.equals("1")) {
            isMailSent = true;
        }

        Assert.assertEquals(isMailSent, true);

    }

    //@Test
    public void sendEmailRepeatdlyTest() {

        String attachedFile = null;// faceContext.getExternalContext().getRealPath("") + FILE_PATH;

        List<String> listAttchedFile = new ArrayList<>();
        listAttchedFile.add(attachedFile);
        String emailRecip = "selma.bn.ayed@gmail.com,selma.ensi@gmail.com";
        String subject = "Subject email repeatdly";
        String texte = "messssssggages...";

        Map<String, String> maprepeatdly = sMTPRepository.sendEmailRepeatdly(emailRecip, subject, texte, listAttchedFile);
        boolean isMailSentReaptdly = false;
        String intMailSentRepeatdlyInt = maprepeatdly.get("isMailSent");
        if (intMailSentRepeatdlyInt.equals("1")) {
            isMailSentReaptdly = true;
        }
        Assert.assertEquals(isMailSentReaptdly, true);

        Map<String, String> map = sMTPRepository.sendEmail(emailRecip, subject, texte, listAttchedFile);
        boolean isMailSent = false;
        String intMailSentint = maprepeatdly.get("isMailSent");
        if (intMailSentint.equals("1")) {
            isMailSent = true;
        }
        Assert.assertEquals(isMailSent, true);

        String sizeRepeatdly = maprepeatdly.get("size");
        String elapsedTimeRepeatdly = maprepeatdly.get("elapsedTime");
        String size = map.get("size");
        String elapsedTime = map.get("elapsedTime");

        System.out.println("sizeRepeatdly= " + sizeRepeatdly);
        System.out.println("size= " + size);
        System.out.println("elapsedTimeRepeatdly= " + elapsedTimeRepeatdly);
        System.out.println("elapsedTime= " + elapsedTime);
    }

    //  @Test
    public void testMinMaxAvgMessageDelivery() {
        Map<String, String> mapExpected = new HashMap<>();
        mapExpected.put("max", "10");
        mapExpected.put("min", "2");
        mapExpected.put("avg", "6");

        List<SMTPMessage> listSMTPMessage = sMTPMessageRepository.findSMTPMessageByUser(connectedUser);
        Map<String, String> map = sMTPMessageRepository.getMinMaxAvgMessageDelivery(listSMTPMessage);
        Assert.assertEquals(mapExpected, map);
    }

    // @Test
    public void testSizeElapsedTime() {

        //email with attached file
        String attachedFile = FILE_PATH;/// FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + FILE_PATH;
        List<String> listAttchedFile = new ArrayList<>();
        listAttchedFile.add(attachedFile);
        List<String> listAttchedFileMulti = new ArrayList<>();
        listAttchedFileMulti.add(attachedFile);
        listAttchedFileMulti.add(attachedFile);
        String emailRecip = "selma.b.ayed@gmail.com";
        String subject = "Subject";
        String texte = "messssssggages...";

        Map<String, String> map = sMTPRepository.sendEmail(emailRecip, subject, texte, listAttchedFile);
        Map<String, String> mapMULTIFILES = sMTPRepository.sendEmail(emailRecip, subject, texte, listAttchedFileMulti);
        Integer size = Integer.parseInt(map.get("size"));
        Integer elapsedTime = Integer.parseInt(map.get("elapsedTime"));

        System.out.println("size= " + size);
        System.out.println("elapsedTime= " + elapsedTime);
        Integer sizem = Integer.parseInt(mapMULTIFILES.get("size"));
        Integer elapsedTimem = Integer.parseInt(mapMULTIFILES.get("elapsedTime"));

        System.out.println("sizem= " + sizem);
        System.out.println("elapsedTimem= " + elapsedTimem);

        Assert.assertTrue(size < sizem);

    }

    private void createUserAccount() {

        SMTPUser user = new SMTPUser();
        user.setEmail("selma.bn.ayed@gmail.com");///to be changed with correct one
        user.setFirstName("selma");
        user.setLastName("ben ayed");
        user.setIdUser(1);
        user.setUserName("selma");
        user.setPassword("pwd");///to be changed with correct one

        UserAppRepository.updateUser(user);

        SMTPUser existedUser = UserAppRepository.findUserByUsername("selma");
        Assert.assertNotNull(existedUser);

    }

    private void CreateSMTPProperties() {

        SMTPProperties sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(1);
        sMTPProperties.setLibelle("mail_user");
        sMTPProperties.setValueSMTP("selma.bn.ayed@gmail.com");///to be changed with correct one
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);

        sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(2);
        sMTPProperties.setLibelle("mail_password");
        sMTPProperties.setValueSMTP("pwd");/////to be changed with correct one
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);

        sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(3);
        sMTPProperties.setLibelle("mail.smtp.auth");
        sMTPProperties.setValueSMTP("true");
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);

        sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(4);
        sMTPProperties.setLibelle("mail.smtp.starttls.enable");
        sMTPProperties.setValueSMTP("true");
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);

        sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(5);
        sMTPProperties.setLibelle("mail.smtp.host");
        sMTPProperties.setValueSMTP("smtp.gmail.com");
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);

        sMTPProperties = new SMTPProperties();
        sMTPProperties.setIdsmtpProperties(6);
        sMTPProperties.setLibelle("mail.smtp.port");
        sMTPProperties.setValueSMTP("587");
        sMTPPropertiesRepository.createSMTPProperties(sMTPProperties);
    }

}
