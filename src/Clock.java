import java.util.Observable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *The clock is the class responsible for checking
 *the current time and notifying
 *all the observers that the time has changed.
 *
 *
 *As the Clock needs to be observable it has to extend Observable
 */


public class Clock extends Observable {
    /**
     * Default delay for the clock.
     */
    private static final int DEFAULT_DELAY = 1000;
    /**
     * Logger of the CLock class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Clock.class);
    /**
     * Instance of the clock.
     * @uml.property  name="clock"
     */
    private static  Clock clock = null;
    /**
     * Delay of the clock uodate.
     * @uml.property  name="delay"
     */
    private long delay = DEFAULT_DELAY;
    /**
     * timer class instance.
     * @uml.property  name="timer"
     */
    @SuppressWarnings("unused")
    private Timer timer = null;
    /**
     * Current date of the clock.
     * @uml.property  name="date"
     */
    private Date date = null;
    /**
     *Constructor of a new clock.
     *Creates a new clock an sets up the clock's date
     *Creates a new timer and gives it the clock
     */
    public Clock() {
        setDate(new Date());
        logger.debug("Clock created");
        this.timer = new Timer(this);
    }
    /**
     *Gets a clock instance
     *If the clock hasn't been created, creates the clock.
     */
    static final Clock getInstance() {
        if (clock == null) {
            clock = new Clock();
        }
        return clock;
    }
    /**
     *Setter of the delay of the timer.
     */
    public final void setDelay(final long aDelay) {
        this.delay = aDelay;
    }

    /**
     * Setter of the property <tt>date</tt>.
     * @param aDate  The date to set.
     * @uml.property  name="date"
     */
    private void setDate(final Date aDate) {
        this.date = aDate;
    }
    /**
     *Getter of the delay of the timer.
     */
    public final long getDelay() {
        return delay;
    }
    /**
     * Getter of the property <tt>date</tt>.
     * @return  Returns the date.
     * @uml.property  name="date"
     **/
    public final Date getDate() {
        return date;
        }
    /**
     * Updates the state of the clock by getting the current date,
     *setting changed the observable clock and notifying the clock
     *observers.
     *Sets a new date to the clock each time the timer updates
     *Sets the state to changed and notifies all the observers
     */
    private void update() {
        setDate(new Date());
        logger.debug("Clock date updated to: " + getDate());
        this.setChanged();
        this.notifyObservers(this);
        logger.debug("Clock updated");
        }
    /**
     * Timer is a thread class, while the program is running, waits
     * as many milliseconds as the delay and then calls the clock to update.
     */

    public final class Timer extends Thread {
        /**
         * Logger of the timer class.
         * @uml.property  name="logger"
         */
        private final Logger logger = LoggerFactory.getLogger(Timer.class);
        /**
         * Current date of the timer.
         * @uml.property  name="date"
         */
        @SuppressWarnings("unused")
        private Date date = null;
        /**
         * Clock instance.
         * @uml.property  name="clock"
         */
        private Clock clock = null;
        /**
         *Constructor of timer.
         *Creates a new timer with a given clock
         *This thread will die when the program stops
         *Calls the start function to start the thread
         */
        private Timer(final Clock aClock) {
            setClock(aClock);
            setDaemon(true); //mor quan acaba el main
            start();
            logger.debug("Timer created");
        }
        /**
         *Setter of the clock of the timer.
         */
        private void setClock(final Clock aClock) {
            this.clock = aClock;
        }
        /**
         *Run method of the thread timer
         *While the program is running, waits the delay and updates
         *the clock after.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(this.clock.getDelay());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                this.clock.update();
            }
        }
    }
}
