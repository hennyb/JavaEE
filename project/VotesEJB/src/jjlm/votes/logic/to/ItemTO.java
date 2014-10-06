/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import java.util.ArrayList;
import java.util.List;
import jjlm.votes.persistence.entities.ItemType;

/**
 *
 * @author henny
 */
public class ItemTO extends AbstractEntityTO {

    private static final long serialVersionUID = -4145396564527603179L;

    private List<ItemOptionTO> options;
    private String title;
    
    private ItemType itemType;
    
    private PollTO poll;

    public ItemTO() {
        options = new ArrayList<>();
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public List<ItemOptionTO> getOptions() {
        return options;
    }

    public void setOptions(List<ItemOptionTO> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

}
