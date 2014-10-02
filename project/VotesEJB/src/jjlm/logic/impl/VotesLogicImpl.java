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
import jjlm.votes.persistence.PollAccess;
import jjlm.votes.persistence.entities.AbstractEntity;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
@Stateless
public class VotesLogicImpl implements VotesLogic {

    @EJB
    private OrganizerAccess oa;

    @EJB
    private PollAccess pa;

    @Override
    public OrganizerTO lookupUser(String uid) {
        return UnikoLdapLookup.lookupPerson(uid);
    }

    @Override
    public OrganizerTO getUser(String uid) {
        return getUserEntity(uid).createTO();
    }

    private Organizer getUserEntity(String uid) {
        OrganizerTO ldapUser = lookupUser(uid);
        if (ldapUser == null) {
            return null;
        }
        Organizer o = oa.findByName(uid);
        if (o == null) {
            o = new Organizer();
            o.setRealname(ldapUser.getRealname() + "fromldap");
            o.setName(ldapUser.getUsername() + "fromldap");
            o.setEmail(uid + "@uni-koblenz.de");
            o.setUsername(uid);
            oa.create(o);
            return o;
        } else {
            o.setUsername(ldapUser.getRealname());
            return oa.edit(o);
        }
    }

    @Override
    public OrganizerTO getOrganizer(String email) {
        Organizer o = oa.findOrganizer(email);

        if (o == null) {
            throw new IllegalArgumentException("Organizer with email: " + email + " does not exist");
        }

        OrganizerTO to = o.createTO();

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
        //organizer.setPolls(to.getPolls());
        organizer.setRealname(to.getRealname());
        organizer.setUsername(to.getUsername());

        if (to.getId() == null) {
            oa.create(organizer);
        }
    }

    @Override
    public OrganizerTO findFirst() {
        List<Organizer> all = oa.findAll();

        if (all.isEmpty()) {
            return new Organizer().createTO();
        }
        return all.get(0).createTO();

    }

    @Override
    public String getPlainString() {
        return "halllllllo";
    }

    @Override
    public List<PollTO> getAllPolls() {

//        ArrayList<PollTO> result = new ArrayList<>();
//        
//        System.out.println("sizeee " +pa.getAllPolls().size());
//        
//        for(Poll poll : pa.getAllPolls())
//            result.add(poll.createTO());
        return AbstractEntity.createTransferList(pa.getAllPolls());
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID) {
        return AbstractEntity.createTransferList(pa.getPolls(organizerID));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(OrganizerTO to) {
        return AbstractEntity.createTransferList(pa.getPolls(to.getId()));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID, int offset, int max) {
        System.out.println("listsize: "+pa.getAllPolls().size());
        return AbstractEntity.createTransferList(pa.getAllPolls());
    }
}
