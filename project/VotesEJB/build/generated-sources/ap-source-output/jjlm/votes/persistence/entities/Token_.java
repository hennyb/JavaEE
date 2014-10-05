package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Poll;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-05T16:05:45")
@StaticMetamodel(Token.class)
public class Token_ extends NamedEntity_ {

    public static volatile SingularAttribute<Token, Poll> poll;
    public static volatile SingularAttribute<Token, String> value;
    public static volatile SingularAttribute<Token, Participant> participant;

}