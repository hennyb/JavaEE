/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

/**
 *
 * @author henny
 */
public class VoteOptionTO extends NamedEntityTO{
    private String description;
    private ItemTO item;
    private static final long serialVersionUID = 639616302059355682L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemTO getItem() {
        return item;
    }

    public void setItem(ItemTO item) {
        this.item = item;
    }
    
}
