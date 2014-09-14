/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic.impl;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import jjlm.votes.logic.to.OrganizerTO;

public class UnikoLdapLookup {

    /**
     * Tries to find the user with the specified <code>uid</code> in the LDAP
     * directory of the University. When a matching user was found, firstname
     * and lastname are copied from LDAP. Otherwise, this method retuns
     * <code>null</code>.
     *
     * @param uid the user id (unix user name) to look for
     *
     * @return a {@link PersonTO} with uid, firstName, and lastName from LDAP,
     * or <code>null</code> when <code>uid</code> was not found.
     */
    public static OrganizerTO lookupPerson(String uid) {
        System.out.println("lookup "+uid);
        OrganizerTO organizer = new OrganizerTO();
        organizer.setName(uid);
        organizer.setUsername(uid+"@uni-koblenz.de");

        Hashtable<String, String> env = new Hashtable<>();

        String sp = "com.sun.jndi.ldap.LdapCtxFactory";
        env.put(Context.INITIAL_CONTEXT_FACTORY, sp);

        String ldapUrl = "ldaps://ldap.uni-koblenz.de";
        env.put(Context.PROVIDER_URL, ldapUrl);
        
       //env.put(Context.SECURITY_AUTHENTICATION, "simple");
       //env.put(Context.SECURITY_PRINCIPAL, "cn=S. henny, ou=people,ou=koblenz,dc=Uni-Koblenz-landau,dc=de");
       //env.put(Context.SECURITY_CREDENTIALS, "");

        DirContext dctx = null;
        try {
            organizer.setEmail(uid+"@uni-koblenz.de");
            dctx = new InitialDirContext(env);
            String base = "ou=people,ou=koblenz,dc=Uni-Koblenz-landau,dc=de";
            SearchControls sc = new SearchControls();
            String[] attributeFilter = {"uid", "sn", "givenname"};
            sc.setReturningAttributes(attributeFilter);
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "(&(objectClass=Person)(uid=" + uid + "))";
            NamingEnumeration results = dctx.search(base, filter, sc);
            if (results.hasMore()) {
                SearchResult sr = (SearchResult) results.next();
                Attributes attrs = sr.getAttributes();
                Attribute a = attrs.get("uid");
                if (a != null) {
                    organizer.setName((String) a.get());
                }
                a = attrs.get("givenname");
                if (a != null) {
                    organizer.setRealname((String) a.get());
                }

                a = attrs.get("sn");
                if (a != null) {
                    organizer.setRealname(organizer.getRealname() + " " + (String) a.get());
                    
                    organizer.setEncryptedPassword("123456");
                }

            } else {
                organizer = null;
            }
        } catch (NamingException ex) {
            System.err.println("exception: "+ex.getLocalizedMessage());
            Logger.getLogger(UnikoLdapLookup.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (dctx != null) {
                try {
                    dctx.close();
                } catch (NamingException ex) {
                    Logger.getLogger(UnikoLdapLookup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return organizer;
    }
}
