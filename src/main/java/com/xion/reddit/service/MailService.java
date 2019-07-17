package com.xion.reddit.service;

import com.xion.reddit.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);
    private final String BASE_URL = "http://localhost:8080";
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultiPart, boolean isHtml) {
        logger.debug("Sending Emails");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("noreply@springit.com");
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String subject) {
        Locale locale = Locale.ENGLISH;
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseURL", BASE_URL);
        String content = springTemplateEngine.process(templateName, context);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        logger.debug("Sending activation email to {}", user.getEmail());
        sendEmailFromTemplate(user, "email/activation", "Springit User Activation");
    }

    @Async
    public void sendWelcomeEmail(User user) {
        logger.debug("Sending welcome emial to {}", user.getEmail());
        sendEmailFromTemplate(user, "email/welcome", "Welcome New Springit User");
    }
}
