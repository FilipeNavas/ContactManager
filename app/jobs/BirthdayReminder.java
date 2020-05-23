package jobs;

import java.util.Date;
import java.util.List;
import models.Item;
import models.User;
import notifiers.Mails;
import org.joda.time.LocalDateTime;
import play.Logger;
import play.jobs.Every;
import play.jobs.Job;

/**
 *
 * @author filipe
 */
@Every("1h")
public class BirthdayReminder extends Job {
    
    @Override
    public void doJob() {
        
        //time now without minutes or seconds
        LocalDateTime now = new LocalDateTime().withMinuteOfHour(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        
        //sends email if birthday is approaching
        List<User> users = User.findAll();
        
        Logger.info("Job send birthday reminders. Time now: " + now.toString());
        
        //iterate of users contact lists
         for(User u: users){
             
            //Improvement to be done: get only birthdays that are within a specified range (i.e. 1 month ahead)
            for(Item i: u.contacts){                
                if(getDifferenceHoursTwoDates(i.dob, now, u.birthdayReminderHours)){
                    Mails.reminder(u, i);
                }
            }
        }
        
    }
    
    public boolean getDifferenceHoursTwoDates(Date birthdayDate, LocalDateTime now, int hours){
        
        LocalDateTime birthday = new LocalDateTime(birthdayDate); //converts to JodaTime
        
        int year = now.plusHours(hours).getYear(); //make sure to get the correct year for the birthday (i.e. in december to get january)      
        birthday = birthday.withYear(year); //birthday to compare against (future date)

        //if now is equals to birthday minus X hours, this is equal to the reminder the user had set
        return now.isEqual(birthday.minusHours(hours));
    }
    
}
