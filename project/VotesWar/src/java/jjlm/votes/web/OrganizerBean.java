/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;

@SessionScoped
@Named
public class OrganizerBean extends AbstractBackingBean implements Serializable {

    private static final long serialVersionUID = -452973949961650456L;

    @EJB
    private VotesLogic logic;

    private OrganizerTO organizer = null;

    private String name = "henny@uni-koblenz.de";
    private String email;

    public OrganizerBean() {
        System.out.println("hello world");

    }

    public OrganizerTO getOrganizer() {
        return organizer;
    }

    public void storeOrganizer() {
        logic.storeOrganizer(organizer);
    }

    public void setOrganizer(OrganizerTO to) {
        this.organizer = to;
    }

    public void setOrganizer(String email) {
        organizer = logic.getOrganizer(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }

    public String getName() {

        System.out.println("--------------");
        if (email != null) {
            return logic.getOrganizer(email).getRealname();
        }
        return "";
    }

}
