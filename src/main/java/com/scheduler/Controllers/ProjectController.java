package com.scheduler.Controllers;

import com.scheduler.Messaging.SMSRequest;
import com.scheduler.Messaging.SMSResponse;
import com.scheduler.Models.Meeting;
import com.scheduler.Models.User;
import com.scheduler.Repositories.MeetingRepository;
import com.scheduler.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ProjectController {

    //Schedule send mails
    //Send email

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);
    private MeetingRepository meetingRepository;
    private UserRepository userRepository;
    private JavaMailSender mailSender;
    private SMSResponse smsResponse;
    private SMSRequest smsRequest;

    @Autowired
    public ProjectController(MeetingRepository meetingRepository, UserRepository userRepository, JavaMailSender mailSender, SMSResponse smsResponse, SMSRequest smsRequest){
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.smsResponse = smsResponse;
        this.smsRequest = smsRequest;
    }

    /* Create User */
    @GetMapping("/create_user")
    public String getCreateUserForm(Model model){
        model.addAttribute("user", new User());
        return "create_user";
    }

    @PostMapping("/register")
    public String registerUser(User user){
        userRepository.save(user);
        return "redirect:/create_user";
    }


    /* Create Meeting */
    @GetMapping("/create_meeting")
    public String getCreateMeetingForm(Model model){
        List<User> users = userRepository.findAll();

        model.addAttribute("meeting", new Meeting());
        model.addAttribute("allUsers", users);


        //Get today's meetings
        List<Meeting> meetings = meetingRepository.findMeetingsToday();

        for (Meeting meeting : meetings){
            System.out.println(meeting.getName());
            for (User user : meeting.getUsers()){
                System.out.println(user.getName());
            }
        }
        return "create_meeting";
    }

    @PostMapping("/schedule_meeting")
    public String scheduleMeeting(Meeting meeting){
        meetingRepository.save(meeting);
        return "redirect:/create_meeting";
    }

    private void sendMail(User user, Meeting meeting) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //Get email Details
        String email = user.getEmailAddress();
        String fullNames = user.getName();

//        int ownerId = meeting.getOwnerId();
//        Optional<User> owner = userRepository.findById(ownerId);

        helper.setFrom("staff@mop.com", "Meeting Office Planner");
        helper.setTo(email);

        String subject = "Meeting Notification";

        String content = "<p>Hi " + fullNames + ",</p>"
                + "<p>Your " + meeting.getName() + " is scheduled to happen today at " + meeting.getStartTime() + ".</p>"
                + "<p>Purpose to attend.</p>"
                + "<p><b>Regards,</b></p>"
                + "<p><b>Meeting Office Planner</b></p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    void sendSMS(User user, Meeting meeting){

        String phoneNumber = user.getPhoneNumber();
        String message = "Hi "+ user.getName() + ". " +
                "Your " + meeting.getName() + " meeting is scheduled to happen today at " + meeting.getStartTime() + ". " +
                "Purpose to attend, Thank You!";


        smsRequest.setPhoneNumber(phoneNumber);
        smsRequest.setMessage(message);

        smsResponse.sendSMS(smsRequest);

    }

    @Scheduled(cron = "0 0 6 * * *")
    void sendMails() throws MessagingException, UnsupportedEncodingException {

        //Get today's meetings
        List<Meeting> meetings = meetingRepository.findMeetingsToday();

        for (Meeting meeting : meetings){

            //send to owner first
            int ownerId = meeting.getOwnerId();
            User owner = userRepository.findById(ownerId);

            sendMail(owner, meeting);

            //Get meetings users
            for (User user : meeting.getUsers()){

                //send email to them
                sendMail(user, meeting);
                System.out.println("Mail Sent at " +LocalTime.now());
            }
        }
    }



    // TODO: Verify twilio numbers to send sms
    @Scheduled(cron = "0 0 6 * * *")
    void sendManySms(){

        //Get today's meetings
        List<Meeting> meetings = meetingRepository.findMeetingsToday();

        for (Meeting meeting : meetings){

            //send to owner first
            int ownerId = meeting.getOwnerId();
            User owner = userRepository.findById(ownerId);

            sendSMS(owner, meeting);
            System.out.println("SMS sent to " + owner.getName() +" at " + LocalTime.now());

            //Get meetings users
            for (User user : meeting.getUsers()){

                //send sms to co owners
                sendSMS(user, meeting);
                System.out.println("SMS sent to " + user.getName() +" at " + LocalTime.now());
            }
        }

    }
}
