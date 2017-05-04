
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Printer is responsible to go over the tree and print
 * every activity of the tree, including the root.
 * The printer is given a root so it has a start point to go over the tree
 *
 * It has to implement Visitor because he needs to access every element
 * of the tree to be able to print it.
 * It also has to implement Observer because he needs to print the whole
 * tree every time the clock update thats why the printer needs to be
 * an observer of the clock.
 *
 * @uml.dependency   supplier="Observer"
 */


public class Printer implements Visitor, Observer {
    /**
     * Number 1000 constant used to convert milliseconds to seconds.
     */
    private static final int ONE_THOUSAND = 1000;
    /**
     * Used to convert hours to seconds.
     */
    private static final int SECONDS_IN_AN_HOUR = 3600;
    /**
     * Used to convert hours to minutes.
     */
    private static final int MINUTES_IN_AN_HOUR = 60;
    /**
     * Used to convert minutes to seconds.
     */
    private static final int SECONDS_IN_A_MINUTE = 60;
    /**
     * Logger of the Printer class.
     * @uml.property  name="logger"
     */
    private static Logger logger = LoggerFactory.getLogger(Printer.class);
    /**
     * The clock the printer needs to be the observer of.
     * @uml.property  name="clock"
     */
    private Clock clock = null;
    /**
     * The root project of the activities.
     * @uml.property  name="rootProject"
     */
    private Project rootProject;
    /**
     * Constructor of the printer class, creates a printer with the given
     * root project.
     * It gets an instance of the clock so he can tell the clock to add
     * the printer as an observer
     */
    public Printer(final Project aRootProject) {
        this.rootProject = aRootProject;
        this.clock = Clock.getInstance();
        this.clock.addObserver(this);
        logger.debug("Printer created");
    }
    /**
     * The printer visits a project and all its childs.
     * It prints the name, start date, end date and duration.
     * The duration is shown in "hh:mm:ss" format.
     */
    public final void visitProject(final Project project) throws Exception {
        System.out.print(project.getName());
        System.out.print(": ");
        System.out.print(project.getStartDate());
        System.out.print(": ");
        System.out.print(project.getEndDate());
        System.out.print(": ");
        System.out.println(milliSecondsToString(project.getDuration()));
        for (Activity a: project.getChildActivities()) {
            a.acceptVisitor(this);
        }
    }
    /**
     * The printer visits a task.
     * It prints the name, start date, end date and duration.
     * The duration is shown in "hh:mm:ss" format
     */
    public final void visitTask(final Task task) {
        System.out.print(task.getName());
        System.out.print(": ");
        System.out.print(task.getStartDate());
        System.out.print(": ");
        System.out.print(task.getEndDate());
        System.out.print(": ");
        System.out.println(milliSecondsToString(task.getDuration()));
    }
    /**
     * The printer visits a interval.
     * To-Do when implemented
     */
    public void visitInterval(final Interval interval) {
    }
    /**
     *This method is called whenever the observed clock is changed.
     *An application calls an Observable object's notifyObservers method
     *to have all the object's observers notified of the change.
     *Every time the clock changes the printer prints the root project and then
     *goes over every leaf and also prints it.
     */
    public final void update(final Observable o, final Object arg) {
        logger.debug("Start printing");
        System.out.println("----------------------------------------------");
        System.out.print(this.rootProject.getName());
        System.out.print(": ");
        System.out.print(this.rootProject.getStartDate());
        System.out.print(": ");
        System.out.print(this.rootProject.getEndDate());
        System.out.print(": ");
        System.out.println(milliSecondsToString(
                this.rootProject.getDuration()));
        for (Activity a: this.rootProject.getChildActivities()) {
            try {
                a.acceptVisitor(this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     *Converts a long  from milliseconds to a string representing
     *the time in hh:mm:ss format, so it's more human readable.
     */
    private String milliSecondsToString(final long milliSeconds) {
        int seconds = (int) (milliSeconds / ONE_THOUSAND);
        int hours = (int) seconds / SECONDS_IN_AN_HOUR;
        int minutes = (int) (seconds % SECONDS_IN_AN_HOUR) / MINUTES_IN_AN_HOUR;
        seconds = (int) seconds % SECONDS_IN_A_MINUTE;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
