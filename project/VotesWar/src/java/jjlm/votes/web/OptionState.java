/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import jjlm.votes.logic.to.ItemTO;

/**
 *
 * @author henny
 */
public class OptionState {

    private ItemTO item;
    private boolean selected;

    public ItemTO getItem() {
        return item;
    }

    public void setItem(ItemTO item) {
        this.item = item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public String toString () {
        
        return String.valueOf(this.selected);
        
    }
    
    
}
