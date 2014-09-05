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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import jjlm.votes.logic.to.ItemTO;

// End of user code
/**
 * Description of Item.
 *
 * @author Johannes
 */
@Entity
public class Item extends NamedEntity<Item, ItemTO> {

    private static final long serialVersionUID = -5062238818277568048L;

    private Set<VoteOption> options;
    
    private String titel;
    private Integer m;
    
    private Poll poll;

    public Item() {
        super();
        
        options = new HashSet<>();

    }

    // Start of user code (user defined methods for Item)
    // End of user code
    /**
     * Returns options.
     *
     * @return options
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    public Set<VoteOption> getOptions() {
        return this.options;
    }

    /**
     * Sets a value to attribute options.
     *
     * @param newOptions
     */
    public void setOptions(Set<VoteOption> newOptions) {
        this.options = newOptions;
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

    /**
     * Returns m.
     *
     * @return m
     */
    public Integer getM() {
        return this.m;
    }

    /**
     * Sets a value to attribute m.
     *
     * @param newM
     */
    public void setM(Integer newM) {
        this.m = newM;
    }

    
    @ManyToOne
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
    

    @Override
    public ItemTO createTO() {
        ItemTO to = new ItemTO();

        return to;
    }

}
