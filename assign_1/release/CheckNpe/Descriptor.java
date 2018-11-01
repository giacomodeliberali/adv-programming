/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checknpe;

import java.util.ArrayList;

/**
 *
 * @author giacomodeliberali
 */
public class Descriptor {

    String name;
    String exception;
    ArrayList<String> parameters;
    boolean hasReferenceTypes;
    boolean isNpeSensible;

    public Descriptor() {
    }
    
    

    public Descriptor(String name) {
        
        this.name = name;
        this.hasReferenceTypes = false;       
        this.isNpeSensible = false;

        parameters = new ArrayList<>();
    }

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
