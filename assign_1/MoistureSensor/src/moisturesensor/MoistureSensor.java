package moisturesensor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author giacomodeliberali
 */
public class MoistureSensor implements Serializable {
    
    /**
     * The channel of the property change listener for "decreasing" property 
     */
    public static final String DECREASING_CHANNEL = "decreasing";    
    
    /**
     * The channel of the property change listener for "currentHumidity" property
     */
    public static final String CURRENT_HUMIDITY_CHANNEL = "currentHumidity";


    /**
     * Indicates if the humidity is decreasing or not
     */
    private boolean decreasing;

    /**
     * The current humidity read by the sensor
     */
    private int currentHumidity;

    /**
     * The property change support used to fire property changes and notify
     * subscribers
     */
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * The timer used to schedule the sensor reads
     */
    private Timer timer = new Timer(false);

    /**
     * The random increment step used to generate increasing or decreasing
     * sequences of values
     */
    private int randomIncrementStep = 10;

    /**
     * Indicates if the sensor has started
     */
    private boolean isStarted = false;

    /**
     * Creates a new MoistureSensor
     */
    public MoistureSensor() {
        setDecreasing(false);
        setCurrentHumidity(0);
    }


    /**
     * Starts the sensor. Every second a new value is read
     */
    public void start() {
        
        if (!isStarted) {
            isStarted = true;
            setDecreasing(decreasing);
            setCurrentHumidity(currentHumidity);
        } else {
            return;
        }

        Random generator = new Random();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!decreasing) {
                    // read values must be increasing up to 100
                    int readHumidity = Math.min(generator.nextInt(randomIncrementStep) + currentHumidity, 100);
                    setCurrentHumidity(readHumidity);
                } else {
                    // read values must be decreasing down to 0
                    int readHumidity = Math.max(Math.min(currentHumidity - generator.nextInt(randomIncrementStep), 100), 0);
                    setCurrentHumidity(readHumidity);
                }
            }
        }, 0, 1000);
    }

    /**
     * Stops the sensor
     */
    public void stop() {
        timer.cancel();
        timer = new Timer();
        isStarted = false;
    }

    /**
     * If humidity is decreasing
     * @return True if the humidity is decreasing
     */
    public boolean isDecreasing() {
        return decreasing;
    }

    /**
     * Sets if humidity is decreasing
     */
    public void setDecreasing(boolean decreasing) {
        changes.firePropertyChange(DECREASING_CHANNEL, this.decreasing, decreasing);
        this.decreasing = decreasing;
    }

    
    /**
     * The current humidity
     * @return The current humidity
     */
    public int getCurrentHumidity() {
        return currentHumidity;
    }

    
    /**
     * Indicate if the current sensor is started and running
     * @return True if stared and running
     */
    public boolean isStarted() {
        return isStarted;
    }
    
    
    /**
     * Sets the current humidity
     * @param currentHumidity The current humidity
     */
    public void setCurrentHumidity(int currentHumidity) {
        if (currentHumidity >= 0 && currentHumidity <= 100) {
            changes.firePropertyChange(CURRENT_HUMIDITY_CHANNEL, this.currentHumidity, currentHumidity);
            this.currentHumidity = currentHumidity;
        }
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
