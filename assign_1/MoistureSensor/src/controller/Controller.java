package controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author giacomodeliberali
 */
public class Controller implements Serializable {

    /**
     * The channel of the property change listener for "on" property
     */
    public static final String ON_CHANNEL = "on";
    
    /**
     * Indicates if the irrigation is turned on or of
     */
    private boolean on;
    
    /**
     * The current humidity
     */
    private int locHumididy;

    /**
     * The property change support used to fire property changes and notify
     * subscribers
     */
    protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * Creates a new controller
     */
    public Controller() {
        checkIrrigation();
    }

    /**
     * Check the humidity value and turns on/off the irrigation accordingly
     */
    private void checkIrrigation() {
        if (locHumididy >= 90) {
            setOn(false);
        } else if (locHumididy <= 30) {
            setOn(true);
        }
    }

    /**
     * The humidity
     * @return The humidity
     */
    public int getLocHumididy() {
        return locHumididy;
    }

    /**
     * Sets the humidity
     * @param locHumididy The humidity 
     */
    public void setLocHumididy(int locHumididy) {
        if (locHumididy >= 0 && locHumididy <= 100) {
            this.locHumididy = locHumididy;
            this.checkIrrigation();
        }
    }

    /**
     * Indicates if the irrigation is turned on
     * @return True if turned on, false otherwise
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Turns on/off the irrigation
     * @param value The irrigation
     */
    public void setOn(boolean value) {
        this.changes.firePropertyChange(ON_CHANNEL, on, value);
        on = value;
    }

    /**
     * Add a new property change listener
     * @param propertyName The property to observe
     * @param listener The listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changes.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a property change listener
     * @param propertyName The observed property
     * @param listener The registered listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        changes.removePropertyChangeListener(propertyName, listener);
    }
}
