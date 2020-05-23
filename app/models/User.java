package models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 *
 * @author filipe
 */

@Entity
public class User extends Model{
    
    @Required
    public String firstname;
    
    @Required
    public String lastname;
    
    @Required
    @Email
    public String email;
    
    @Required
    @Password
    public String password;
    
    public int birthdayReminderHours;
       
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Item> contacts;
    
}
