/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

// Start of user code (user defined imports)
import java.io.Serializable;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// End of user code
/**
 * Description of System.
 *
 * @author Johannes
 */
@Entity
public class System implements Serializable {

    /**
     * Description of the property polls.
     */
    private int systemId;

    private HashSet<Poll> polls = new HashSet<Poll>();

	// Start of user code (user defined attributes for System)
    // End of user code
    /**
     * The constructor.
     */
    public System() {
        // Start of user code constructor for System)
        super();
        // End of user code
    }

    // Start of user code (user defined methods for System)
    // End of user code
    /**
     * Returns polls.
     *
     * @return polls
     */
    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    public HashSet<Poll> getPolls() {
        return this.polls;
    }

    /**
     * Sets a value to attribute polls.
     *
     * @param newPolls
     */
    public void setPolls(HashSet<Poll> newPolls) {
        this.polls = newPolls;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

}
