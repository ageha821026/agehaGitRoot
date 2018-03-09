package testbed.unsafe;
import java.io.*;
import java.util.*;

import javax.naming.*;
import javax.naming.directory.*;
import javax.xml.xquery.*;

public class J07001 {
    public void f() throws NamingException {
        try {
            Properties props = new Properties();
            String fileName = "MakeDB.properties";
            FileInputStream in = new FileInputStream(fileName);
            props.load(in);

            
            String name = props.getProperty("name");

            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
            javax.naming.directory.DirContext ctx = new InitialDirContext(env);

            javax.xml.xquery.XQDataSource xqds = (javax.xml.xquery.XQDataSource) ctx
                .lookup("xqj/personnel");
            javax.xml.xquery.XQConnection conn = xqds.getConnection();

            String es = "doc('users.xml')/userlist/user[uname='" + name + "']";

             
            XQPreparedExpression expr = conn.prepareExpression(es);
            XQResultSequence result = expr.executeQuery();

            while (result.next()) {
                String str = result.getAtomicValue();
                if (str.indexOf('>') < 0)
                    {
                        System.out.println(str);
                    }
            }
            result.close();
            expr.close();
            conn.close();
        } catch (javax.xml.xquery.XQException ex) {
            System.out.println("Exception: " + ex);
        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }
    }
}
