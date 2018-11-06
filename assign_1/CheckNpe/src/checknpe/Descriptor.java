package checknpe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A wrapper for a method/constructor descriptor
 * @author giacomodeliberali
 */
public class Descriptor implements Serializable {

    private String name;
    private String exception;
    private ArrayList<String> parameters;
    private boolean hasReferenceTypes;
    private boolean isNpeSensible;

    public Descriptor() {
        this.hasReferenceTypes = false;
        this.isNpeSensible = false;
        parameters = new ArrayList<>();
    }

    public Descriptor(String name) {
        this.name = name;
        this.hasReferenceTypes = false;
        this.isNpeSensible = false;
        parameters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public boolean hasReferenceTypes() {
        return hasReferenceTypes;
    }

    public void setHasReferenceTypes(boolean hasReferenceTypes) {
        this.hasReferenceTypes = hasReferenceTypes;
    }

    public boolean isNpeSensible() {
        return isNpeSensible;
    }

    public void setIsNpeSensible(boolean isNpeSensible) {
        this.isNpeSensible = isNpeSensible;
    }

    public void addParameter(String paramType) {
        this.parameters.add(paramType);
    }

    /**
     * Generate a string representation of this method
     * @return A string representing this method
     */
    @Override
    public String toString() {
        String str = "- " + name + "(";

        int index = 0;
        for (String p : parameters) {
            str += p;

            if (index < parameters.size() - 1) {
                str += ", ";
            }

            index++;
        }

        str += ")";
        if (hasReferenceTypes) {
            str += " -- NPE " + (isNpeSensible ? "positive" : "negative");
        }

        return str;
    }
}
