/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.SystemTO;
import jjlm.votes.persistence.entities.System;

/**
 *
 * @author henny
 */
public class SystemAccess extends NamedAccess<System, SystemTO>{

    @PersistenceContext(name="VotesEJBPU")
    EntityManager em;
    
    public SystemAccess() {
        super(System.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
  
}
