
import com.andrewsoutar.cmp128.Utilities;
import com.andrewsoutar.cmp128.Utilities.GenericScanner;
import com.andrewsoutar.cmp128.Utilities.MenuAction;
import com.andrewsoutar.cmp128.Utilities.VoidFunction;

public class CashRegisterApplication {
    private GenericScanner kbdScanner;

    public CashRegisterApplication (GenericScanner kbdScanner) {
        this.kbdScanner = kbdScanner;
    }

    public void run () {
        Utilities.mainLoop (kbdScanner, new VoidFunction () {
                public void call () {
                    Utilities.printBordered (new String [] {
                            "WELCOME TO THE CASH REGISTER APPLICATION!"
                        });
                }
            }, new MenuAction [] {
                new MenuAction () {
                    public String getName () {
                        return ("display the order menu");
                    }

                    public Boolean call () {
                        Transaction transaction = new Transaction (kbdScanner);
                        transaction.run ();
                        return (true);
                    }
                },
                new MenuAction () {
                    public String getName () {
                        return ("exit");
                    }

                    public Boolean call () {
                        return (Utilities.exitLoop (kbdScanner));
                    }
                }
            });
        Utilities.printBordered (new String [] {
                "THANK YOU FOR SHOPPING HERE"
            });
    }
}
