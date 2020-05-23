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
        User user = Member.getLoggeedUser();
        List list = user.contacts;
        render(list);
    }
    
    /**
     * This methods renders the add view. 
     * If present, it will send the item to the view to pre-populate the inputs (it happens when validation fails)
     * @param item 
     */
    public static void add(Item item){
        renderArgs.put("item", item);
        render();
    }
    
    public static void edit(@Required long id){
        Item item = Item.findById(id);
        if(item == null){
            flash.error("Contact not found!" ); 
            getAll();
        }
        
        String dobString = "";
        if(item !=null && item.dob != null) //avoid dob being null
            dobString = Application.formatDate(item.dob);
        
        renderArgs.put("item", item);
        renderArgs.put("dob", dobString);
        render();
    }
    
    public static void addAction(@Valid Item item, String action){
        if(validation.hasErrors()) {              
            params.flash(); // add http parameters to the flash scope
            Validation.keep(); // keep the errors for the next request (because we call the method to render after)
            /*
             * If needed or desired, messages can be passed with the following two lines for a more custom settings
            flash.put("message", "There are required fields missing");
            flash.put("messageType", "warning");
            */
            flash.error("There are required fields missing" );            
            add(item); //pass the item as an argument to pre-populate the fields
        }
        
        //gets logged user to associate contacts to it
        String loggedUserEmail = Security.connected();
        User user = User.find("email", loggedUserEmail).first();        
        
        //associates user id to item foreing key
        item.user = user;
        
        //edit just updates a single contact
        if(action.equals("edit")){               
            item.edit("item", params.all());
            item.save(); //explicit save
            flash.success("Contact edited successfully!");
        
        //save creates a new item, associates to user and updates user
        }else{                        
            item.save();
            flash.success("Contact added successfully!");
        }
        
        getAll();
    }
    
    /**
     * Returns a JSON of one item given an id 
     * @param id      
     */
    public static void getOneJsonAction(@Required long id){
        if(Validation.hasErrors()) {
            params.flash(); 
            Validation.keep();
            getAll();
        }
        
        Item item = Item.findById(id);
        item.user = null; //to avoid circular referencer expection
        renderJSON(item, Item.class);
    }
    
    public static void deleteAction(@Required long id) {
        if(Validation.hasErrors()) {
            flash.error("There are required fields missing" );
            params.flash(); 
            Validation.keep();
            getAll();
        }
        
        Item item = Item.findById(id);
        item.delete();
        flash.success("%s was deleted successfully!", item.firstname);        
        getAll();
    }
        
}