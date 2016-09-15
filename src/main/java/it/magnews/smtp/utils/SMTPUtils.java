package it.magnews.smtp.utils;

import it.magnews.smtp.entities.SMTPUser;
import it.magnews.smtp.dto.UserDTO;
import java.util.logging.Logger;

/**
 *
 * @author sbenayed
 */
public class SMTPUtils {

    private static final Logger LOG = Logger.getLogger(SMTPUtils.class.getName());

    public SMTPUtils() {
    }

    public static SMTPUser convertDtoTOEntity(UserDTO userDTO) {

        SMTPUser user = new SMTPUser();

        user.setIdUser(Integer.parseInt(userDTO.getId()));
        user.setUserName(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        return user;
    }

    public static UserDTO convertEntityToDto(SMTPUser entity) {

        UserDTO userDTO = new UserDTO();

        userDTO.setId(entity.getIdUser() + "");
        userDTO.setUsername(entity.getUserName());
        userDTO.setPassword(entity.getPassword());
        userDTO.setFirstName(entity.getFirstName());
        userDTO.setLastName(entity.getLastName());
        userDTO.setEmail(entity.getEmail());

        return userDTO;

    }

    public static boolean isValidEmailAddress(String[] emails) {

        boolean isValid = true;

        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        for (String email : emails) {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
            java.util.regex.Matcher m = p.matcher(email);
            if (!m.matches()) {
                return false;
            }

        }

        return isValid ;
    }
}
