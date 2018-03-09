package testbed.unsafe;

import java.io.*;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class J09001 {
    public void f() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");

        try {
            
            javax.naming.directory.DirContext ctx = new InitialDirContext(env);

            
            Properties props = new Properties();
            String fileName = "ldap.properties";
            FileInputStream in = new FileInputStream(fileName);
            props.load(in);

            
            String name = props.getProperty("name");
            String filter = "(name =" + name + ")";

            
            NamingEnumeration answer = ctx.search("ou=NewHires", filter,
                                                  new SearchControls());

            printSearchEnumeration(answer);
            ctx.close();
        } catch (NamingException e) {
            System.err.print("...'");
        } catch (IOException e) {
            System.err.print("...'");
        }
    }

    public void printSearchEnumeration(NamingEnumeration value) {
        try {
            while (value.hasMore()) {
                SearchResult sr = (SearchResult) value.next();
                System.out.println(">>>" + sr.getName());
                System.out.println(sr.getAttributes());
            }
        } catch (NamingException e) {
            System.err.print("...'");
        }
    }
}
