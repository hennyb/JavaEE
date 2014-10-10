/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;

/**
 *
 * @author lukas
 */
@Named
@Stateless
public class MailerBean {

    @EJB
    protected VotesLogic logic;
    
    private PollTO poll;
    private int pollId;
    private TokenTO token;
    
    private static final Properties fMailServerConfig = new Properties();
    
    public void init(Integer pollId) {
        this.pollId = pollId;
        try {
            this.poll = logic.getPoll(this.pollId);
            
            List<ParticipantTO> participants = logic.getParticipantsOfPoll(this.pollId);
            
            for (ParticipantTO participant: participants) {
               participant.setToken(logic.getTokensOfPollAndParticipant(this.pollId, participant.getId()));
            }
                 
            this.poll.setParticipants(participants);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    @Asynchronous
    public void sendMails() {
        for (ParticipantTO participant: this.poll.getParticipants()) {
            this.sendMail(participant);
        }
    }
    
    private void sendMail(ParticipantTO participant) {
        try {
            Session session = Session.getDefaultInstance(fMailServerConfig, null);
            MimeMessage message = new MimeMessage(session);
            
            message.setSubject(this.poll.getTitle());
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(participant.getEmail()));
            message.setText(this.getMailBody(participant));
            
            Transport.send(message);
 
            Logger.getLogger(MailerBean.class.getName()).log(Level.INFO, "Mail sent to: {0}", participant.getEmail());
        } catch (MessagingException ex) {
            Logger.getLogger(MailerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getMailBody(ParticipantTO participant) {
        String result =  "Hi,\n";
        result += "Here is your Link to the Poll:  poll.xhtml?id=" + this.poll.getId();
        result += "Your Token is: " + participant.getToken().getSignature();
        return result;
    }

}
