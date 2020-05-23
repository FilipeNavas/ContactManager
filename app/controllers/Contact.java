package controllers;

import java.util.List;
import models.Item;
import models.User;
import play.data.validation.*;
import play.mvc.Controller; 
import play.mvc.With;

/**
 *
 * @author filipe navas
 */

@With(Secure.class)
public class Contact extends Controller{

    /**
     * Get all contacts from the logged user
     */
    public static void getAll() {
        User user = Member.getLoggedUser();
        List list = user.contacts;
        render(list);
    }
    
    /**
     * This methods renders the add view. 
     * If present, it will send the contact to the view to pre-populate the inputs (it happens when validation fails)
     * @param contact 
     */
    public static void add(Item contact){
        renderArgs.put("contact", contact);
        render();
    }
    
    public static void edit(@Required long id){
        Item contact = Item.findById(id);
        if(contact == null){
            flash.error("Contact not found!" ); 
            getAll();
        }
        
        String dobString = "";
        if(contact !=null && contact.dob != null) //avoid dob being null
            dobString = Application.formatDate(contact.dob);
        
        renderArgs.put("contact", contact);
        renderArgs.put("dob", dobString);
        render();
    }
    
    public static void addAction(@Valid Item contact, String action){
        if(validation.hasErrors()) {              
            params.flash(); // add http parameters to the flash scope
            Validation.keep(); // keep the errors for the next request (because we call the method to render after)
            /*
             * If needed or desired, messages can be passed with the following two lines for a more custom settings
            flash.put("message", "There are required fields missing");
            flash.put("messageType", "warning");
            */
            flash.error("There are required fields missing" );            
            add(contact); //pass the contact as an argument to pre-populate the fields
        }
        
        //gets logged user to associate contacts to it
        String loggedUserEmail = Security.connected();
        User user = User.find("email", loggedUserEmail).first();        
        
        //associates user id to contact foreing key
        contact.user = user;
        
        //edit
        if(action.equals("edit")){               
            contact.edit("contact", params.all());
            contact.save(); //explicit save
            flash.success("Contact edited successfully!");
        
        //save creates a new contact
        }else{                        
            contact.save();
            flash.success("Contact added successfully!");
        }
        
        getAll();
    }
    
    /**
     * Returns a JSON of one contact given an id (used in the view contact modal)
     * @param id      
     */
    public static void getOneJsonAction(@Required long id){
        if(Validation.hasErrors()) {
            params.flash(); 
            Validation.keep();
            getAll();
        }
        
        Item contact = Item.findById(id);
        contact.user = null; //to avoid circular referencer expection
        renderJSON(contact, Item.class);
    }
    
    public static void deleteAction(@Required long id) {
        if(Validation.hasErrors()) {
            flash.error("There are required fields missing" );
            params.flash(); 
            Validation.keep();
            getAll();
        }
        
        Item contact = Item.findById(id);
        contact.delete();
        flash.success("%s was deleted successfully!", contact.firstname);        
        getAll();
    }
        
}