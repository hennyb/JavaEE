/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import jjlm.votes.logic.to.ParticipantListTO;

@Entity
public class ParticipantList extends AbstractEntity<ParticipantList, ParticipantListTO>{
    
    private String title;
    private String text;
    private String seperator;
    
    private Organizer organizer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSeperator() {
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }
    
    @ManyToOne
    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @Override
    public ParticipantListTO createTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
