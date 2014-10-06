/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jjlm.votes.web.help.RequestParameters;

/**
 *
 * @author henny
 */
@Named
@SessionScoped
public class EditItemBean extends OrganizerBean {

    private int itemId;

    public void init() {
        try {
            setItemId(Integer.parseInt(RequestParameters.get("id")));
            setValues();
        } catch (Exception e) {

        }
    }
    
    private void setValues(){
        
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String save() {

        return "";

    }

    public String delete() {

        return "";
    }

}
