package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.persistence.entities.VoteOption;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-05T16:05:45")
@StaticMetamodel(Item.class)
public class Item_ extends NamedEntity_ {

    public static volatile SingularAttribute<Item, String> titel;
    public static volatile SetAttribute<Item, VoteOption> options;
    public static volatile SingularAttribute<Item, Poll> poll;
    public static volatile SingularAttribute<Item, Integer> m;

}