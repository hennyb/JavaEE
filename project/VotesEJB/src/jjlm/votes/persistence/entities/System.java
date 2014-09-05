/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

// Start of user code (user defined imports)
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import jjlm.votes.logic.to.NamedEntityTO;
import jjlm.votes.logic.to.SystemTO;

// End of user code
/**
 * Description of System.
 *
 * @author Johannes
 */
@Entity
public class System extends NamedEntity<System, SystemTO> {

    private Set<Poll> polls;

	
    public System() {
        super();
        polls = new HashSet<Poll>();
        
    }

    // Start of user code (user defined methods for System)
    // End of user code
    /**
     * Returns polls.
     *
     * @return polls
     */
    @OneToMany(mappedBy = "system", fetch = FetchType.EAGER)
    public Set<Poll> getPolls() {
        return this.polls;
    }

    /**
     * Sets a value to attribute polls.
     *
     * @param newPolls
     */
    public void setPolls(Set<Poll> newPolls) {
        this.polls = newPolls;
    }

    @Override
    public SystemTO createTO() {
        SystemTO to = new SystemTO();
        return to;
    }

}
