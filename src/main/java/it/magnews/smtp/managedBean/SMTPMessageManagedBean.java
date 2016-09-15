package it.magnews.smtp.managedBean;

/**
 *
 * @author sbenayed
 */
import it.magnews.smtp.entities.SMTPMessage;
import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.repository.SMTPMessageRepository;
import it.magnews.smtp.repository.SMTPRepository;
import it.magnews.smtp.repository.UserAppRepository;
import it.magnews.smtp.utils.JsfUtils;
import it.magnews.smtp.utils.SMTPUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "sMTPMessageManagedBean")
@ViewScoped
public class SMTPMessageManagedBean {

    private String text, reciever, object;

    private String min, max, avg;
    private String isMailSent, size, elapsedTime, isMailSentR, sizeR, elapsedTimeR;

    @ManagedProperty(value = "#{sMTPRepository}")
    private SMTPRepository sMTPRepository;

    @ManagedProperty(value = "#{login}")
    private Login login;

    @ManagedProperty(value = "#{sMTPMessageRepository}")
    private SMTPMessageRepository sMTPMessageRepository;

    @ManagedProperty(value = "#{userAppRepository}")
    private UserAppRepository userAppRepository;

    private SMTPMessage sMTPMessage;
    private SMTPUser connectedUser;

    public static DateFormat dateformat = new SimpleDateFormat("yyy-MM-dd");

    private static final String FILE_PATH = "/files/MagNewsDeveloperTests.docx";

    @PostConstruct
    void init() {
        Calendar today = Calendar.getInstance();
        sMTPMessage = sMTPMessageRepository.findSMTPMessageByDate(today.getTime());
        connectedUser = login.getConnectedUser();
    }

    public void sendMessage() {
        String attachedFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + FILE_PATH;
        List<String> listAttchedFile = new ArrayList<>();
        listAttchedFile.add(attachedFile);
        String[] recieverArray = reciever.split(",");

        if (SMTPUtils.isValidEmailAddress(recieverArray)) {
            Map<String, String> map = sMTPRepository.sendEmail(reciever, object, text, listAttchedFile);
            isMailSent = map.get("isMailSent");
            size = map.get("size");
            elapsedTime = map.get("elapsedTime");

            if (isMailSent.equals("1")) {

                if (sMTPMessage == null) {
                    Calendar today = Calendar.getInstance();
                    sMTPMessage = new SMTPMessage();
                    sMTPMessage.setCounter("1");
                    sMTPMessage.setDateMessage(today.getTime());
                    sMTPMessage.setIdUser(connectedUser);
                    sMTPMessageRepository.addSMTPMessage(sMTPMessage);

                } else {

                    int newCounter = Integer.parseInt(sMTPMessage.getCounter()) + 1;
                    sMTPMessage.setCounter(newCounter + "");
                    sMTPMessageRepository.updateSMTPMessage(sMTPMessage);

                }
                //JsfUtils.addSuccessMessage("Email sent");
                JsfUtils.executeScript("PF('timeSizeDialog').show();");

            } else {
                JsfUtils.addErrorMessage("Email not sent, Try again!");
            }
        } else {
            JsfUtils.addErrorMessage("Email of reciever not valid!");
        }

    }

    public void sendMessageR() {
        String attachedFile = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + FILE_PATH;
        List<String> listAttchedFile = new ArrayList<>();
        listAttchedFile.add(attachedFile);
        String[] recieverArray = reciever.split(",");

        if (SMTPUtils.isValidEmailAddress(recieverArray)) {

            Map<String, String> mapR = sMTPRepository.sendEmailRepeatdly(reciever, object, text, listAttchedFile);
            isMailSentR = mapR.get("isMailSent");
            sizeR = mapR.get("size");
            elapsedTimeR = mapR.get("elapsedTime");

            if (isMailSentR.equals("1")) {

                if (sMTPMessage == null) {
                    Calendar today = Calendar.getInstance();
                    sMTPMessage = new SMTPMessage();
                    sMTPMessage.setCounter("1");
                    sMTPMessage.setDateMessage(today.getTime());
                    sMTPMessage.setIdUser(connectedUser);
                    sMTPMessageRepository.addSMTPMessage(sMTPMessage);

                } else {

                    int newCounter = Integer.parseInt(sMTPMessage.getCounter()) + 1;
                    sMTPMessage.setCounter(newCounter + "");
                    sMTPMessageRepository.updateSMTPMessage(sMTPMessage);

                }
                //JsfUtils.addSuccessMessage("Email sent");
                JsfUtils.executeScript("PF('timeSizeDialogR').show();");

            } else {
                JsfUtils.addErrorMessage("Email not sent, Try again!");
            }
        } else {
            JsfUtils.addErrorMessage("Email of reciever not valid!");
        }

    }

    public void calculateMaxMinAvg() {

        List<SMTPMessage> listSMTPMessage = sMTPMessageRepository.findSMTPMessageByUser(connectedUser);
        if (listSMTPMessage != null && !listSMTPMessage.isEmpty()) {
            Map<String, String> map = sMTPMessageRepository.getMinMaxAvgMessageDelivery(listSMTPMessage);

            min = map.get("min");
            max = map.get("max");
            avg = map.get("avg");
            JsfUtils.executeScript("PF('minmaxavgDialog').show();");
        } else {
            JsfUtils.addWornMessage("No results to show, send SMTP message before");
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public SMTPRepository getsMTPRepository() {
        return sMTPRepository;
    }

    public void setsMTPRepository(SMTPRepository sMTPRepository) {
        this.sMTPRepository = sMTPRepository;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public SMTPMessageRepository getsMTPMessageRepository() {
        return sMTPMessageRepository;
    }

    public void setsMTPMessageRepository(SMTPMessageRepository sMTPMessageRepository) {
        this.sMTPMessageRepository = sMTPMessageRepository;
    }

    public UserAppRepository getUserAppRepository() {
        return userAppRepository;
    }

    public void setUserAppRepository(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getIsMailSent() {
        return isMailSent;
    }

    public void setIsMailSent(String isMailSent) {
        this.isMailSent = isMailSent;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getIsMailSentR() {
        return isMailSentR;
    }

    public void setIsMailSentR(String isMailSentR) {
        this.isMailSentR = isMailSentR;
    }

    public String getSizeR() {
        return sizeR;
    }

    public void setSizeR(String sizeR) {
        this.sizeR = sizeR;
    }

    public String getElapsedTimeR() {
        return elapsedTimeR;
    }

    public void setElapsedTimeR(String elapsedTimeR) {
        this.elapsedTimeR = elapsedTimeR;
    }

}
