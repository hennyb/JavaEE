/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.votes.web.UserBean;

/**
 *
 * @author darjeeling
 */
@Named
@SessionScoped
public abstract class OrganizerBean implements Serializable {
    
    @Inject
    protected UserBean user;
    
    
}
