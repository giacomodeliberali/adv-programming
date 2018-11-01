# Irrigation system

The system is composed by 3 beans:
- MoistureSensor ([JAR](./lib/MoistureSensor.jar) / [JAVA](./MoistureSensor.java))
- Controller ([JAR](./lib/Controller.jar) / [JAVA](./Controller.java))
- DashboardFrame ([JAR](./IrrigationDashboard.jar) / [JAVA](./DashboardFrame.java))

## Manual Irrigation

Additional files:
- VetoController ([JAR](./lib/Controller.jar) / [JAVA](./VetoController.java)): this controller extends the one defined in the previous exercise adding the possibility to register a `VetoableChangeListener` which is called before updating the properties (the only `on` property, really). Also overrides the setter for the `on` property in order to fire the *VetoableChange* and to catch the eventual exception raised by subscribers. If no exception is thrown the super *setOn(boolean value)* is called in order to notify also subscribers of base class.

- ManualDashboardFrame ([JAR](./IrrigationDashboard.jar) / [JAVA](./ManualDashboardFrame.java)): extends the previous defined frame adding a new button and replacing the standard `Controller` with the new derived `VetoController`. In this way a new `VetoableChangeListener` can be registered in this frame in order to throw a `PropertyVetoException` if humidity constraints are not satisfied.

### Who plays the role of vetoing the change?

The new [`ManualDashboardFrame`](./ManualDashboardFrame.java) plays the role of vetoing the change when the conditions are not satisfied. Making this choice external to the `Controller` make possible to change this logic without touching the controller code.

### Code reuse

Almost all code from exercise 1 is reused. Two new classes are introduced, but both inherit from classes defined previously.

