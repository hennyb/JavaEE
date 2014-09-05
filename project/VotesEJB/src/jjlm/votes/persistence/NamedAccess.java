/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence;

import jjlm.votes.logic.to.NamedEntityTO;
import jjlm.votes.persistence.entities.NamedEntity;

/**
 *
 * @author henny
 */
public abstract class NamedAccess<E extends NamedEntity<E, T>, T extends NamedEntityTO>
        extends AbstractAccess<E, T> {

    public NamedAccess(Class<E> entityClass) {
        super(entityClass);
    }

    public E findByName(String name) {
        return findBy("name", name);
    }
}
