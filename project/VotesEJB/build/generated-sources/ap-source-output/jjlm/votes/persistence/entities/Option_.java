package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Item;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-09-05T13:53:37")
@StaticMetamodel(Option.class)
public class Option_ { 

    public static volatile SingularAttribute<Option, Item> item;
    public static volatile SingularAttribute<Option, String> name;
    public static volatile SingularAttribute<Option, String> description;
    public static volatile SingularAttribute<Option, Integer> id;

}