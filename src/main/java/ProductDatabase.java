
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.HashMap;

import org.w3c.dom.Node;

public class ProductDatabase {
    public LinkedList <Item> items = new LinkedList <Item> ();
    public HashMap <Integer, Item> itemsByPLU = new HashMap <Integer, Item> ();

    public ProductDatabase (Node itemsXML) {
        for (Node itemNode = itemsXML.getFirstChild ();
             itemNode != null; itemNode = itemNode.getNextSibling ()) {
            if (itemNode.getNodeType () == Node.ELEMENT_NODE
                && itemNode.getNodeName () == "item") {
                Item i = new Item ();

                for (Node prop = itemNode.getFirstChild ();
                     prop != null; prop = prop.getNextSibling ()) {
                    if (prop.getNodeType () == Node.ELEMENT_NODE) {
                        String str = prop.getTextContent ();
                        switch (prop.getNodeName ()) {
                        case "plu":     i.plu     = new Integer (str);    break;
                        case "name":    i.name    = str;                  break;
                        case "price":   i.price   = new BigDecimal (str); break;
                        case "unit":    i.unit    = str;                  break;
                        case "taxable": i.taxable = new Boolean (str);    break;
                        }
                    }
                }
                items.add (i);
                if (i.plu != null) itemsByPLU.put (i.plu, i);
            }
        }
    }
}
