package testbed.unsafe;
import java.io.*;
import java.util.*;
import java.io.IOException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class J08001 {
    public void f() throws ParserConfigurationException, SAXException,
                           IOException, XPathExpressionException {

        Properties props = new Properties();
        String fileName = "External.properties";
        FileInputStream in = new FileInputStream(fileName);
        props.load(in);

        
        String name = props.getProperty("name");
        String passwd = props.getProperty("password");

        DocumentBuilderFactory domFactory = DocumentBuilderFactory
            .newInstance();
        domFactory.setNamespaceAware(true);
        domFactory.setValidating(true);

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse("passwd.xml");

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        
        XPathExpression expr = xpath.compile("//users/user[login/text()='"
                                             + name + "' and password/text() = '" + passwd
                                             + "']/home_dir/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            String value = nodes.item(i).getNodeValue();
            if (value.indexOf(">") < 0)
                {
                    System.out.println(value);
                }
        }
    }
}
