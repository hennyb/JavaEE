/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.persistence.entities.ItemType;

@Named
@SessionScoped
public class NewItemBean extends OrganizerBean {

    private String itemTitle;
    private String itemType;

    public List<ItemType> getItemTypes() {

        List<ItemType> values = Arrays.asList(ItemType.values());

        return values;

    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

}
