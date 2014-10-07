/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import jjlm.votes.persistence.entities.Organizer;

public class ParticipantListTO extends AbstractEntityTO{
    
    private String title;
    private String text;
    private String seperator;
    
    private OrganizerTO organizer;

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

    public OrganizerTO getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerTO organizer) {
        this.organizer = organizer;
    }
}
