/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.persistence.entities.ItemType;


@Named
@SessionScoped
public class PollVotingBean implements Serializable {

    @EJB
    protected VotesLogic logic;

    private PollTO poll;
    private TokenTO token;
    private Map<Integer, OptionState> optionStates;

    public PollVotingBean() {
        
        optionStates = new HashMap<Integer, OptionState>();
        
    }

    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

    public TokenTO getToken() {
        return token;
    }

    public void setToken(TokenTO token) {
        this.token = token;
    }

    public String updateOptionState(int optionId) {

        OptionState opState = this.optionStates.get(optionId);
        System.out.println("setOptionSate");
        if (opState.getItem().getItemType() == ItemType.M_OF_N ) {

            // toggle self
            System.out.println("setOptionSate1");
            opState.setSelected(!opState.isSelected());
            optionStates.put(optionId, opState);

        } else {
            System.out.println("setOptionSate2");
            // toggle others
            for (ItemOptionTO io : opState.getItem().getOptions()) {
                OptionState ops = this.optionStates.get(io.getId());
                ops.setSelected(false);
                optionStates.put(io.getId(), ops);
            }

            opState.setSelected(true);
            optionStates.put(optionId, opState);

            System.out.println("setOptionSate3");
        }
        
        System.out.println(this.optionStates);

        return "poll";

    }

    public String getOptionStyleClass(int optionId) {
        
        if (this.optionStates.get(optionId).isSelected()) {
        
            return "btn btn-success";
        
        }
        else {
        
            return "btn btn-primary";
        
        }

    }
    
    public String getOptionGlyphicon (int optionId) {
        
        if (this.optionStates.get(optionId).isSelected()) {
        
            return "glyphicon glyphicon-ok";
        
        }
        else {
        
            return "glyphicon glyphicon-remove";
        
        }
        
    }
    
    /**
     * initialize bean on request
     */
    public void init() {

        poll = logic.getPoll(1);
        poll.setItems(logic.getItemsOfPoll(poll.getId()));

        
        for (ItemTO item : poll.getItems()) {
            item.setOptions(logic.getOptionsOfItem(item.getId()));
        }
        
           
        if (this.optionStates.size() == 0) {
            
            for (ItemTO item : poll.getItems()) {
                    
                for (ItemOptionTO io : item.getOptions()) {
                    
                    OptionState opState = new OptionState();
                    opState.setItem(item);
                    opState.setSelected(false);
                    optionStates.put(io.getId(), opState);
                    
                }
                
            }
            
        }

    }
    
    public String submit () {
     
        return "poll";
        
    }
    
    public String abstain () {
     
        return "poll";
        
    }
    
    public String cancel () {
     
        return "poll";
        
    }

    
    
    private boolean foo = false;
    
    public void toggleFoo () {
        
        System.out.println(this.foo);
        this.foo = !this.foo;
        
    }
    
    public boolean getFoo () {
        
        return this.foo;
        
    }


}
