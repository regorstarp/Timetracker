import java.io.Serializable;
import java.util.Date;

/**
 * Abstract class Activity, an activity can either be a task or project.
 * @uml.dependency   supplier="Observer"
 */


@SuppressWarnings("serial")
public abstract class Activity implements Serializable {
    /**
     *Empty no-arguments constructor required for the object to be Serializable.
     */
    public Activity() {
    }
    /**
     * Name of the activity.
     * @uml.property  name="name"
     */
    private String name = null;

    /**
     * Description of the activity.
     * @uml.property  name="description"
     */
    private String description = null;
    /**
     * Father project of the activity.
     * @uml.property  name="father"
     */
    private Project father = null;

    /**
     *Constructor of a new activity.
     *Creates an activity with a given name ,description and the
     *activity's father project
     *If it isn't the root project, it tells its father to add
     *this project to its child activities
     */
    public Activity(final String aName, final String aDescription
            , final Project aFather) throws Exception {
        if (aName == null) {
            throw new IllegalArgumentException(
                    "aName null in Activity constructor");
        }
        if (aDescription == null) {
            throw new IllegalArgumentException(
                    "aDescription null in Activity constructor");
        }
        this.setName(aName);
        this.setDescription(aDescription);
        this.setFather(aFather);
        setTimelapse(new TimeLapse());
        Date date = new Date();
        setStartDate(date);
        setEndDate(date);
        if (aFather != null) {
            aFather.addChildActivity(this);
        }
    }
    /**
     *Sets the activity's father project.
     */
    private void setFather(final Project aFatherProject) {
        this.father = aFatherProject;
    }
    /**
     *Getter the activity's father project.
     */
    protected final Project getFather() {
        return this.father;
    }
    /**
     *Getter of the activity's name.
     */
    protected final String getName() {
        return this.name;
    }

    /**
     *Setter of the activity's name.
     */
    private void setName(final String aName) {
        this.name = aName;
    }

    /**
     *Setter of the activity's description.
     */
    private void setDescription(final String aDescription) {
        this.description = aDescription;
    }
    /**
     *Getter of the activity's description.
     */
    protected final String getDescription() {
        return this.description;
    }

    /**
     *Getter of the activity's duration.
     */
    protected final long getDuration() {
        //return this.duration;
        //
        return getTimelapse().getDuration();
    }
    /**
     * Updates the activity's duration by adding the time passed
     * to the total duration of the activity.
     */
    protected final void updateDuration(final long newTimePassed) {
        //this.duration += newTimePassed;
        //pre
        assert (newTimePassed > 0) : "newTimePassed not greater than 0";
        getTimelapse().update(newTimePassed);
    }
    /**
     *Getter of the activity's end date.
     */
    public final Date getEndDate() {
        //return endDate;
        //
        return getTimelapse().getEndDate();
    }
    /**
     *Setter of the activity's end date.
     */
    public final void setEndDate(final Date aEndDate) {
        //this.endDate = aEndDate;
        //
        getTimelapse().setEndDate(aEndDate);
    }

    /**
     *Getter of the activity's start date.
     */
    public final Date getStartDate() {
        //return startDate;
        //
        return getTimelapse().getStartDate();
    }
    /**
     *Setter of the activity's start date.
     */
    protected final void setStartDate(final Date aStartDate) {
        getTimelapse().setStartDate(aStartDate);
    }
    /**
     * Accepts the printer visitor that will print the activity.
     */
    abstract void acceptVisitor(Printer printer) throws Exception;
    abstract void acceptVisitor(Report report) throws Exception;
    /**
     * @uml.property  name="timelapse"
     * @uml.associationEnd  inverse="activity:Timelapse"
     */
    private TimeLapse timelapse;

    /**
     * Getter of the property <tt>timelapse</tt>
     * @return  Returns the timelapse.
     * @uml.property  name="timelapse"
     */
    public final TimeLapse getTimelapse() {
        return this.timelapse;
    }
    /**
     * Setter of the property <tt>timelapse</tt>
     * @param timelapse  The timelapse to set.
     * @uml.property  name="timelapse"
     */
    public final void setTimelapse(final TimeLapse aTimelapse) {
        this.timelapse = aTimelapse;
        }
}
