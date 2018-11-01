package controller;

import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 *
 * @author giacomodeliberali
 */
public class VetoController extends Controller {

    /**
     * The vetoable change support
     */
    private VetoableChangeSupport vetoChangeSupport = new VetoableChangeSupport(this);

    /**
     * The empty constructor for the bean
     */
    public VetoController() {
    }

    /**
     * Adds a vetoable change listener for the property. The only property with veto support is ON_CHANNEL
     *
     * @param propertyName The property to observe and veto
     * @param listener The listener
     */
    public void addPropertyChangeListener(String propertyName, VetoableChangeListener listener) {
        vetoChangeSupport.addVetoableChangeListener(propertyName, listener);
    }

    /**
     * Removes a vetoable change listener for the property. The only property with veto support is ON_CHANNEL
     * @param propertyName The observed property
     * @param listener The registered listener
     */
    public void removePropertyChangeListener(String propertyName, VetoableChangeListener listener) {
        vetoChangeSupport.removeVetoableChangeListener(propertyName, listener);
    }

    /**
     * Fire a VetoableChange before changing the controller on property.
     * @param value The new value trying to set
     */
    @Override
    public void setOn(boolean value) {
        try {
            vetoChangeSupport.fireVetoableChange(ON_CHANNEL, on, value);
            super.setOn(value);
        } catch (PropertyVetoException ex) {
            System.err.println(VetoController.class.getName() + " => " + ex.toString());
        }
    }

}
