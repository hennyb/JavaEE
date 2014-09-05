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

    /**
     * Description of the property description.
     */
    private String description = "";

    /**
     * Description of the property name.
     */
    private String name = "";

    /**
     * Description of the property id.
     */
    private Integer id = Integer.valueOf(0);

    private Item item;

	// Start of user code (user defined attributes for Option)
	// End of user code
    /**
     * The constructor.
     */
    public VoteOption() {
        // Start of user code constructor for Option)
        super();
        // End of user code
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

    /**
     * Returns name.
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets a value to attribute name.
     *
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Returns id.
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Sets a value to attribute id.
     *
     * @param newId
     */
    public void setId(Integer newId) {
        this.id = newId;
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
