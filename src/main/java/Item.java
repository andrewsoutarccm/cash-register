
import        java.math.BigDecimal;
import        java.text.NumberFormat;
import static java.lang.String.format;

public class Item {
    public Integer plu;
    public String name;
    public BigDecimal price;
    public String unit;
    public Boolean taxable;

    public boolean isTaxable () {
        if (taxable == null) taxable = Prompt.taxable ();
        return (taxable);
    }

    public Item () {}

    public Item (BigDecimal price) {
        this.price = price;
    }

    public String toString () {
        return (format ("%5s %-30s%s%s",
                        (plu != null ? plu.toString () : ""), name,
                        NumberFormat.getCurrencyInstance ().format (price),
                        (unit != null ? ("/" + unit) : "")));
    }
}
