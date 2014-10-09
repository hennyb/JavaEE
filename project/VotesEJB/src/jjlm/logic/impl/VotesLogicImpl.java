/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jjlm.votes.persistence.entities.PollState;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.persistence.ItemAccess;
import jjlm.votes.persistence.ItemOptionAccess;
import jjlm.votes.persistence.OrganizerAccess;
import jjlm.votes.persistence.ParticipantAccess;
import jjlm.votes.persistence.PollAccess;
import jjlm.votes.persistence.TokenAccess;
import jjlm.votes.persistence.entities.AbstractEntity;
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.ItemOption;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.persistence.entities.Token;

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

    @EJB
    private ParticipantAccess pta;

    @EJB
    private TokenAccess ta;

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

        poll.setOrganizer(oa.find(to.getOrganizer().getId()));
        poll.setPollState(to.getPollState());

        if (to.getStartPoll() == null || to.getPollState() == null) {
            poll.setPollState(PollState.PREPARED);
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
    public List<PollTO> getPollsfromOrganizer(int organizerID) {
        return AbstractEntity.createTransferList(pa.getPolls(organizerID));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID, int offset, int max) {
        System.out.println("listsize: " + pa.getAllPolls().size());
        return AbstractEntity.createTransferList(pa.getAllPolls());
    }

    @Override
    public PollTO getPoll(int pollId) {

        // PollTO poll = pa.find(pollId).createTO();
        // this.storePoll(poll);
        // return poll;
        return pa.find(pollId).createTO();
    }

    @Override
    public PollState getStateOfPoll(int pollId) {
        //return PollState.FINISHED;
        return pa.getStateOfPoll(pollId);
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
        item.setM(to.getM());
        item.setAbstainedCount(to.getAbstainedCount());

        if (to.getId() == null) {
            item.setAbstainedCount(0);
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
            option.setCount(0);
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
        Item item = ia.find(itemId);

        for (ItemOptionTO o : getOptionsOfItem(itemId)) {
            deleteItemOption(o.getId());
        }

        ia.remove(item);
    }

    @Override
    public void deletePoll(int pollId) {
        Poll poll = pa.find(pollId);
        for (ItemTO item : getItemsOfPoll(pollId)) {
            deleteItem(item.getId());
        }

        for (ParticipantTO participant : getParticipantsOfPoll(pollId)) {
            deleteParticipant(participant.getId());
        }

        for (TokenTO token : getTokensOfPoll(pollId)) {
            deleteToken(token.getId());
        }

        pa.remove(poll);
    }

    @Override
    public ParticipantTO storeParticipant(ParticipantTO to) {
        Participant participant = to.getId() == null ? new Participant() : pta.find(to.getId());

        participant.setEmail(to.getEmail());
        participant.setPoll(pa.find(to.getPoll().getId()));
        participant.setHasVoted(to.isHasVoted());
        participant.setId(to.getId());

        if (to.getId() == null) {
            pta.create(participant);

        } else {
            participant = pta.edit(participant);
        }

        return participant.createTO();
    }

    @Override
    public void deleteParticipant(int participantId) {
        for (Token t : ta.getTokenOfParticipant(participantId)) {
            ta.remove(t);
        }
        pta.remove(pta.find(participantId));
    }

    @Override
    public List<ParticipantTO> getParticipantsOfPoll(int pollId) {
        return AbstractEntity.createTransferList(pta.getParticipantsOfPoll(pollId));
    }

    @Override
    public boolean uniquePollTitle(String title) {
        return pa.uniqueTitle(title);
    }

    @Override
    public boolean uniquePollTitle(String title, int pollId) {
        return pa.uniqueTitle(title, pollId);
    }

    @Override
    public List<TokenTO> getTokensOfPoll(int pollId) {
        return AbstractEntity.createTransferList(ta.getTokensOfPoll(pollId));
    }

    @Override
    public void deleteToken(int tokenId) {
        ta.remove(ta.find(tokenId));
    }

    @Override
    public void deleteTokensOfPoll(int pollId) {
        for (TokenTO token : getTokensOfPoll(pollId)) {
            deleteToken(token.getId());
        }
    }

    @Override
    public void resetPoll(int pollId) {

        PollTO to = getPoll(pollId);
        to.setStartPoll(null);
        to.setPollState(PollState.PREPARED);

        storePoll(to);

        ioa.resetCount(pollId);
        ia.resetAbstainedCount(pollId);
        deleteTokensOfPoll(pollId);

    }

    @Override
    public List<TokenTO> createTokensForPoll(int pollId) {
        List<Token> tokens = new ArrayList<>();

        for (Participant participant : pta.getParticipantsOfPoll(pollId)) {

            Token token = new Token();
            token.setInvalid(false);
            token.setPoll(participant.getPoll());
            token.setParticipant(participant);
            token.setSignature(UUID.randomUUID().toString());

            ta.create(token);

            tokens.add(token);

        }

        return AbstractEntity.createTransferList(tokens);

    }

    @Override
    public List<TokenTO> startPoll(int pollId) {

        PollTO poll = getPoll(pollId);
        poll.setStartPoll(new Date());
        poll.setPollState(PollState.STARTED);
        
        storePoll(poll);

        return createTokensForPoll(pollId);

    }
}
