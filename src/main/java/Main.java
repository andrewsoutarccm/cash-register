
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;

import org.xml.sax.SAXException;

public class Main {
    public static void main (String... args) {
        Node itemsXML;
        try {
            itemsXML =
                DocumentBuilderFactory.newInstance ().newDocumentBuilder ()
                .parse (new File (Main.class.getClassLoader ()
                                  .getResource ("items.xml")
                                  .getFile ()))
                .getDocumentElement ();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw (new RuntimeException (e));
        }

        if (itemsXML.getNodeType () != Node.ELEMENT_NODE
            || itemsXML.getNodeName () != "items") {
            throw (new RuntimeException ("Invalid format in the items file"));
        }

        CashRegisterApplication app =
            new CashRegisterApplication (new ProductDatabase (itemsXML));
        app.run ();
    }
}
