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
public class SystemTO extends NamedEntityTO {

    private static final long serialVersionUID = -4506416163897602487L;

    private List<PollTO> polls;

    public SystemTO() {
        polls = new ArrayList<>();
    }

    public List<PollTO> getPolls() {
        return polls;
    }

    public void setPolls(List<PollTO> polls) {
        this.polls = polls;
    }

}
