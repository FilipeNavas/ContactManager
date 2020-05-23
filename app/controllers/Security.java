/*
 */

package controllers;

import models.User;

/**
 *
 * @author filipe navas
 */
public class Security extends Secure.Security {
    
    static boolean authenticate(String username, String password) {
        User user = User.find("email", username).first();
        return user != null && user.password.equals(password);          
    }
    
    static void onAuthenticated(){
        Application.loginProcess();
    }
    
    static void onDisconnected(){
        Application.logoutProcess();
    }
}
