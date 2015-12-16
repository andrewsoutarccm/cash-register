
import        java.math.BigDecimal;
import        java.text.NumberFormat;
import        java.util.LinkedList;
import        java.util.List;
import static java.lang.String.format;

import        com.andrewsoutar.cmp128.Utilities.BasicMenuAction;
import static com.andrewsoutar.cmp128.Utilities.mainLoop;
import static com.andrewsoutar.cmp128.Utilities.printBordered;

public class Transaction {
    private final static BigDecimal TAX_RATE = new BigDecimal ("0.07");
    private final static NumberFormat $$$ = NumberFormat.getCurrencyInstance ();
    private final ProductDatabase db;

    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal taxAmt = BigDecimal.ZERO;

    public Transaction (ProductDatabase db) {
        this.db = db;
    }

    private void add (Item item, int quantity) {
        BigDecimal cost = item.price.multiply (new BigDecimal (quantity));
        subtotal = subtotal.add (cost);
        if (item.isTaxable ()) taxAmt = taxAmt.add (cost.multiply (TAX_RATE));
    }

    public void run () {
        mainLoop (Prompt.kbdScanner, null, new BasicMenuAction [] {
                new BasicMenuAction ("process an item by PLU number") {
                    public Boolean call () {
                        Item item = Prompt.plu (db);
                        System.out.println (item);
                        add (item, Prompt.quantity ());
                        return (Prompt.end ());
                    }
                },
                new BasicMenuAction ("choose from a submenu of items") {
                    public Boolean call () {
                        List <Item> menu = new LinkedList <> ();
                        for (Item i : db.items) if (i.plu == null) menu.add (i);
                        add (Prompt.menuChoice (menu), Prompt.quantity ());
                        return (Prompt.end ());
                    }
                },
                new BasicMenuAction ("process an item manually") {
                    public Boolean call () {
                        add (new Item (Prompt.price ()), Prompt.quantity ());
                        return (Prompt.end ());
                    }
                }
            });

        BigDecimal total = subtotal.add (taxAmt);
        printBordered (new String [] {
                format ("Subtotal: %s", $$$.format (subtotal)),
                format ("Tax: %s", $$$.format (taxAmt)),
                format ("Total: %s", $$$.format (total)),
            });

        for (; total.signum () == 1; total = total.subtract (Prompt.paid ())) {
            System.out.format ("Balance due: %s%n", $$$.format (total));
        }

        printBordered (new String [] {
                total.signum () == 0 ? "NO CHANGE DUE"
                : format ("Change due: %s", $$$.format (total.abs ()))
            });
    }
}
