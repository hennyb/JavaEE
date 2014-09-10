/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;

@SessionScoped
@Named
public class OrganizerBean extends AbstractBackingBean implements Serializable{
    private static final long serialVersionUID = -452973949961650456L;
    
    @EJB
    private VotesLogic logic;
    
    private OrganizerTO organizer;

    public OrganizerTO getOrganizer() {
        return organizer;
    }
    
    public void storeOrganizer(){
        
        logic.storeOrganizer(organizer);
    }
    
    
}
