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
    

    
    public MyPollsBean() {
        
    }
    
    
    public List<PollTO> getMyPolls() {
        try{
            return logic.getPollsfromOrganizer(this.getOrganizer().getId());
        
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
