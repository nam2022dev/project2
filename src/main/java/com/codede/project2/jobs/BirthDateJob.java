package com.codede.project2.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.codede.project2.entity.User;
import com.codede.project2.repo.UserRepo;
import com.codede.project2.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BirthDateJob {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    /// tim thang user co ngay sinh hom nay va
    // gui email chuc mung
    @Scheduled(cron = "0 * * * * *")
    public void sendEmailBirthdate() {
        System.out.println("Init birthdate job");
        // 1tim user co ngay sinh hom nay
        Calendar cal = Calendar.getInstance();
//

        List<User> users = userRepo.findByBirthdate(cal.get(Calendar.DATE),
                cal.get(Calendar.MONTH) + 1);

        for (User user : users) {
            log.info(user.getName());
            //send email , gia su username la email (to)
//            emailService.sendBirthday(user.getEmail(), user.getName());
//            emailService.sendTest();
        }
    }
}
