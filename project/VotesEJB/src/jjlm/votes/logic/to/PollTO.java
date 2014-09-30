/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Token;

/**
 *
 * @author henny
 */
public class PollTO extends NamedEntityTO {

    private static final long serialVersionUID = 2776900162657996129L;

    private String description;

    private Date startPoll;
    private Date endPoll;


    private List<ItemTO> items;
    
    private List<OrganizerTO> organizer;
    private List<TokenTO> tokens;
    private List<ParticipantTO> participants;

    public PollTO() {
        items = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartPoll() {
        return startPoll;
    }

    public void setStartPoll(Date startPoll) {
        this.startPoll = startPoll;
    }

    public Date getEndPoll() {
        return endPoll;
    }

    public void setEndPoll(Date endPoll) {
        this.endPoll = endPoll;
    }


    public List<ItemTO> getItems() {
        return items;
    }

    public void setItems(List<ItemTO> items) {
        this.items = items;
    }

    public List<OrganizerTO> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(List<OrganizerTO> organizer) {
        this.organizer = organizer;
    }

    public List<TokenTO> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenTO> tokens) {
        this.tokens = tokens;
    }

    public List<ParticipantTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantTO> participants) {
        this.participants = participants;
    }
    
    

}
