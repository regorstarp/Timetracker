import java.io.Serializable;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interval consists on a period of time dedicated to a certain task.
 * As interval is defined as a period of time we need to have the start
 * time and date and the end time and date of this interval.
 * When an interval is created
 *
 * An interval is responsible of keeping the Task updated every time
 * the interval updates, this is why the interval needs to
 * implement Observer, so it can be an observer of the class Clock,
 * and then the interval can update the task every time the Clock updates.
 * This class needs to implement Serializable so that we can save a
 * Interval Object to a file after running the program, so the next
 * time we run this program we can continue having the Interval Object as
 * it was last time we ran it.
 *
 *
 *
 * @uml.dependency   supplier="Observer"
 * @uml.dependency   supplier="Clock"
 */


@SuppressWarnings("serial")
public class Interval implements Observer, Serializable {
        /**
         * Logger of the Iterval class.
         * @uml.property  name="logger"
         */
        private static Logger logger = LoggerFactory.getLogger(Interval.class);
        //transient because we don't need it to be serialized
        /**
         * Clock instance.
         * @uml.property  name="clock"
         */
        private transient Clock clock = null;
        /**
         * Father task of the interval.
         * @uml.property  name="father"
         */
        private Task father = null;
        /**
         * Duration of the interval.
         * @uml.property  name="duration"
         */
        private long duration = 0;
        /**
         * Empty  constructor required for the object to be Serializable.
         * */
        public Interval() {
        }
        /**
         *Constructor of an interval with the given father task
         *The father adds the interval to its list of intervals,
         *It gets an instance of the clock to add itself to the
         *list of observers of the clock so it is notified every time the
         *clock changes.
         *The father sets its start date as the current date of the clock
         * @throws Exception
         */
        public Interval(final Task aFather) throws Exception {
            if (aFather == null) {
                throw new IllegalArgumentException(
                        "aFather null in Interval constructor");
            }
            setFather(aFather);
            this.clock = Clock.getInstance();
            this.clock.addObserver(this);
            logger.debug("Interval created");
            this.timelapse = new TimeLapse();
            setStartDate(this.clock.getDate());
            this.father.addInterval(this);
            checkInvariants();
            }
        private void checkInvariants() {
            assert (getFather() != null) : "null interval father";
            assert (getTimelapse() != null) : "null interval timelapse";
            assert (getStartDate() != null) : "null start date";
            assert (getEndDate() != null) : "null end date";
            if (!getStartDate().equals(getEndDate())) {
                assert (getStartDate().before(getEndDate()))
                : "start date after end date";
                assert (getDuration() == 0)
                : "duration not 0 when interval not started";
            }
            assert (getDuration() >= 0) : "negative duration";
        }
        /**
         *Setter of the date when the interval started.
         */
        private void setEndDate(final Date aEndDate) {
            //this.endDate = aEndDate;
            this.timelapse.setEndDate(aEndDate);
        }
        /**
         *Setter of the interval's father task.
         */
        private void setFather(final Task aFather) {
            this.father = aFather;
            }
        /**
         *Setter of the interval's start date.
         */
        private void setStartDate(final Date aDate) {
            //this.startDate = aDate;
            //
            this.timelapse.setStartDate(aDate);
        }
        /**
         *Getter of the date when the interval started.
         */
        public final Date getStartDate() {
            //return startDate;
            //
            return this.timelapse.getStartDate();
        }
        /**
         *Getter of the date when the interval ended.
         */
        public final Date getEndDate() {
            //return endDate;
            //
            return this.timelapse.getStartDate();
        }
        /**
         *This method is called whenever the observed clock is changed.
         *An application calls an Observable object's notifyObservers method
         *to have all the object's observers notified of the change.
         */
        public final void update(final Observable o, final Object arg) {
            if (o == null) {
                throw new IllegalArgumentException("null o in interval update");
            }
            if (arg == null) {
                throw new IllegalArgumentException(
                        "null arg in interval update");
            }
            //pre
            assert (((Clock) arg).getDate().after(getEndDate()))
            : "New end date before current end date";
            assert (((Clock) arg).getDate().after(getStartDate()))
            : "New end date before current start date";
            this.setEndDate(((Clock) arg).getDate());
            logger.debug("end date updated to " + getEndDate());
            logger.debug("time passed =  " + this.clock.getDelay());
            updateDuration(this.clock.getDelay());
            try {
                this.father.intervalUpdated(this.clock.getDelay(),
                        getEndDate());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            checkInvariants();
        }
        /**
         * Updates the interval duration by adding the given delay
         * to the current duration
         */
        private void updateDuration(final long delay) {
            //pre
            assert (delay > 0) : "delay not greater than 0";
            this.duration += delay;
            checkInvariants();
        }
        /**
         *Stops the interval by deleting the interval as an
         *observer of the clock.
         */
        public final void stopInterval() {
            this.clock.deleteObserver(this);
            logger.debug("Interval stopped");
            checkInvariants();
        }
        /**
     * @uml.property  name="timelapse"
     * @uml.associationEnd  inverse="interval:Timelapse"
     */
    private TimeLapse timelapse;
    /**
     * Getter of the property <tt>timelapse</tt>
     * @return  Returns the timelapse.
     * @uml.property  name="timelapse"
     */
    public final TimeLapse getTimelapse() {
        return timelapse;
    }
    /**
     * Setter of the property <tt>timelapse</tt>
     * @param timelapse  The timelapse to set.
     * @uml.property  name="timelapse"
     */
    public final void setTimelapse(final TimeLapse newTimelapse) {
        this.timelapse = newTimelapse;
    }
    public final void acceptVisitor(final Report report) throws Exception {
        // TODO Auto-generated method stub
        if (report == null) {
            throw new IllegalArgumentException(
                    "null report in interval acceptVisitor(report)");
                }
                report.visitInterval(this);
                checkInvariants();
            }
    public final Task getFather() {
        return this.father;
    }
    public final long getDuration() {
        return this.duration;
    }
    }
