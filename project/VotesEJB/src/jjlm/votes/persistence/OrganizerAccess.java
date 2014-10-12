/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.persistence.entities.Organizer;

@Stateless
@LocalBean
public class OrganizerAccess extends AbstractAccess<Organizer, OrganizerTO> {

    @PersistenceContext(name = "VotesEJBPU")
    EntityManager em;

    public OrganizerAccess() {
        super(Organizer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Organizer findOrganizer(String email) {
        Organizer o = null;
        o = findBy("email", email);
        return o;
    }

    public boolean isOrganizerEmailUnique(String email) {
        return em.createQuery("select count(o) From Organizer o"
                + " Where o.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() == 0;

    }

}
