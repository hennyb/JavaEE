package jjlm.votes.web.organizer;

import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.PollTO;

/**
 * BackingBean for listing all polls of an organizer
 * 
 */
@Named
@SessionScoped
public class MyPollsBean extends OrganizerBean  {
    
    /**
     * Current table offset
     */
    private int offset = 0;
    
    /**
     * Current table size
     */
    private final int limit = 10;
    
    /**
     * Initializes MyPollsBean on request (redirect)
     */
    public void init () {
        
        if (this.user.isLoggedIn()) {
            
            // stub
            
        }
               
    }
   
    /**
     * Gets count of all polls created by the user
     * @return 
     */
    public Long getMyPollsCount () {
        
        return logic.getPollCountFromOrganizer(this.getOrganizer().getId());
        
    }
    
    /**
     * Gets list of all polls created by the user
     * @return 
     */
    public List<PollTO> getMyPolls() {
        
        return logic.getPollsfromOrganizer(this.getOrganizer().getId(), this.offset, this.limit);
        
    }
    
    /**
     * Determines if the current table as a following table
     * @return 
     */
    public boolean hasNext () {
        
        return (long) offset + limit <= getMyPollsCount();
        
    }
    
    /**
     * Determines if the current table as a previous table
     * @return 
     */
    public boolean hasPrev () {
        
        return 0 <= offset - limit;
        
    }

    /**
     * Displays the following table 
     */
    public void next () {
        
        if (hasNext()) {
            
            offset += limit;
            
        }
        
    }
    
    /**
     * Displays the previous table
     */
    public void prev () {
        
        if (hasPrev()) {
            
             offset -= limit;
            
        }
       
    }
    
    
}
