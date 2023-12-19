package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendSignInEmail(Users user){
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"UTF-8");
        String to=user.getEmailAddress();
        String sub="Accio shopping account has created";

        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            Context context=new Context();

            String htmlContent=templateEngine.process("signIn",context);
            htmlContent=htmlContent.replace("${name}",user.getUserName());
            htmlContent=htmlContent.replace("${role}",user.getRole().toString());
            htmlContent=htmlContent.replace("${address}",user.getAddress());
            htmlContent=htmlContent.replace("${PhoneNumber}",user.getPhoneNumber()+"");
            htmlContent=htmlContent.replace("${isAminapprove}",user.isAdminApprove()+"");
            mimeMessageHelper.setText(htmlContent,true);
            javaMailSender.send(mimeMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void sendOrderPlaceMail(Users user, Orders order){
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,"UTF-8");
        String to=user.getEmailAddress();
        //System.out.println(to);
        String sub="Congratulations !! you order got placed successfully";

        Context context=new Context();
//        context.setVariable("userName",user.getUserName());
//        context.setVariable("orderId",order.getId());
//        context.setVariable("estimatedDate",order.getEstimatedDelivery()+"");
        try{
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);


            String htmlContent=templateEngine.process("placedOrder",context);
            htmlContent=htmlContent.replace("${name}",user.getUserName());
            htmlContent=htmlContent.replace("${orderId}",order.getId()+"");
            htmlContent=htmlContent.replace("${totalItems}",order.getTotalOrderItems()+"");
            htmlContent=htmlContent.replace("${totalPrice}",order.getTotalOrderPrice()+"");
            htmlContent=htmlContent.replace("${estimateDate}",order.getEstimatedDelivery()+"");
            mimeMessageHelper.setText(htmlContent,true);
            javaMailSender.send(mimeMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
