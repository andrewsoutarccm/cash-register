
import com.andrewsoutar.cmp128.Utilities.GenericScanner;

public class Main {
    public static void main (String... args) {
        CashRegisterApplication app =
            new CashRegisterApplication (new GenericScanner ());
        app.run ();
    }
}
