/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.PollTO;

/**
 *
 * @author maxmeffert
 */

@Named
@SessionScoped
public class MyPollsBean extends OrganizerBean  {
    
    private int offset = 0;
    private int limit = 10;
    private int max;
    

    public String getPollState(String id) {
        return logic.getStateOfPoll(Integer.parseInt(id)).toString();
    }
    
    
    
    public MyPollsBean() {
        
    }
    
    
    public List<PollTO> getMyPolls() {
        try{
            System.out.println("getMyPolls "+this.getOrganizer().getId()+"  "+this.getOrganizer().getEmail());
            return logic.getPollsfromOrganizer(logic.getOrganizer(this.getOrganizer().getEmail()));
        
        }catch(Exception e){
            
        }
        return new ArrayList<>();
    }

    public void next () {
        
        if (offset + limit <= max) {
            
            offset += limit;
            
        }
        
       
        
    }
    
    public void prev () {
        
        if (0 <= offset - limit) {
            
            offset -= limit;
            
        }
        
    }
    
    
}
