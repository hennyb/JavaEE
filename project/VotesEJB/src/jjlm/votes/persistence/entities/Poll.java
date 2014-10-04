/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;

// End of user code
/**
 * Description of Poll.
 *
 * @author Johannes
 */
@Entity
public class Poll extends NamedEntity<Poll, PollTO> {

    private static final long serialVersionUID = -1526570451061341296L;

    private String description;

    private Date startPoll;
    private Date endPoll;

    private Set<Item> items;

    private Set<Organizer> organizer;
    private Set<Token> tokens;
    private Set<Participant> participants;

    public Poll() {
        super();

        items = new HashSet<>();
        organizer = new HashSet<>();
        tokens = new HashSet<>();
        participants = new HashSet<>();
        
    }

    @OneToMany(mappedBy = "poll", fetch = FetchType.EAGER)
    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    @OneToMany(mappedBy = "poll", fetch = FetchType.EAGER)
    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    @ManyToMany
    @JoinTable(name="POLL_ORGANIZER")
    public Set<Organizer> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Set<Organizer> organizer) {
        this.organizer = organizer;
    }

    /**
     * Returns description.
     *
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets a value to attribute description.
     *
     * @param newDescription
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Returns items.
     *
     * @return items
     */
    @OneToMany(mappedBy = "poll", fetch = FetchType.EAGER)
    public Set<Item> getItems() {
        return this.items;
    }

    /**
     * Sets a value to attribute items.
     *
     * @param newItems
     */
    public void setItems(Set<Item> newItems) {
        this.items = newItems;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getStartPoll() {
        return startPoll;
    }

    public void setStartPoll(Date startPoll) {
        this.startPoll = startPoll;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getEndPoll() {
        return endPoll;
    }

    public void setEndPoll(Date endPoll) {
        this.endPoll = endPoll;
    }

    @Override
    public PollTO createTO() {
        PollTO to = new PollTO();
        to.setId(getId());
        to.setName(getName());
        to.setDescription(getDescription());
        to.setEndPoll(getEndPoll());
        to.setStartPoll(getStartPoll());
        if(getOrganizer()==null)
            to.setOrganizer(new ArrayList<OrganizerTO>());
        else
            to.setOrganizer(createTransferList(getOrganizer()));
        
        return to;
    }

}