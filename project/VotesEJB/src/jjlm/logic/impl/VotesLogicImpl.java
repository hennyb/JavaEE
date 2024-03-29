/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import jjlm.votes.persistence.entities.ItemType;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.persistence.entities.Token;

@Stateless
public class VotesLogicImpl implements VotesLogic {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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

    @Resource(lookup = "unimail")
    private Session mailSession;

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
        organizer.setPasswordSalt(to.getPasswordSalt());

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
        for (PollTO p : AbstractEntity.createTransferList(pa.getPolls(organizerID))) {
            if (p.getPollState() != null
                    && p.getEndPoll() != null
                    && p.getPollState() != PollState.FINISHED && p.getEndPoll().getTime() < new Date().getTime()) {
                p.setPollState(PollState.FINISHED);
                storePoll(p);
            }
        }
        return AbstractEntity.createTransferList(pa.getPolls(organizerID));
    }

    @Override
    public List<PollTO> getPollsfromOrganizer(int organizerID, int offset, int limit) {
        for (PollTO p : AbstractEntity.createTransferList(pa.getPolls(organizerID))) {
            if (p.getPollState() != null
                    && p.getEndPoll() != null
                    && p.getPollState() != PollState.FINISHED && p.getEndPoll().getTime() < new Date().getTime()) {
                p.setPollState(PollState.FINISHED);
                storePoll(p);
            }
        }
        List<PollTO> result = AbstractEntity.createTransferList(pa.getPolls(organizerID, offset, limit));

        System.out.println(result);

        return result;

    }

    @Override
    public PollTO getPoll(int pollId) {

        // PollTO poll = pa.find(pollId).createTO();
        // this.storePoll(poll);
        // return poll;
        Poll poll = pa.find(pollId);

        if (poll.getEndPoll() != null && poll.getEndPoll().getTime() < new Date().getTime()) {
            if (poll.getPollState() != null && poll.getPollState() != PollState.FINISHED) {
                poll.setPollState(PollState.FINISHED);
                storePoll(poll.createTO());
            }

        }

        return pa.find(pollId).createTO();
    }

    @Override
    public List<ItemTO> getItemsOfPoll(int pollId) {
        return AbstractEntity.createTransferList(ia.getItems(pollId));
    }

    @Override
    public ItemTO storeItem(ItemTO to) {

        Item item = to.getId() == null ? new Item() : ia.find(to.getId());

        Poll poll = pa.find(to.getPoll().getId());

        item.setItemType(to.getItemType());
        item.setTitle(to.getTitle());
        item.setPoll(poll);
        item.setId(to.getId());
        item.setValid(to.isValid());
        item.setM(to.getM());
        item.setAbstainedVotes(to.getAbstainedVotes());

        if (to.getId() == null) {
            item.setAbstainedVotes(0);
            ia.create(item);
        } else {

            item = ia.edit(item);
        }

        if (item.getItemType() == ItemType.YES_NO) {
            if (to.getId() != null) {
                for (ItemOptionTO op : getOptionsOfItem(item.getId())) {
                    deleteItemOption(op.getId());
                }
            }

            item.setM(1);

            ItemOptionTO no = new ItemOptionTO();
            no.setVotes(0);
            no.setTitle("No");
            no.setDescription("");
            no.setItem(item.createTO());

            ItemOptionTO yes = new ItemOptionTO();

            yes.setVotes(0);
            yes.setTitle("Yes");
            yes.setDescription("");
            yes.setItem(item.createTO());

            storeItemOption(yes);
            storeItemOption(no);
        }

        validateItem(item.getId());
        validatePoll(poll.getId());

        return item.createTO();

    }

    @Override
    public List<ItemOptionTO> getOptionsOfItem(int itemID) {
        return AbstractEntity.createTransferList(ioa.getOptions(itemID));
    }

    @Override
    public ItemOptionTO storeItemOption(ItemOptionTO to) {
        ItemOption option = to.getId() == null ? new ItemOption() : ioa.find(to.getId());
        Item item = ia.find(to.getItem().getId());
        option.setDescription(to.getDescription());
        option.setVotes(to.getVotes());
        option.setItem(item);
        option.setTitle(to.getTitle());
        option.setId(to.getId());

        if (to.getId() == null) {
            option.setVotes(0);
            ioa.create(option);
        } else {
            option = ioa.edit(option);
        }
        validateItem(item.getId());
        validatePoll(item.getPoll().getId());

        return option.createTO();
    }

    @Override
    public ItemTO getItem(int itemId) {
        Item item = ia.find(itemId);
        return item.createTO();
    }

    @Override
    public void deleteItemOption(int itemOptionId) {
        ItemOption io = ioa.find(itemOptionId);
        Item item = ia.find(io.getItem().getId());

        ioa.remove(ioa.find(itemOptionId));

        validateItem(item.getId());
        validatePoll(item.getPoll().getId());

    }

    @Override
    public void deleteItem(int itemId) {
        Item item = ia.find(itemId);

        for (ItemOptionTO o : getOptionsOfItem(itemId)) {
            deleteItemOption(o.getId());
        }
        ia.remove(item);

        validatePoll(item.getPoll().getId());
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

        Poll poll = pa.find(to.getPoll().getId());
        participant.setEmail(to.getEmail());
        participant.setPoll(poll);
        participant.setHasVoted(to.isHasVoted());
        participant.setId(to.getId());

        if (to.getId() == null) {
            pta.create(participant);
            validatePoll(poll.getId());

        } else {
            participant = pta.edit(participant);
        }

        return participant.createTO();
    }

    @Override
    public void deleteParticipant(int participantId) {
        Participant participant = pta.find(participantId);
        for (Token t : ta.getTokenOfParticipant(participantId)) {
            ta.remove(t);
        }
        pta.remove(participant);

        validatePoll(participant.getPoll().getId());

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
        ia.resetAbstainedVotes(pollId);
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
    public List<TokenTO> startPoll(int pollId, String notificationText) {

        PollTO poll = getPoll(pollId);
        poll.setStartPoll(new Date());
        poll.setPollState(PollState.STARTED);

        storePoll(poll);

        List<TokenTO> tokens = createTokensForPoll(pollId);

        for (ParticipantTO participant : getParticipantsOfPoll(pollId)) {
            StringBuilder emailText = new StringBuilder();

            emailText.append(notificationText + "\n");
            emailText.append("Hello "+participant.getEmail()+ "\n");
            emailText.append("You are invited to the poll "+poll.getTitle() + "\n");
            emailText.append("Here is a small description:"+ "\n");
            emailText.append(poll.getDescription()+ "\n");
            emailText.append("please visit my-web-page.com/poll and verify your token: "+getParticipant(participant.getId()).getToken().getSignature() +" \n");
            emailText.append("The Poll starts on "+poll.getStartPoll()+"\n");
            emailText.append("And ends on "+poll.getEndPoll()+"\n");
            emailText.append("There are "+getNumberOfParticipants(pollId)+ " participants invited to this poll"+"\n");
            
            emailText.append("Best Regards \n");
            emailText.append(poll.getOrganizer().getRealname());
            
            
            try {
                Message msg = new MimeMessage(mailSession);
                msg.setSubject("Invitation to Poll: " + poll.getTitle());
                msg.setSentDate(new Date());
                msg.setFrom(new InternetAddress("hennnyhenny@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(participant.getEmail(), false));
                msg.setText(emailText.toString());

                Transport.send(msg);
            } catch (MessagingException ex) {
                System.err.println(ex);
            }

        }
        return tokens;

    }

    private void validateItem(int itemId) {

        System.out.println(itemId);

        Item item = ia.find(itemId);
        List<ItemOptionTO> options = this.getOptionsOfItem(itemId);

        boolean valid
                = 0 <= item.getM() && item.getM() <= options.size()
                && 2 <= options.size();

        if (valid != item.isValid()) {

            item.setValid(valid);
            ia.edit(item);

        }

    }

    private void validatePoll(int pollId) {
        Poll poll = pa.find(pollId);

        boolean b = getItemsOfPoll(poll.getId()).size() > 0 && pa.allItemsValid(poll.getId());

        b &= getParticipantsOfPoll(pollId).size() >= 3;

        if (poll.getPollState() == PollState.PREPARED) {

            b &= new Date().getTime() < poll.getEndPoll().getTime();

        }

        if (b != poll.isValid()) {

            poll.setValid(b);
            pa.edit(poll);

        }
    }

    @Override
    public boolean isItemTitleUnique(int pollId, String title) {
        return ia.isItemTitleUnique(pollId, title);
    }

    @Override
    public boolean isItemTitleUnique(int pollId, int itemId, String title) {
        return ia.isItemTitleUnique(pollId, itemId, title);
    }

    @Override
    public TokenTO getTokenBySignature(String signature) {
        TokenTO t = ta.getTokenBySignature(signature).createTO();
        t.getPoll().setItems(getItemsOfPoll(t.getPoll().getId()));
        for (ItemTO item : t.getPoll().getItems()) {
            item.setOptions(getOptionsOfItem(item.getId()));
        }
        return t;
    }

    public TokenTO getTokensOfPollAndParticipant(int pollId, int participantId) {
        return ta.getTokensOfPollAndParticipant(pollId, participantId).createTO();
    }

    @Override
    public void incrementItemOptionCount(int optionId) {
        ioa.incrementCount(optionId);
    }

    @Override
    public void incrementAbstainedItem(int itemId) {

        ItemTO item = getItem(itemId);
        item.setAbstainedVotes(item.getAbstainedVotes() + 1);

        storeItem(item);

    }

    @Override
    public long getParticipation(int pollId) {
        return pa.getParticipation(pollId);
    }

    @Override
    public TokenTO storeToken(TokenTO to) {
        Token t = to.getId() == null ? new Token() : ta.find(to.getId());

        t.setId(to.getId());
        t.setInvalid(to.isInvalid());
        t.setParticipant(pta.find(to.getParticipant().getId()));
        t.setSignature(to.getSignature());
        t.setPoll(pa.find(to.getPoll().getId()));

        if (to.getId() == null) {
            ta.create(t);

        } else {
            t = ta.edit(t);
        }

        return t.createTO();
    }

    @Override
    public boolean isValidEndDate(int paramID, String endDate) {
        Poll poll = pa.find(paramID);
        try {
            //if not parseable
            Date date = sdf.parse(endDate);
            //if enddate in the past
            if (date.getTime() < new Date().getTime()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        //if pollend before pollstart
        if (poll.getStartPoll() != null && poll.getStartPoll().getTime() > poll.getEndPoll().getTime()) {
            return false;
        }

        return true;
    }

    @Override
    public Long getPollCountFromOrganizer(int organizerId) {
        return pa.getPollCount(organizerId);
    }

    @Override
    public boolean isOrganizerEmailUnique(String email) {
        return oa.isOrganizerEmailUnique(email);
    }

    @Override
    public List<Integer> getPollIdsOfOrganizer(int organizerId) {
        return pa.getPollIdsOfOrganizer(organizerId);
    }

    @Override
    public List<Integer> getItemIdsOfOrganizer(int organizerId) {
        return ia.getItemIdsOfOrganizer(organizerId);
    }

    @Override
    public Boolean isPasswordValid(String password, String salt, String encryptedPassword) throws NoSuchAlgorithmException {
        String encPass = this.encryptPassword(password, salt);
        return encryptedPassword.equals(encPass);
    }

    @Override
    public String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-256");

        String result = "";
        byte[] bytes;
        //do not change unless you want to drop all users
        String plain = salt + password;

        bytes = md.digest(plain.getBytes());

        for (byte b : bytes) {

            result += String.format("%02X", b);

        }

        return result;
    }

    @Override
    public ParticipantTO getParticipant(int participantId) {
        return pta.getParticipantWithPoll(participantId);
    }

    @Override
    public long getNumberOfParticipants(int pollId) {
        return pa.getParticipations(pollId);
    }

    @Override
    public boolean isItemValid(int itemId) {
        System.out.println(itemId);
        return getItem(itemId).isValid();
    }

}
