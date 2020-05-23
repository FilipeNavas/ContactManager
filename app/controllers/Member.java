/*
 */

package controllers;

import static controllers.Contact.add;
import play.mvc.Controller;
import play.mvc.With;
import models.User;
import play.data.validation.Valid;
import play.data.validation.Validation;

/**
 *
 * @author filipe navas
 */

@With(Secure.class)
public class Member extends Controller{
    
    protected static User getLoggeedUser(){
        String loggedUserEmail = Security.connected();
        return User.find("email", loggedUserEmail).first();
    }

    public static void settings(){
        User user = getLoggeedUser();
        
        if(user == null){
            flash.error("User not found!" ); 
            Contact.getAll();
        }
        
        render(user);
    }
    
    public static void settingsAction(@Valid User user, String password){
        if(validation.hasErrors()) {              
            params.flash(); // add http parameters to the flash scope
            Validation.keep(); // keep the errors for the next request (because we call the method to render after)
            flash.error("There are required fields missing" );            
            settings(); //pass the item as an argument to pre-populate the fields
        }
        
        if(!password.isEmpty()){
            flash.put("message", "You changed your password!");
            user.password = password;
        }
        
        user.save();
        flash.success("Settintgs updated successfully!");
        settings();
    }
    
}
