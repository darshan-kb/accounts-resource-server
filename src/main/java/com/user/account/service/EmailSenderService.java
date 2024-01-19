package com.user.account.service;



import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderService implements EmailSender{
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender mailSender;
    @Override
    @Async
    public void send(String to, String email, String subject) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setText(email, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom("noreply@spingame.com");
            mailSender.send(mimeMessage);
        }
        catch(Exception e){
            LOGGER.error("Failed to send the email",e);
            throw new IllegalStateException();
        }
    }

    public void rechargeEmail(String to, String user, String otp, double amount){
        send(to,buildEmail(otp,user,amount),"Recharge OTP");
    }

    public String buildEmail(String otp,String user, double amount){
        return """
                <div style="font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2">
                  <div style="margin:50px auto;width:70%;padding:20px 0">
                    <div style="border-bottom:1px solid #eee">
                      <a href="" style="font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600">Game</a>
                    </div>
                    <p style="font-size:1.1em">Hi,</p>"""+
                    """
                    <p> Use the following OTP for recharge amount of"""+amount+"""
                    to+"""+user+"""
                    . OTP is valid for 15 minutes</p>
                    <h2 style="background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;">"""
                    +otp+"""
                    </h2>
                    <p style="font-size:0.9em;">Regards,<br />Game</p>
                    <hr style="border:none;border-top:1px solid #eee" />
                  </div>
                </div>
                """;
    }
}
