import java.util.Observable;

/**
 * The Observer defines a one-to-many relationship so that when one object
 * changes state, the others are notified and updated automatically.
 */
public interface Observer {
        /**
         *This method is called whenever the observed clock is changed.
         *An application calls an Observable object's notifyObservers method
         *to have all the object's observers notified of the change.
         */
        void update(Observable arg0, Object arg1);
}
