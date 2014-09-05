/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

/**
 *
 * @author henny
 */
public abstract class NamedEntityTO extends AbstractEntityTO implements Comparable<NamedEntityTO> {

    private static final long serialVersionUID = 4027757361891311318L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(NamedEntityTO o) {
        if (getClass() != o.getClass()) {
            throw new IllegalArgumentException(
                    "Can't compare " + getClass().getSimpleName()
                    + " to " + o.getClass().getSimpleName());
        }
        if (equals(o)) {
            return 0;
        }
        int r = getName().compareTo(o.getName());
        if (r == 0) {
            r = getId().compareTo(o.getId());
        }
        return r;
    }
}
