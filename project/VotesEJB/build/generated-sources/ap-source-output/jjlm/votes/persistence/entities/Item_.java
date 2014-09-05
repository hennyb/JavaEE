package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Option;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-09-05T13:53:37")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Integer> itemId;
    public static volatile SingularAttribute<Item, String> titel;
    public static volatile SetAttribute<Item, Option> options;
    public static volatile SingularAttribute<Item, Integer> m;

}