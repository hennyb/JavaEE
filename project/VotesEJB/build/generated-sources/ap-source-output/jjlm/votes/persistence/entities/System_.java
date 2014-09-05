package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Poll;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-09-05T13:53:37")
@StaticMetamodel(System.class)
public class System_ { 

    public static volatile SingularAttribute<System, Integer> systemId;
    public static volatile SetAttribute<System, Poll> polls;

}