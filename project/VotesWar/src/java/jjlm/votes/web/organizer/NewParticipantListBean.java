/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jjlm.votes.web.logic.ParticipantListParser;

/**
 *
 * @author darjeeling
 */
@Named
@RequestScoped
public class NewParticipantListBean {

    private String name;
    private String text;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    
    
    
    public String save () {
        
        ParticipantListParser parser = new ParticipantListParser();
        
        System.out.println(parser.parse(this.text));
        
        return "index?faces-redirect=true";
        
    }
    
    
}
