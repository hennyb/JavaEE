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
    private long count;
    
    
    public void init () {
        
        if (this.user.isLoggedIn()) {
            
            // stub
            
        }
               
    }
   
    public Long getMyPollsCount () {
        
        return logic.getPollCountFromOrganizer(this.getOrganizer().getId());
        
    }
    
    public List<PollTO> getMyPolls() {
        
        return logic.getPollsfromOrganizer(this.getOrganizer().getId(), this.offset, this.limit);
        
    }
    
    public boolean hasNext () {
        
        return (long) offset + limit <= getMyPollsCount();
        
    }
    
    public boolean hasPrev () {
        
        return 0 <= offset - limit;
        
    }

    public void next () {
        
        if (hasNext()) {
            
            offset += limit;
            
        }
        
    }
    
    public void prev () {
        
        if (hasPrev()) {
            
             offset -= limit;
            
        }
       
    }
    
    
}
