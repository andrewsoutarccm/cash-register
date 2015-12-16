
import java.math.BigDecimal;
import java.util.List;

import com.andrewsoutar.cmp128.Utilities.GenericScanner;
import com.andrewsoutar.cmp128.Utilities.UnaryFunction;

public class Prompt {
    public static GenericScanner kbdScanner = new GenericScanner ();

    private static UnaryFunction <BigDecimal, BigDecimal> positive =
        new UnaryFunction <BigDecimal, BigDecimal> () {
            public BigDecimal call (BigDecimal val) {
                return (val.signum () == 1 ? val : null);
            }
        };

    public static Item plu (ProductDatabase db) {
        return (kbdScanner.prompt (Integer.class, "Enter PLU code",
                                   new UnaryFunction <Item, Integer> () {
                                       public Item call (Integer plu) {
                                           return (db.itemsByPLU.get (plu));
                                       }
                                   }));
    }

    public static <T> T menuChoice (List <T> list) {
        int counter = 0;
        for (T elt : list) System.out.format ("%3s. %s%n", ++counter, elt);
        return (kbdScanner.prompt
                (Integer.class, "Choice", new UnaryFunction <T, Integer> () {
                        public T call (Integer index) {
                            try {return (list.get (index - 1));}
                            catch (IndexOutOfBoundsException e) {return (null);}
                        }
                    }));
    }

    public static BigDecimal price () {
        return (kbdScanner.prompt (BigDecimal.class, "Enter price", positive));
    }

    public static int quantity () {
        return (kbdScanner.prompt (Integer.class, "Enter quantity",
                                   new UnaryFunction <Integer, Integer> () {
                                       public Integer call (Integer val) {
                                           return (val > 0 ? val : null);
                                       }
                                   }));
    }

    public static boolean taxable () {
        return (kbdScanner.prompt (String.class,
                                   "Enter T for taxable or N for non-taxable",
                                   new UnaryFunction <Boolean, String> () {
                                       public Boolean call (String input) {
                                           switch (input.toLowerCase ()) {
                                           case "t": return (true);
                                           case "n": return (false);
                                           default:  return (null);
                                           }
                                       }
                                   }));
    }

    public static boolean end () {
        return (kbdScanner.prompt (String.class, "End order? [y/n]",
                                   new UnaryFunction <Boolean, String> () {
                                       public Boolean call (String response) {
                                           switch (response.toLowerCase ()) {
                                           case "y": case "yes": return (false);
                                           case "n": case "no":  return (true);
                                           default:              return (null);
                                           }
                                       }
                                   }));
    }

    public static BigDecimal paid () {
        return (kbdScanner.prompt
                (BigDecimal.class, "Enter amount tendered", positive));
    }
}
