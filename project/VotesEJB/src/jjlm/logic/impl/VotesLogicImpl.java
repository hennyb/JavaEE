/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jjlm.votes.persistence.entities.PollState;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.ItemAccess;
import jjlm.votes.persistence.ItemOptionAccess;
import jjlm.votes.persistence.OrganizerAccess;
import jjlm.votes.persistence.PollAccess;
import jjlm.votes.persistence.entities.AbstractEntity;
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.ItemOption;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;

@Stateless
public class VotesLogicImpl implements VotesLogic {

    @EJB
    private OrganizerAccess oa;

    @EJB
    private PollAccess pa;

    @EJB
    private ItemAccess ia;

    @EJB
    private ItemOptionAccess ioa;
    

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
        Poll poll = to.getId() == null ? new Poll() : pa.find(to.getId());

        poll.setId(to.getId());
        poll.setTitle(to.getTitle());
        poll.setDescription(to.getDescription());
        poll.setEndPoll(to.getEndPoll());
        poll.setStartPoll(to.getStartPoll());
        poll.setValid(to.isValid());

        Set<Organizer> organizer = poll.getOrganizer();
        for (Organizer o : organizer) {
            o.getPolls().remove(poll);
        }
        organizer.clear();

        for (OrganizerTO oto : to.getOrganizer()) {
            Organizer o = oa.find(oto.getId());
            organizer.add(o);
            o.getPolls().add(poll);
        }

        if (to.getId() == null) {
            pa.create(poll);
        } else {
            poll = pa.edit(poll);
        }

        return poll.createTO();
    }

    @Override
    public OrganizerTO storeOrganizer(OrganizerTO to) {

        Organizer organizer = to.getId() == null ? new Organizer() : oa.find(to.getId());

        organizer.setId(to.getId());
        organizer.setEmail(to.getEmail());
        organizer.setEncryptedPassword(to.getEncryptedPassword());
        //organizer.setPolls(to.getPolls());
        organizer.setPolls(new HashSet<Poll>());
        organizer.setRealname(to.getRealname());
        organizer.setUsername(to.getUsername());

        if (to.getId() == null) {
            oa.create(organizer);
        } else {
            organizer = oa.edit(organizer);
        }

        return organizer.createTO();
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
        return AbstractEntity.createTransferList(pa.getAllPolls());
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID) {
        return AbstractEntity.createTransferList(pa.getPolls(1));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(OrganizerTO to) {
        return AbstractEntity.createTransferList(pa.getPolls(to.getId()));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID, int offset, int max) {
        System.out.println("listsize: " + pa.getAllPolls().size());
        return AbstractEntity.createTransferList(pa.getAllPolls());
    }

    @Override
    public PollTO addOrganizerToPoll(int organizerId, int pollId) {
        PollTO poll = pa.addOrganizerToPoll(pollId, organizerId).createTO();

        return poll;
    }

    @Override
    public PollTO getPoll(String name, String description) {
        return pa.getPoll(name, description).createTO();
    }

    @Override
    public PollTO getPoll(int pollId) {
        return pa.find(pollId).createTO();
    }

    @Override
    public PollState getStateOfPoll(int pollId) {
        PollTO pTo = getPoll(pollId);

        if (pTo.getEndPoll() != null && pTo.getEndPoll().getTime() < new Date().getTime()) {
            return PollState.FINISHED;
        }
        if (pTo.getStartPoll() != null && pTo.getStartPoll().getTime() > new Date().getTime()) {
            return PollState.STARTED;
        }
        return PollState.PREPARED;
    }

    @Override
    public List<ItemTO> getItemsOfPoll(int poollId) {
        return AbstractEntity.createTransferList(ia.getItems(poollId));
    }

    @Override
    public ItemTO storeItem(ItemTO to) {

        Item item = to.getId() == null ? new Item() : ia.find(to.getId());

        item.setItemType(to.getItemType());
        item.setTitle(to.getTitle());
        item.setPoll(pa.find(to.getPoll().getId()));
        item.setId(to.getId());
        item.setValid(to.isValid());

        if (to.getId() == null) {
            ia.create(item);
        } else {
            item = ia.edit(item);
        }

        return item.createTO();

    }

    @Override
    public List<ItemOptionTO> getOptionsOfItem(int itemID) {
        return AbstractEntity.createTransferList(ioa.getOptions(itemID));
    }

    @Override
    public ItemOptionTO storeItemOption(ItemOptionTO to) {
        ItemOption option = to.getId() == null ? new ItemOption() : ioa.find(to.getId());

        option.setDescription(to.getDescription());
        option.setCount(to.getCount());
        option.setItem(ia.find(to.getItem().getId()));
        option.setTitle(to.getTitle());
        option.setId(to.getId());

        if (to.getId() == null) {
            ioa.create(option);
        } else {
            option = ioa.edit(option);
        }

        return option.createTO();
    }

    @Override
    public ItemTO getItem(int itemId) {
        return ia.find(itemId).createTO();
    }

    @Override
    public void deleteItemOption(int itemOptionId) {
        ioa.remove(ioa.find(itemOptionId));
    }

    @Override
    public void deleteItem(int itemId) {
        Item item=ia.find(itemId);
        
        for(ItemOptionTO o:getOptionsOfItem(itemId)){
            deleteItemOption(o.getId());
        }
        
        ia.remove(item);
    }

    @Override
    public void deletePoll(int pollId) {
        Poll poll =pa.find(pollId);
        for(ItemTO item:getItemsOfPoll(pollId)){
            deleteItem(item.getId());
        }
        
        pa.remove(poll);
    }
}
