
import java.math.BigDecimal;

public class a_0bigdecimal {

    public static void main(String[] agrs) {
        BigDecimal good = new BigDecimal("0.21");
        System.out.println(good);
        BigDecimal bad = new BigDecimal(0.21F);
        System.out.println(bad);
    }
}
