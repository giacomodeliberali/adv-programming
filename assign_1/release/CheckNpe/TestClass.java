/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public String npeNegative(Object obj) {
        if (obj != null) {
            return obj.toString();
        }

        return null;
    }

}
