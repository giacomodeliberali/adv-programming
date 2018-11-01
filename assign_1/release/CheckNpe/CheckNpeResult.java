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
public class CheckNpeResult {

    ArrayList<Descriptor> constructors;
    ArrayList<Descriptor> methods;

    public CheckNpeResult(ArrayList<Descriptor> constructors, ArrayList<Descriptor> methods) {
        this.constructors = constructors;
        this.methods = methods;
    }

}
