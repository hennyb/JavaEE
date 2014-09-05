/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// End of user code
/**
 * Description of Poll.
 *
 * @author Johannes
 */
@Entity
public class Poll implements Serializable {

    private int pollId;

    private String description = "";

    private HashSet<Item> items = new HashSet<Item>();

    private Date start = new Date();

    private Date end = new Date();

    private String titel = "";

    private System system;

    public Poll() {
        // Start of user code constructor for Poll)
        super();
        // End of user code
    }

    // Start of user code (user defined methods for Poll)
    // End of user code
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
    public HashSet<Item> getItems() {
        return this.items;
    }

    /**
     * Sets a value to attribute items.
     *
     * @param newItems
     */
    public void setItems(HashSet<Item> newItems) {
        this.items = newItems;
    }

    /**
     * Returns start.
     *
     * @return start
     */
    public Date getStart() {
        return this.start;
    }

    /**
     * Sets a value to attribute start.
     *
     * @param newStart
     */
    public void setStart(Date newStart) {
        this.start = newStart;
    }

    /**
     * Returns end.
     *
     * @return end
     */
    public Date getEnd() {
        return this.end;
    }

    /**
     * Sets a value to attribute end.
     *
     * @param newEnd
     */
    public void setEnd(Date newEnd) {
        this.end = newEnd;
    }

    /**
     * Returns titel.
     *
     * @return titel
     */
    public String getTitel() {
        return this.titel;
    }

    /**
     * Sets a value to attribute titel.
     *
     * @param newTitel
     */
    public void setTitel(String newTitel) {
        this.titel = newTitel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    @ManyToOne
    @JoinColumn(name = "systemId")
    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

}
