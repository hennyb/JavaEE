/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henny
 */
public class ItemTO extends NamedEntityTO {

    private static final long serialVersionUID = -4145396564527603179L;

    private List<VoteOptionTO> options;
    private String titel;
    private Integer m;
    
    private PollTO poll;

    public ItemTO() {
        options = new ArrayList<>();
    }

    public List<VoteOptionTO> getOptions() {
        return options;
    }

    public void setOptions(List<VoteOptionTO> options) {
        this.options = options;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m) {
        this.m = m;
    }

    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }
}
