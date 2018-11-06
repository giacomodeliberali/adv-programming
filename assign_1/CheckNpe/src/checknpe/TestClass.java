package checknpe;

/**
 *
 * @author giacomodeliberali
 */
public class TestClass {

    public TestClass() {
    }

    public TestClass(Object obj) {
        obj.equals(obj);
    }

    public TestClass(Object first, Object second) {
        if (first != null && second != null) {
            first.equals(second);
        }
    }

    public String npePositive(Object obj) {
        return obj.toString();
    }

    public int npeNegative(int obj) {
        return obj;
    }

    public boolean npeNegative(boolean obj) {
        return obj;
    }

    public String npeNegative(Object obj) {
        if (obj != null) {
            return obj.toString();
        }

        return null;
    }

}
