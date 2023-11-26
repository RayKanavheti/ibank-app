package com.equals.customersupportservice.consumers;

import com.equals.customersupportservice.domain.SupportTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.function.Consumer;

@Slf4j
@Configuration
public class SupportTicketConsumer {

    @Bean
    public Consumer<SupportTicket> supportTickerBinding() {
        return (supportTicket) -> {
            try {

                log.info("Received Alert for New Support Ticket: " + supportTicket);
                sendEmailNotification(supportTicket);
                sendPushNotification(supportTicket);

            } catch (Exception e) {
                log.error("Error processing support ticket: {}", supportTicket, e);
            }
        };
    }

    private void sendEmailNotification(SupportTicket supportTicket) {

        String subject = "New Support Ticket Created";
        String message = "A new support ticket has been created.\nTicket ID: " + supportTicket.getId()
                + "\nCustomer ID: " + supportTicket.getCustomerId()
                + "\nIssue: " + supportTicket.getIssue();

       // emailService.sendEmail("support@example.com", subject, message);
    }

    private void sendPushNotification(SupportTicket supportTicket) {

        String title = "New Support Ticket Created";
        String body = "Ticket ID: " + supportTicket.getId()
                + "\nCustomer ID: " + supportTicket.getCustomerId()
                + "\nIssue: " + supportTicket.getIssue();
      //  pushNotificationService.sendPushNotification("Support Team", title, body);
    }

}
