package jjlm.votes.web;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.web.logic.HashGenerator;
import jjlm.votes.web.logic.ParticipantListParser;

/**
 * BackingBean to register new Users
 *
 */
@Named
@RequestScoped
public class RegisterBean {

    private String username;
    private String realname;
    private String password1;
    private String password2;
    private String email;

    @EJB
    private VotesLogic logic;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String register() {
        
	try {
	        Organizer organizer = new Organizer();
	        organizer.setEmail(email);
	        String salt = organizer.generatePasswordSalt();
	        String encryptedPassword = logic.encryptPassword(password1, salt);
	        organizer.setPasswordSalt(salt);
	        organizer.setEncryptedPassword(encryptedPassword);
	        organizer.setRealname(realname);
	        organizer.setUsername(username);

	        //logic.storeOrganizer(organizer);
	        logic.storeOrganizer(organizer.createTO());
	        return "index?faces-redirect=true";

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "index?faces-redirect=true";

    }

    public void validateEmail(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String emailString = (String) value;
        if (!logic.isOrganizerEmailUnique(emailString)) {
            FacesMessage message = new FacesMessage("Your mailadress is already in use");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
        if (!ParticipantListParser.isValidEmailAddress(emailString)) {
            FacesMessage message = new FacesMessage("Your mailadress is not valid");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }
    }

    public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        UIInput startDateComponent = (UIInput) (context.getViewRoot().findComponent("registerForm:password1"));

        if (startDateComponent == null || startDateComponent.getValue() == null) {
            FacesMessage message = new FacesMessage("Insert password");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

        String password1 = (String) startDateComponent.getValue();
        String password2 = (String) value;

        if (password1.length() < 6) {
            FacesMessage message = new FacesMessage("Password must contain at least 6 characters");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        } else if (!password1.equals(password2)) {
            FacesMessage message = new FacesMessage("Passwords are not equal");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

}
