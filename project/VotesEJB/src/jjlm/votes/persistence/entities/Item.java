/**
 * *****************************************************************************
 * 2014, All rights reserved.
 * *****************************************************************************
 */
package jjlm.votes.persistence.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import jjlm.votes.logic.to.ItemTO;

@Entity
public class Item extends AbstractEntity<Item, ItemTO> {

    private static final long serialVersionUID = -5062238818277568048L;

    private String title;
    private Set<ItemOption> options;

    private ItemType itemType;

    private Poll poll;
    
    private boolean valid;

    public Item() {
        super();

        options = new HashSet<>();

    }
    
    
    @Enumerated(EnumType.STRING)
    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    // Start of user code (user defined methods for Item)
    // End of user code
    /**
     * Returns options.
     *
     * @return options
     */
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    public Set<ItemOption> getOptions() {
        return this.options;
    }

    /**
     * Sets a value to attribute options.
     *
     * @param newOptions
     */
    public void setOptions(Set<ItemOption> newOptions) {
        this.options = newOptions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public ItemTO createTO() {
        ItemTO to = new ItemTO();
        to.setId(id);
        to.setItemType(itemType);
        to.setPoll(poll.createTO());
        to.setTitle(title);
        to.setValid(valid);

        return to;
    }

}
