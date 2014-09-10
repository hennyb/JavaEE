/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

        System.out.println(o.getRealname());
        if (o == null) {
            throw new IllegalArgumentException("Organizer with email: " + email + " does not exist");
        }
        
        System.out.println("getOrganizer");
        
        OrganizerTO to =o.createTO();
        
        return to;
    }

    @Override
    public PollTO storePoll(PollTO to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void storeOrganizer(OrganizerTO to) {

        Organizer organizer = to.getId() == null ? new Organizer() : oa.find(to.getId());

        if (organizer == null) {
            throw new IllegalArgumentException("Organizer does not exist");
        }

        organizer.setEmail(to.getEmail());
        organizer.setEncryptedPassword(to.getEncryptedPassword());
        organizer.setPolls(to.getPolls());
        organizer.setRealname(to.getRealname());
        organizer.setUsername(to.getUsername());

        if (to.getId() == null) {
            oa.create(organizer);
        }
    }
    
    @Override
    public OrganizerTO findFirst(){
        List<Organizer> all = oa.findAll();
        
        if(all.isEmpty())
            return new Organizer().createTO();
       return all.get(0).createTO();
   
    }

    @Override
    public String getPlainString() {
        return "halllllllo";
    }

}
