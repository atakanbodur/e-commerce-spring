package com.example.ecommercebackend.service;

import com.example.ecommercebackend.model.Customer;
import com.example.ecommercebackend.model.Order;
import com.example.ecommercebackend.repository.CustomerRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CustomerRepository customerRepository;

    private static final String SENDGRID_API_KEY = "";

    private CompletableFuture<Void> sendEmailAsync(String toEmail, String subject, String content) {
        return CompletableFuture.runAsync(() -> sendEmail(toEmail, subject, content));
    }

    private CompletableFuture<Void> sendCheckoutEmailAsync(String toEmail, String subject, String content, byte[] invoiceBytes) {
        return CompletableFuture.runAsync(() -> sendEmailWithAttachment(toEmail, subject, content, invoiceBytes));
    }

    public CompletableFuture<Void> sendWelcomeCustomerEmailAsync(Customer customer) {
        String subject = "Welcome to GATO !";
        String content = "<h1 style='font-family: Arial, sans-serif; font-size: 24px; color: #333;'>Dear " + customer.getName() + ",</h1>" +
                "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>We are delighted to see you in GATO. As a registered user of our website, you are now eligible to purchase products from our platform. We hope that your shopping experience with us exceeds your expectations. Stay tuned for exciting new arrivals, special offers, and seasonal promotions exclusive to our valued customers.</p>" +
                "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>If you have any further questions or concerns, you can contact us at <a href='mailto:gatocustomerservice@gmail.com'>gatocustomerservice@gmail.com</a>.</p>" +
                "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>Happy shopping!</p>";

        return sendEmailAsync(customer.getEmail(), subject, content);
    }

    public CompletableFuture<Void> sendCheckoutEmailAsync(Order order) {
            byte[] pdfContent = invoiceService.generateInvoicePdf(order); // You need to have this method available in EmailService
            Customer customer = customerRepository.getById(order.getCustomerid());
            String toEmail = customer.getEmail();
            String subject = "Your GATO Invoice";
            String content = "<h1 style='font-family: Arial, sans-serif; font-size: 24px; color: #333;'>Dear " + customer.getName() + ",</h1>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>Thank you for your purchase! Please find your invoice attached to this email.</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>If you have any questions, please contact us at <a href='mailto:gatocustomerservice@gmail.com'>gatocustomerservice@gmail.com</a>.</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>Best regards,</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 16px; color: #333; line-height: 24px;'>GATO Team</p>";

            return sendCheckoutEmailAsync(toEmail, subject, content, pdfContent);
    }

    public void sendEmail(String toEmail, String subject, String content) {
        Email from = new Email("gatocustomerservice@gmail.com");
        Email to = new Email(toEmail);
        Content emailContent = new Content("text/html", content);
        Mail mail = new Mail(from, subject, to, emailContent);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void sendEmailWithAttachment(String toEmail, String subject, String content, byte[] invoiceBytes) {
        Email from = new Email("gatocustomerservice@gmail.com");
        Email to = new Email(toEmail);
        Content emailContent = new Content("text/html", content); // Use HTML content
        Mail mail = new Mail(from, subject, to, emailContent);

        Attachments attachment = new Attachments();
        attachment.setContent(Base64.encodeBase64String(invoiceBytes));
        attachment.setType("application/pdf");
        attachment.setFilename("invoice.pdf");
        attachment.setDisposition("attachment");
        mail.addAttachments(attachment);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
