package jjlm.votes.persistence.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.System;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-09-05T13:53:37")
@StaticMetamodel(Poll.class)
public class Poll_ { 

    public static volatile SingularAttribute<Poll, String> titel;
    public static volatile SingularAttribute<Poll, System> system;
    public static volatile SingularAttribute<Poll, Integer> pollId;
    public static volatile SingularAttribute<Poll, Date> start;
    public static volatile SingularAttribute<Poll, String> description;
    public static volatile SingularAttribute<Poll, Date> end;
    public static volatile SetAttribute<Poll, Item> items;

}