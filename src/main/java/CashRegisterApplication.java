
import        com.andrewsoutar.cmp128.Utilities.BasicMenuAction;
import        com.andrewsoutar.cmp128.Utilities.VoidFunction;
import static com.andrewsoutar.cmp128.Utilities.exitAction;
import static com.andrewsoutar.cmp128.Utilities.mainLoop;
import static com.andrewsoutar.cmp128.Utilities.printBordered;

public class CashRegisterApplication {
    private final ProductDatabase db;

    public CashRegisterApplication (ProductDatabase db) {
        this.db = db;
    }

    public void run () {
        mainLoop (Prompt.kbdScanner, new VoidFunction () {
                public void call () {
                    printBordered (new String [] {
                            "WELCOME TO THE CASH REGISTER APPLICATION!"
                        });
                }
            }, new BasicMenuAction [] {
                new BasicMenuAction ("display the order menu") {
                    public Boolean call () {
                        new Transaction (db).run ();
                        return (true);
                    }
                },
                exitAction (Prompt.kbdScanner),
            });
        printBordered (new String [] { "THANK YOU FOR SHOPPING HERE" });
    }
}
