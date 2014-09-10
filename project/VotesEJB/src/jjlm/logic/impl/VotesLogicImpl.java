/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.OrganizerAccess;
import jjlm.votes.persistence.entities.Organizer;

/**
 *
 * @author henny
 */
@Stateless
public class VotesLogicImpl implements VotesLogic {
    
    @EJB
    private OrganizerAccess oa;

    @Override
    public OrganizerTO getOrganizer(String email) {
        Organizer o = oa.findOrganizer(email);
    
        if(o == null)
        {
            throw new IllegalArgumentException("Organizer with email: "+email+" does not exist");
        }
        
        return o.createTO();
    }

    @Override
    public PollTO storePoll(PollTO to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
