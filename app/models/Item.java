package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.data.validation.Email;
import play.data.validation.InPast;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;


/**
 *
 * @author filipe
 */

@Entity 
public class Item extends Model{
    
    @Required
    public String firstname;
    
    @Required
    public String lastname;
    
    @Email
    public String email;
    
    @Required
    @Phone
    public String phone;
    
    @InPast
    public Date dob;
    
    @ManyToOne
    public User user;
    
    public Item(){}

    public Item(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    
}
