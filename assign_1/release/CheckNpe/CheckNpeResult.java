package checknpe;

import java.util.ArrayList;

/**
 * A DTO for the check results
 *
 * @author giacomodeliberali
 */
public class CheckNpeResult {

    /**
     * The descriptors of constructors
     */
    private ArrayList<Descriptor> constructors;

    /**
     * The descriptors of methods
     */
    private ArrayList<Descriptor> methods;

    /**
     * Creates a new wrapper object for results
     *
     * @param constructors The descriptors of constructors
     * @param methods The descriptors of methods
     */
    public CheckNpeResult(ArrayList<Descriptor> constructors, ArrayList<Descriptor> methods) {
        this.constructors = constructors;
        this.methods = methods;
    }

    /**
     * The constructors descriptors
     * @return The constructors descriptors
     */
    public ArrayList<Descriptor> getConstructors() {
        return constructors;
    }
    
    /**
     * The methods descriptors
     * @return The methods descriptors
     */
    public ArrayList<Descriptor> getMethods() {
        return methods;
    }
    
    

}
