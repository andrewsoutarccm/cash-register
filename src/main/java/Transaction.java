
import com.andrewsoutar.cmp128.Utilities;
import com.andrewsoutar.cmp128.Utilities.GenericScanner;
import com.andrewsoutar.cmp128.Utilities.UnaryFunction;

public class Transaction {
    private final static float TAX_RATE = 0.07F;

    GenericScanner kbdScanner;

    Float subtotal = 0.0F;
    Float taxAmt = 0.0F;
    Float grandTotal;

    public Transaction (GenericScanner kbdScanner) {
        this.kbdScanner = kbdScanner;

        while (true) {
            Float itemTotal =
                (kbdScanner.<Float, Float> prompt
                 (Float.class, "Enter price",
                  new UnaryFunction <Float, Float> () {
                      public Float call (Float realPrice) {
                          if (realPrice > 0) {
                              return (realPrice);
                          } else {
                              return (null);
                          }
                      }
                  })) *
                (kbdScanner.<Integer, Integer> prompt
                 (Integer.class, "Enter quantity",
                  new UnaryFunction <Integer, Integer> () {
                      public Integer call (Integer realQuantity) {
                          if (realQuantity > 0) {
                              return (realQuantity);
                          } else {
                              return (null);
                          }
                      }
                  }));

            subtotal += itemTotal;

            taxAmt +=
                (kbdScanner.<Float, String> prompt
                 (String.class, "Enter T for taxable or N for non-taxable",
                  new UnaryFunction <Float, String> () {
                      public Float call (String response) {
                          switch (response.toLowerCase ()) {
                          case "t":
                              return (itemTotal * TAX_RATE);
                          case "n":
                              return (new Float (0.0F));
                          default:
                              return (null);
                          }
                      }
                  }));

            if (kbdScanner.<Boolean, String> prompt
                (String.class, "End order? [y/n]",
                 new UnaryFunction <Boolean, String> () {
                     public Boolean call (String response) {
                         switch (response.toLowerCase ()) {
                         case "y":
                         case "yes":
                             return (new Boolean (true));
                         case "n":
                         case "no":
                             return (new Boolean (false));
                         default:
                             return (null);
                         }
                     }
                 })) {
                break;
            }
        }

        grandTotal = subtotal + taxAmt;
    }

    public void run () {
        Utilities.printBordered (new String [] {
                String.format ("Subtotal: %.2f", subtotal),
                String.format ("Tax: %.2f", taxAmt),
                String.format ("Grand total: %.2f", grandTotal)
            });

        Float balance;

        for (balance = grandTotal; !(balance < 0.01F);
             balance -=
                 kbdScanner.<Float, Float> prompt
                 (Float.class, "Enter amount tendered",
                  new UnaryFunction <Float, Float> () {
                      public Float call (Float userInput) {
                          if (userInput > 0.0F) {
                              return (userInput);
                          } else {
                              return (null);
                          }
                      }
                  })) {
            Utilities.printBordered (new String [] {
                    String.format ("Balance due: %.2f", balance)
                });
        }

        Utilities.printBordered (new String [] {
                (balance < 0.0F)
                ? String.format ("Change due: %.2f", -balance)
                : "NO CHANGE DUE"
            });
    }
}
