package controllers;

import java.text.SimpleDateFormat;
import play.mvc.*;

import java.util.*;

import models.*;
import play.data.validation.Valid;
import play.data.validation.Validation;

public class Application extends Controller {
    
    public static void index() {
        render();
    }
    
    public static void loginProcess(){
        flash.success("Welcome"); 
        Contact.getAll();
    }            
    
    public static void logoutProcess() {
        //logout
        flash.success("Bye!"); 
        index();        
    }
    
    public static void signup() {
        render();
    }
    
    public static void signupAction(@Valid User user) {
        if(Validation.hasErrors()) {   
            flash.error("There are required fields missing" );              
            params.flash(); // add http parameters to the flash scope
            Validation.keep(); // keep the errors for the next request (because we call the method to render after)
            signup();
        }
        
        user.save(); 
        flash.success("Welcome %s", user.firstname);
        Contact.getAll();
    }
    
    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    
}