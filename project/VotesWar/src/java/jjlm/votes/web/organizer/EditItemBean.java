/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.persistence.entities.ItemType;
import jjlm.votes.web.help.RequestParameters;

/**
 *
 * @author henny
 */
@Named
@SessionScoped
public class EditItemBean extends OrganizerBean {

    private int itemId;

    private String itemTitle;
    private int itemType;

    private String optionTitle;
    private String optionDescription;

    private ItemTO item;

    private int itemM;

    public void init() {

        setItemTitle("");
        setOptionTitle("");
        setOptionDescription("");
        setItemType(0);
        item = new ItemTO();

        try {
            setItemId(Integer.parseInt(RequestParameters.get("id")));
            item = logic.getItem(itemId);
            item.setOptions(logic.getOptionsOfItem(itemId));
            setItemTitle(item.getTitle());
            setItemType(item.getItemType().ordinal());
        } catch (Exception e) {

        }
    }

    public ItemTO getItem() {
        return item;
    }

    public void setItem(ItemTO item) {
        this.item = item;
    }

    public int getItemM() {
        return itemM;
    }

    public void setItemM(int itemM) {
        this.itemM = itemM;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getOptionDescription() {
        return optionDescription;
    }

    public void setOptionDescription(String optionDescription) {
        this.optionDescription = optionDescription;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String save() {
        item.setItemType(ItemType.values()[getItemType()]);
        item.setTitle(itemTitle);
        if (item.getItemType() == ItemType.M_OF_N) {
            item.setM(itemM);
        } else {
            item.setM(1);
        }

        logic.storeItem(item);

        return "edit-item?faces-redirect=true&id=" + getItemId();
    }

    public String delete() {
        logic.deleteItem(itemId);

        return "edit-poll?faces-redirect=true&id=" + item.getPoll().getId();
    }

    public String addOption() {

        ItemOptionTO to = new ItemOptionTO();
        to.setItem(item);
        to.setTitle(optionTitle);
        to.setDescription(optionDescription);
        to.setVotes(0);

        logic.storeItemOption(to);

        return "edit-item?faces-redirect=true&id=" + getItemId();
    }

    public List<ItemOptionTO> getAllOptions() {
        return logic.getOptionsOfItem(itemId);
    }

    public String deleteOption(int optionid) {
        logic.deleteItemOption(optionid);
        return "edit-item?faces-redirect=true&id=" + getItemId();
    }

    public void validateItemTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.isItemTitleUnique(item.getPoll().getId(), item.getId(), title) && !title.equals("")) {
            FacesMessage message = new FacesMessage("The item-title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

}
