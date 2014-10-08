/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic;

import jjlm.votes.persistence.entities.PollState;
import java.util.List;
import javax.ejb.Remote;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;

@Remote
public interface VotesLogic {

    OrganizerTO getOrganizer(String email);

    OrganizerTO findFirst();

    OrganizerTO storeOrganizer(OrganizerTO to);

    PollTO storePoll(PollTO to);

    PollTO addOrganizerToPoll(int organizerId, int pollId);

    String getPlainString();

    List<PollTO> getPollsfromOrganizer(OrganizerTO to);

    List<PollTO> getPollsfromOrganizer(int organizerID);

    List<PollTO> getPollsfromOrganizer(int organizerID, int from, int to);

    List<PollTO> getAllPolls();

    PollTO getPoll(String name, String description);

    PollTO getPoll(int pollId);

    PollState getStateOfPoll(int pollId);

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

    /**
     * Check is poll has unique titel in systen scope.
     *
     * @param poll
     * @return
     */
    boolean uniquePollTitle(PollTO poll);
}
