/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.web.UserBean;

/**
 *
 * @author darjeeling
 */
@Named
@SessionScoped
public class OrganizerBean implements Serializable {

    @Inject
    protected UserBean user;

    @EJB
    protected VotesLogic logic;

    private OrganizerTO organizer;

    public OrganizerTO getOrganizer() {
        try {
            if (organizer == null) {
                organizer = logic.getOrganizer(user.getEmail());
            } else {
                if (!organizer.getEmail().equals(user.getEmail())) {
                    organizer = logic.getOrganizer(user.getEmail());
                }
            }
        } catch (Exception e) {

        }
        return organizer;
    }

}
