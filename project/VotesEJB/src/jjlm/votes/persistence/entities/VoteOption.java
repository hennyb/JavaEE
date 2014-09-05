/**
 * *****************************************************************************
 * 2014, All rights reserved.
 ******************************************************************************
 */
package jjlm.votes.persistence.entities;

// Start of user code (user defined imports)
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jjlm.votes.logic.to.VoteOptionTO;

// End of user code
/**
 * Description of Option.
 *
 * @author Johannes
 */
@Entity
public class VoteOption extends NamedEntity<VoteOption, VoteOptionTO> {

  
    private String description;
    private Item item;


    public VoteOption() {
        super();
        
    }

	// Start of user code (user defined methods for Option)
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

    @ManyToOne
    @JoinColumn(name = "itemId")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public VoteOptionTO createTO() {
        VoteOptionTO to = new VoteOptionTO();
        return to;
    }

}
