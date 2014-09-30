/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.deprecated;


import java.io.Serializable;
import javax.faces.context.FacesContext;

public abstract class AbstractBackingBean implements Serializable {
    private static final long serialVersionUID = -2023889737595970488L;


	public boolean hasMessage(String clientId) {
		if (clientId.equals("*")) {
			return !FacesContext.getCurrentInstance().getMessageList().isEmpty();
		} else {
			return !FacesContext.getCurrentInstance().getMessageList(clientId).isEmpty();
		}
	}
}
