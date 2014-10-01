/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.votes.web.Poll;
import jjlm.votes.web.UserBean;
import jjlm.votes.web.help.RequestParameters;

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
    
    
    public List<Poll> getMyPolls() {
        
        List<Poll> myPolls = new ArrayList<Poll>();
        
        
        for (int i=0; i < 33; i++) {
            
            Poll myPoll = new Poll();
            
            myPoll.setName("Poll-" + i);
            
            myPolls.add(myPoll);
            
            
        }
        
        max = myPolls.size();
        
        return myPolls.subList(offset, offset+limit <= max ? offset+limit : max);
        
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
