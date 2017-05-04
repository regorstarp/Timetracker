

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Project is a type of Activity that consists on an entity that allows the
 * client to organize the tasks he/she is working on.
 * We can define a Project as a collection of tasks and projects, which are
 * the two types of activities we have defined in this program.
 * Project duration is calculated as the sum of his child tasks and
 * projects duration.
 * When we create a new Project we need to define a name,
 * description and father.
 * The father is the project that contains this project.
 * The father will be null if it is the Root Project, which has no father.
 * All the other sub projects will have their own father.
 * A project has the ability to add new child activity to the list
 * of activities it has.
 * The project will update its end time and date and duration every
 * time a child updates. And if the project
 * is not the root project it will tell its father to update.
 *
 * To represent that a Project can have a collection of projects and tasks,
 * we define a composite from the Project class to the Activities class.
 * This class needs to implement Serializable so that we can save a
 * Project Object to a file after running the program, so the next time
 * we run this program we can continue having the Project Object as it
 * was last time we ran it.
 *
 */


@SuppressWarnings("serial")
public class Project extends Activity implements Serializable {
    /**
     * Logger of the Project class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Project.class);
    /**
     *Empty no-arguments constructor required for the object to be Serializable.
     */
    /**
     * List of child activities of the project.
     * @uml.property  name="activity"
     * @uml.associationEnd  multiplicity="(1 -1)"
     * ordering="true" aggregation="composite" inverse="project:Activity"
     * @uml.association  name="activities"
     */
    private List<Activity> activity = new ArrayList<Activity>();
    public Project() {
    }
    /**
     *Constructor of a project with a given name, description and father
     *If this is the root project then the father will be null, if not a
     *father needs to be given.
     *Calls the constructor of activity
     */
    public Project(final String name, final String description,
            final Project father) throws Exception {
        super(name, description, father);
        logger.debug(name + " Created");
        checkInvariants();
     }
     protected final void checkInvariants() {
        assert (getName() != null) : "name null";
        assert (getDescription() != null) : "description null";
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
     * Returns the list of child activities of the project.
     */
    public final List<Activity> getChildActivities() {
        return activity;
    }
    /**
     * *Adds a child activity to the list of child activities of the project.
     */
    public final void addChildActivity(final Activity childActivity)
            throws Exception {
        if (childActivity == null) {
            throw new IllegalArgumentException(
                    "childActivity null arg in project addChildActivity");
        }
        this.activity.add(childActivity);
        logger.debug(childActivity.getName() + " Added to "
        + getName() + " activities");
        }
    /**
     *Called every time a child activity is updated.
     *It receives the time passed and the last end date
     *It updates the project duration by adding the time passed to
     *the total duration of the project and updates the end date of
     *the project with the end date given
     *If it isn't the root project it tells its father to update giving
     *him the time passed and end date received
     */
    public final void update(final long timePassed, final Date endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException(
                    "endDate null in project update");
        }
        //pre
        assert (timePassed > 0) : "timePassed not greater than 0";
        assert (endDate.after(getStartDate())) : "end date before start date";
        updateDuration(timePassed);
        logger.debug(getName() +  " duration updated to " + getDuration());
        setEndDate(endDate);
        logger.debug(getName() +  " end date updated to " + getEndDate());
        if (getFather() != null) {
            getFather().update(timePassed, endDate);
            }
        checkInvariants();
        }
    /**
     * Overriding abstract method from the Activity class.
     * Accepts the printer visitor that is going to print the project
     */
    public final void acceptVisitor(final Printer printer) throws Exception {
        if (printer == null) {
            throw new IllegalArgumentException(
                    "printer null in project acceptVisitor");
        }
        printer.visitProject(this);
        checkInvariants();
    }
    /**
     * @uml.property  name="report"
     * @uml.associationEnd  multiplicity="(0 -1)"
     * ordering="true" inverse="project:Report"
     */
    private List<Report> report = new ArrayList<Report>();
    /**
     * Getter of the property <tt>report</tt>
     * @return  Returns the report.
     * @uml.property  name="report"
     */
    public final List<Report> getReport() {
        return report;
    }
    @Override
    final void acceptVisitor(final Report visitorReport) throws Exception {
        if (visitorReport == null) {
            throw new IllegalArgumentException(
                    "visitorReport null in project acceptVisitor");
        }
        visitorReport.visitProject(this);
    }
}
