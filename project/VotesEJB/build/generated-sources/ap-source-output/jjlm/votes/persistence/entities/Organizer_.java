package jjlm.votes.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jjlm.votes.persistence.entities.Poll;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-10-05T16:05:45")
@StaticMetamodel(Organizer.class)
public class Organizer_ extends NamedEntity_ {

    public static volatile SetAttribute<Organizer, Poll> polls;
    public static volatile SingularAttribute<Organizer, String> email;
    public static volatile SingularAttribute<Organizer, String> encryptedPassword;
    public static volatile SingularAttribute<Organizer, String> realname;
    public static volatile SingularAttribute<Organizer, String> username;

}