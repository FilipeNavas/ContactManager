/*
 */
package notifiers;

import java.text.SimpleDateFormat;
import models.Item;
import models.User;
import play.mvc.Mailer;
import static play.mvc.Mailer.addRecipient;
import static play.mvc.Mailer.setFrom;

/**
 *
 * @author filipe navas
 */
public class Mails extends Mailer {

    public static void reminder(User user, Item contact) {
        setSubject("Birthday Reminder for %s", contact.firstname.concat(" ").concat(contact.lastname));
        addRecipient(user.email);
        setFrom("Contact Manager <no-reply@contactmanager.com>");

        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        String birthday = formatter.format(contact.dob);

        send(user, contact, birthday);
    }

}
