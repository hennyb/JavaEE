/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
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

    private System system;

    private Set<Item> items;

    public Poll() {
        super();

        items = new HashSet<>();
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

    @ManyToOne
    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    @Override
    public PollTO createTO() {
        PollTO to = new PollTO();
        return to;
    }

}
