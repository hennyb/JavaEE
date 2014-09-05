/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author henny
 */
public class PollTO extends NamedEntityTO {

    private static final long serialVersionUID = 2776900162657996129L;

    private String description;

    private Date startPoll;
    private Date endPoll;

    private SystemTO system;

    private List<ItemTO> items;

    public PollTO() {
        items = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartPoll() {
        return startPoll;
    }

    public void setStartPoll(Date startPoll) {
        this.startPoll = startPoll;
    }

    public Date getEndPoll() {
        return endPoll;
    }

    public void setEndPoll(Date endPoll) {
        this.endPoll = endPoll;
    }

    public SystemTO getSystem() {
        return system;
    }

    public void setSystem(SystemTO system) {
        this.system = system;
    }

    public List<ItemTO> getItems() {
        return items;
    }

    public void setItems(List<ItemTO> items) {
        this.items = items;
    }
    
    

}
