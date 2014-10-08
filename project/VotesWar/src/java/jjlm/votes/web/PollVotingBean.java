/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.ItemType;

@Named
@SessionScoped
public class PollVotingBean implements Serializable {

    @EJB
    protected VotesLogic logic;

    private PollTO poll;

    private List<String> selectedOptions;
    private Map<Integer, OptionState> optionStates;

    public PollVotingBean() {
        
        optionStates = new HashMap<>();
        
    }

    public void init() {

        System.out.println("iiinit");
        poll = logic.getPoll(1);
        System.out.println("iiinit1");
        poll.setItems(logic.getItemsOfPoll(poll.getId()));
        System.out.println("iiinit2");
        System.out.println("iiinit3");

        
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

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {

        this.selectedOptions = selectedOptions;
    }

    private void setPoll(PollTO poll) {
        this.poll = poll;
    }

    public PollTO getPoll() {
        if (poll == null) {
            setPoll(new PollTO());
        }

        return poll;
    }

    public String setOptionState(int optionId) {

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

        return "poll?faces-redirect=true";

    }

    public String getOptionState(int optionId) {
        System.out.println(optionId);
        System.out.println(optionStates);
        System.out.println(this.optionStates.containsKey(optionId));
        if (this.optionStates.get(optionId).isSelected()) {
           return "btn btn-success";
        }
        else {
            return "btn btn-primary";
        }

    }

    public boolean treu(int jo){
        System.out.println("joo"+jo);
        return true;
    }
    
    public void print() {
        for (String item : selectedOptions) {
            System.out.println(item);
        }
    }

}
