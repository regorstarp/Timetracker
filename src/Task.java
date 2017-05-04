

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Task is a type of Activity that contains a collection of
 * intervals dedicated to this task.
 * Task duration is calculated as the sum of duration of each of its intervals.
 * When we create a new Task we need to define a name, description and father.
 * The father is the project that contains this task.
 * A task has the ability to add new child interval to the
 * list of intervals it has.
 * The task will update its end time and date and duration every
 * time an interval updates.
 * And it will tell its father project to update every time the task updates.
 * When a task is started a new interval will be created and the interval will
 * run until the task is stopped, once the task stops the currently running
 * interval stops running.
 * To represent that a Task can have a collection of intervals we define a
 * composite from the Task class to Interval class.
 * This class needs to implement Serializable so that we can save a Task Object
 * to a file after running the program, so the next time we run this
 * program we can continue having the Task Object as it was last time we ran it.
 */


@SuppressWarnings("serial")
public class Task extends Activity implements java.io.Serializable {
    /**
     * List of intervals of the Task.
     * @uml.property name="interval"
     * @uml.associationEnd multiplicity="(1 -1)" ordering="true"
     * aggregation="composite" inverse="task:Interval"
     * @uml.association name="intervals"
     */
    private List<Interval> interval = new ArrayList<Interval>();
    /**
     * Logger of the Task class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Task.class);
    /**
     * Stores if the task is active or not active.
     * @uml.property  name="isActive"
     */
    private boolean isActive = false;
    /**
     * Empty no-arguments constructor required for
     * the object to be Serializable.
     */
    public Task() {
    }
    /**
     * Returns the list of intervals of the task.
     */
    public final List<Interval> getIntervals() {
        return interval;
        }
    /**
     *Constructor of a child task.
     *Creates a new task with a given name ,description and father
     *project of the task
     */
    public Task(final String name, final String description,
            final Project father) throws Exception {
        super(name, description, father);
        logger.debug(getName() + " Created");
        checkInvariants();
        }
    private void checkInvariants() {
        assert (getFather() != null) : "task father null";
        assert (getTimelapse() != null) : "null timelapse";
        assert (getStartDate() != null) : "null start date";
        assert (getEndDate() != null) : "null end date";
        if (!getStartDate().equals(getEndDate())) {
            assert (getStartDate().before(getEndDate()))
            : "start date after end date";
        }
        assert (getDuration() >= 0) : "negative duration";
    }
    /**
     *Returns if the task is active or not active.
     */
     private boolean isActive() {
         return isActive;
     }
    /**
     * Starts counting the time dedicated to the task by creating a new interval
     * that is going to be observing the clock and updating the interval during
     * the duration of the interval.
     * @throws Exception
     */
     public final void start() throws Exception {
        if (!isActive()) {
            this.isActive = true;
            Interval i = new Interval(this);
            //sets the start date of the task if its the first time
            //it is started
            if (getStartDate() == null) {
                this.setStartDate(i.getStartDate());
            }
            //sets the start date of the father of the task if it is
            //the first child to start
            if (getFather().getStartDate() == null) {
                getFather().setStartDate(i.getStartDate());
            }
            logger.debug(getName() + " started");
        }
        checkInvariants();
        }
     /**
     *Stops counting the time of a task by stopping the last running interval.
     */
     public final void stop() {
        if (isActive()) {
            this.isActive = false;
            getLastInterval().stopInterval();
            logger.debug(getName() + " stopped");
        }
        checkInvariants();
     }
     public final Interval getLastInterval() {
         return this.interval.get(interval.size() - 1);
     }
     /**
     *Adds an interval to the list of intervals of the task.
     */
    public final void addInterval(final Interval aInterval) throws Exception {
        if (aInterval == null) {
            throw new IllegalArgumentException(
                    "aInterval null in task addInterval");
        }
        this.interval.add(aInterval);
        //post
        assert (aInterval == getLastInterval())
        : "interval not set correctly";
        logger.debug("new interval added to task:" + getName());
        //checkInvariants();
        }
    /**
     *Overriding abstract method from abstract class.
     *Accepts the printer visitor that is going to print the task
     */
    public final void acceptVisitor(final Printer printer) throws Exception {
        if (printer == null) {
            throw new IllegalArgumentException(
                    "printer null in task acceptVisitor");
        }
        printer.visitTask(this);
        checkInvariants();
    }
    /**
     *Called every time an interval is updated, getting the time passed since
     *the last update and the date of the actual update.
     *It adds the time passed to the total duration of the task and it modifies
     *the end date of the task
     *Tells the father project to update giving it the time passed and the
     *end date of the task
     */
    public final void intervalUpdated(final long timePassed,
            final Date endDate) throws Exception {
        if (endDate == null) {
            throw new IllegalArgumentException(
                    "endDate null in task intervalUpdated");
        }
        //pre
        assert (timePassed > 0) : "timePassed not greater than 0";
        assert (endDate.after(getStartDate())) : "end date before start date";
        updateDuration(timePassed);
        logger.debug(getName() +  " duration updated to "
        + getDuration());
        setEndDate(endDate);
        logger.debug(getName() + " end date updated to "
        + getEndDate());
        getFather().update(timePassed, getEndDate());
        checkInvariants();
        }
    @Override
    final void acceptVisitor(final Report report) throws Exception {
        if (report == null) {
        throw new IllegalArgumentException(
                "report null in task acceptVisitor");
        }
        report.visitTask(this);
        checkInvariants();
    }
}
