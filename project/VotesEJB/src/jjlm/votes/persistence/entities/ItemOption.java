/**
 * *****************************************************************************
 * 2014, All rights reserved.
 ******************************************************************************
 */
package jjlm.votes.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import jjlm.votes.logic.to.ItemOptionTO;

@Entity
public class ItemOption extends AbstractEntity<ItemOption, ItemOptionTO> {
  
    private String description;
    private String title;
    
    private Item item;
    
    private Integer votes;

    public ItemOption() {
        super();
        
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
    
    @Lob 
    @Column(name="DESCRIPTION", length=10000)
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    @ManyToOne
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    @Override
    public ItemOptionTO createTO() {
        ItemOptionTO to = new ItemOptionTO();
        to.setId(id);
        to.setItem(item.createTO());
        to.setDescription(description);
        to.setVotes(votes);
        to.setTitle(title);
        return to;
    }

}
