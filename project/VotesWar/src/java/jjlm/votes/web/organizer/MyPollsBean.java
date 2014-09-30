/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.votes.web.Poll;
import jjlm.votes.web.UserBean;

/**
 *
 * @author maxmeffert
 */

@Named
@SessionScoped
public class MyPollsBean extends OrganizerBean  {
    
    
    
    public MyPollsBean() {
        
    }
    
    public List<Poll> getMyPolls() {
        
        List<Poll> myPolls = new ArrayList<Poll>();
        
        for (int i=0; i < 10; i++) {
            
            Poll myPoll = new Poll();
            
            myPoll.setName("Poll-" + i);
            
            myPolls.add(myPoll);
            
        }
        
        return myPolls;
    }
    
}
