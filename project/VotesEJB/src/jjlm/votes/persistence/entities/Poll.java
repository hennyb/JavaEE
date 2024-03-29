/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Size;
import jjlm.votes.logic.to.PollTO;


@Entity
public class Poll extends AbstractEntity<Poll, PollTO> {

    private static final long serialVersionUID = -1526570451061341296L;
    
    private String title;
    private String description;
    
    private Date startPoll;
    private Date endPoll;
    
    private PollState pollState;

    private Set<Item> items;

    private Organizer organizer;
    private Set<Token> tokens;
    private Set<Participant> participants;
    
    private boolean valid;
    private boolean tracking;

    public Poll() {
        super();

        items = new HashSet<>();
        tokens = new HashSet<>();
        participants = new HashSet<>();
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }
    
    @Enumerated(EnumType.STRING)
    public PollState getPollState() {
        return pollState;
    }

    public void setPollState(PollState pollState) {
        this.pollState = pollState;
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

    @ManyToOne
    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @Lob 
    @Column(name="DESCRIPTION", length=10000)
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
    @Override
    public PollTO createTO() {
        PollTO to = new PollTO();
        to.setId(getId());
        to.setDescription(getDescription());
        to.setEndPoll(getEndPoll());
        to.setStartPoll(getStartPoll());
        to.setTitle(title);
        to.setPollState(pollState);
        to.setValid(valid);
        to.setOrganizer(getOrganizer().createTO());

        return to;
    }

}
