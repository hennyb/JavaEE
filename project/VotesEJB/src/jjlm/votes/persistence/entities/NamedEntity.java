/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import jjlm.votes.logic.to.NamedEntityTO;

/**
 *
 * @author henny
 */

@MappedSuperclass
public abstract class NamedEntity<E extends NamedEntity<E, T>, T extends NamedEntityTO> extends AbstractEntity<E, T> {

	private static final long serialVersionUID = -8794804332650701507L;
	protected String name;

	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}