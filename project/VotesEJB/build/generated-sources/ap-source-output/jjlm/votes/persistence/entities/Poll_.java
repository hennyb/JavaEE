package jjlm.votes.persistence.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Item;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Token;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-05T16:05:45")
@StaticMetamodel(Poll.class)
public class Poll_ extends NamedEntity_ {

    public static volatile SingularAttribute<Poll, Date> startPoll;
    public static volatile SetAttribute<Poll, Organizer> organizer;
    public static volatile SingularAttribute<Poll, String> description;
    public static volatile SetAttribute<Poll, Token> tokens;
    public static volatile SingularAttribute<Poll, Date> endPoll;
    public static volatile SetAttribute<Poll, Item> items;
    public static volatile SetAttribute<Poll, Participant> participants;

}