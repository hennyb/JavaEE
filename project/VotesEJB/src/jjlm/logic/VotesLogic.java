/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic;

import java.security.NoSuchAlgorithmException;
import jjlm.votes.persistence.entities.PollState;
import java.util.List;
import javax.ejb.Remote;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;

@Remote
public interface VotesLogic {

    OrganizerTO getOrganizer(String email);

    OrganizerTO findFirst();

    OrganizerTO storeOrganizer(OrganizerTO to);

    PollTO storePoll(PollTO to);

    String getPlainString();

    List<PollTO> getPollsfromOrganizer(int organizerID);

    List<PollTO> getPollsfromOrganizer(int organizerID, int from, int to);

    PollTO getPoll(int pollId);

    List<ItemTO> getItemsOfPoll(int poollId);

    ItemTO getItem(int itemId);

    ItemTO storeItem(ItemTO to);

    List<ItemOptionTO> getOptionsOfItem(int itemID);

    ItemOptionTO storeItemOption(ItemOptionTO to);

    void deleteItemOption(int itemOptionId);

    void deleteItem(int itemId);

    void deletePoll(int pollId);

    List<ParticipantTO> getParticipantsOfPoll(int pollId);

    ParticipantTO storeParticipant(ParticipantTO to);

    void deleteParticipant(int participantId);

    boolean uniquePollTitle(String title);

    boolean uniquePollTitle(String title, int pollId);

    List<TokenTO> getTokensOfPoll(int pollId);

    void deleteToken(int tokenId);

    void deleteTokensOfPoll(int pollId);

    List<TokenTO> startPoll(int pollId, String notificationText);

    void resetPoll(int pollId);

    List<TokenTO> createTokensForPoll(int pollId);

    boolean isItemTitleUnique(int pollId, String title);

    boolean isItemTitleUnique(int pollId, int itemId, String title);

    boolean isOrganizerEmailUnique(String email);

    TokenTO getTokenBySignature(String signature);

    TokenTO getTokensOfPollAndParticipant(int pollId, int participantId);

    void incrementItemOptionCount(int optionId);

    void incrementAbstainedItem(int itemId);

    long getParticipation(int pollId);

    TokenTO storeToken(TokenTO token);

    boolean isValidEndDate(int paramID, String endDate);

    Long getPollCountFromOrganizer(int organizerId);

    List<Integer> getPollIdsOfOrganizer(int organizerId);

    List<Integer> getItemIdsOfOrganizer(int organizerId);

    Boolean isPasswordValid(String password, String salt, String encryptedPassword) throws NoSuchAlgorithmException;

    String encryptPassword(String password, String salt) throws NoSuchAlgorithmException;

    ParticipantTO getParticipant(int participantId);

    long getNumberOfParticipants(int pollId);

    public boolean isItemValid(int itemId);

}
