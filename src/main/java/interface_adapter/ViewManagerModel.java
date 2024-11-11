package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active.
 */
public class ViewManagerModel extends ViewModel<String> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String state = "";

    public ViewManagerModel() {
        super("view manager");
    }

    /**
     * Sets the current state (active view) and notifies listeners.
     * @param newState the name of the new view
     */
    @Override
    public void setState(String newState) {
        String oldState = this.state;
        this.state = newState;
        support.firePropertyChange("state", oldState, newState);
    }

    /**
     * Adds a PropertyChangeListener to listen for state changes.
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a PropertyChangeListener.
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
