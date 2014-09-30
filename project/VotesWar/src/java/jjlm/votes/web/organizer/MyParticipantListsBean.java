/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import jjlm.votes.web.ParticipantList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author darjeeling
 */
@Named
@SessionScoped
public class MyParticipantListsBean implements Serializable {

    public List<ParticipantList> getMyPartcipantLists () {
        
        List<ParticipantList> myParticipantLists = new ArrayList<ParticipantList>();
        
        for (int i=0; i < 10; i++) {
            
            ParticipantList list = new ParticipantList();
            list.setName("ParticipantList-" + i);
            
            myParticipantLists.add(list);
            
        }
        
        return myParticipantLists;
        
    }
    
}
