package com.ecommerce.backend.registration.listener;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.registration.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        User user = onRegistrationCompleteEvent.getUser();
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String message = messages.getMessage("cool",null, Locale.ENGLISH );
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message );
        email.setFrom("noreply@baeldung.com");
        return email;
    }
}
