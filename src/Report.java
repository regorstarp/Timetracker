import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Responsible for displaying information about the timetracker's
 * projects, subprojects, tasks and intervals as requested by the client
 *  Needs to implement visitor to be able to go over every activity and interval
 *  of the timetracker and generate elements that contain information
 *  about each of them.
 */


public abstract class Report implements Visitor {
    /**
     * Separator object.
     * @uml.property  name="separator"
     */
    private Separator separator;
    /**
     * Empty line object.
     * @uml.property  name="emptyLine"
     */
    private EmptyLine emptyLine;
    /**
     * Number 3 constant.
     */
    public static final int THREE = 3;
    /**
     * Number 4 constant.
     */
    public static final int FOUR = 4;
    /**
     * Number 5 constant.
     */
    public static final int FIVE = 5;
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
     * List of projects of this report.
     * @uml.property  name="projects"
     */
    private List<Activity> projects;
    /**
     * @return the projectTable
     */
    public final Taula getProjectTable() {
        return projectTable;
    }
    /**
     * @param projectTable the projectTable to set
     */
    public final void setProjectTable(final Taula aProjectTable) {
        this.projectTable = aProjectTable;
    }
    /**
     * @return the timeLapseTable
     */
    public final Taula getTimeLapseTable() {
        return timeLapseTable;
    }
    /**
     * @param timeLapseTable the timeLapseTable to set
     */
    public final void setTimeLapseTable(final Taula aTimeLapseTable) {
        this.timeLapseTable = aTimeLapseTable;
    }
    /**
     * @return the intervalTable
     */
    public final Taula getIntervalTable() {
        return intervalTable;
    }
    /**
     * @param intervalTable the intervalTable to set
     */
    public final void setIntervalTable(final Taula aIntervalTable) {
        this.intervalTable = aIntervalTable;
    }
    /**
     * @return the taskTable
     */
    public final Taula getTaskTable() {
        return taskTable;
    }
    /**
     * @param taskTable the taskTable to set
     */
    public final void setTaskTable(final Taula aTaskTable) {
        this.taskTable = aTaskTable;
    }
    /**
     * @return the subProjectTable
     */
    public final Taula getSubProjectTable() {
        return subProjectTable;
    }
    /**
     * @param subProjectTable the subProjectTable to set
     */
    public final void setSubProjectTable(final Taula aSubProjectTable) {
        this.subProjectTable = aSubProjectTable;
    }
    /**
     * Table to display the timelapse.
     * @uml.property  name="timeLapseTable"
     */
    private Taula timeLapseTable;
    /**
     * Table to display the root projects.
     * @uml.property  name="projectTable"
     */
    private Taula projectTable;
    /**
     * Table to display the intervals.
     * @uml.property  name="intervalTable"
     */
    private Taula intervalTable;
    /**
     * Table to display the tasks.
     * @uml.property  name="TaskTable"
     */
    private Taula taskTable;
    /**
     * Table to display the sub projects.
     * @uml.property  name="subProjectTable"
     */
    private Taula subProjectTable;
    /**
     * Creates a new report with a given timelapse, format and list
     * of projects,.
     */
    public Report(final TimeLapse timelapse, final Format aFormat,
            final List<Activity> projectsList)
            throws Exception {
        if (timelapse == null) {
            throw new IllegalArgumentException(
                    "null timelapse in Report constructor");
        }
        if (aFormat == null) {
            throw new IllegalArgumentException(
                    "null aFormat in Report constructor");
        }
        if (projectsList == null) {
            throw new IllegalArgumentException(
                    "null projects in Report constructor");
        }
        //pre
        assert (!projectsList.isEmpty()) : "projects list empty";
        setTimeLapse(timelapse);
        setFormat(aFormat);
        setProjects(projectsList);
        this.separator = new Separator();
        this.emptyLine = new EmptyLine();
        checkInvariants();
    }
    /**
     * Setter of the list of projects of this report.
     */
    private void setProjects(final List<Activity> projectsList) {
        this.projects = projectsList;
    }
    /**
     * Checks that the class invariants are respected.
     */
    private void checkInvariants() {
        assert (this.separator != null) : "null separator";
        assert (this.emptyLine != null) : "null emptyLine";
    }
    /**
     * Visits all the elements of the activities and intervals tree
     * and generates an element for each.
     */
    public final void visitTree()
            throws Exception {
        if (getProjects() == null) {
            throw new IllegalArgumentException(
                    "null projects in Report createReport");
        }
        //pre
        assert (!getProjects().isEmpty()) : "projects list empty";
        for (int i = 0; i < getProjects().size(); i++) {
            if (intersects(getProjects().get(i).getTimelapse())) {
                getProjects().get(i).acceptVisitor(this);
            }
        }
        checkInvariants();
    }
    /**
     * Getter of the list of projects that define this report
     */
    private List<Activity> getProjects() {
        return this.projects;
    }
    /**
     * @return the element
     */
    public final List<Element> getElements() {
        return element;
    }

    /**
     * @uml.property  name="element"
     * @uml.associationEnd  multiplicity="(1 -1)"
     * ordering="true" inverse="report:Element"
     * @uml.association  name="elements"
     */
    private List<Element> element = new ArrayList<Element>();
    /**
     * @uml.property  name="format"
     * @uml.associationEnd  multiplicity="(0 -1)"
     * ordering="true" inverse="report:Format"
     * @uml.association  name="format"
     */
    private Format format;
    /**
     * Getter of the property <tt>format</tt>
     * @return  Returns the format.
     * @uml.property  name="format"
     */
    public final Format getFormat() {
        return this.format;
    }
    /**
     * Setter of the property <tt>format</tt>
     * @param format  The format to set.
     * @uml.property  name="format"
     */
    public final void setFormat(final Format aFormat) {
        this.format = aFormat;
    }
    /**
     * Timelapse of the report
     * @uml.property  name="timeLapse"
     * @uml.associationEnd  inverse="report:TimeLapse"
     */
    private TimeLapse timeLapse;
    /**
     * Getter of the property <tt>timeLapse</tt>
     * @return  Returns the timeLapse.
     * @uml.property  name="timeLapse"
     */
    public final TimeLapse getTimeLapse() {
        return this.timeLapse;
    }
    /**
     * Setter of the property <tt>timeLapse</tt>
     * @param timeLapse  The timeLapse to set.
     * @uml.property  name="timeLapse"
     */
    public final void setTimeLapse(final TimeLapse aTimeLapse) {
        this.timeLapse = aTimeLapse;
    }
    /**
     *Converts a long  from milliseconds to a string representing
     *the time in hh:mm:ss format, so it's more human readable.
     */
    protected final String milliSecondsToString(final long milliSeconds) {
        //pre
        assert (milliSeconds >= 0) : "negative milliseconds";
        int seconds = (int) (milliSeconds / ONE_THOUSAND);
        int hours = (int) seconds / SECONDS_IN_AN_HOUR;
        int minutes = (int) (seconds % SECONDS_IN_AN_HOUR) / MINUTES_IN_AN_HOUR;
        seconds = (int) seconds % SECONDS_IN_A_MINUTE;
        checkInvariants();
        return String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);
    }
    /**
     * Visits all the elements of the report and uses the format
     * defined to write them to a file
     */
    public final void writeReport() throws Exception {
        for (int i = 0; i < element.size(); i++) {
            element.get(i).acceptVisitor(this.format);
        }
        this.format.writeReport();
        checkInvariants();
    }
    /**
     * Checks if the given timelapse intersects with the report's timelapse
     */
    public final boolean intersects(final TimeLapse timelapse) {
        return this.timeLapse.intersect(timeLapse);
    }
    /**
     * Creates the table where the timelapse will be displayed.
     */
    public final void createTimelapseTable() throws Exception {
        addEmptyLine();
        this.element.add(new Title("timelapse\n", THREE, false));
        addEmptyLine();
        this.timeLapseTable = new Taula(FOUR, 2, true, true);
        this.timeLapseTable.setPosicio(1, 1, "      ");
        this.timeLapseTable.setPosicio(1, 2, "Date\n");
        this.timeLapseTable.setPosicio(2, 1, "From  ");
        this.timeLapseTable.setPosicio(2, 2,
                this.timeLapse.getStartDate().toString() + "\n");
        this.timeLapseTable.setPosicio(THREE, 1, "Until ");
        this.timeLapseTable.setPosicio(THREE, 2,
                this.timeLapse.getEndDate().toString() + "\n");
        this.timeLapseTable.setPosicio(FOUR, 1, "Report generation date ");
        this.timeLapseTable.setPosicio(FOUR, 2,
                new Date().toString());
        addTable(this.timeLapseTable);
        checkInvariants();
    }
    /**
     * Creates the table where the root projects will be displayed.
     */
    public final void createRootProjectsTable() {
        this.projectTable = new Taula(1, FOUR, true, false);
        this.projectTable.setPosicio(1, 1, "Project    ");
        this.projectTable.setPosicio(1, 2, "Start date                   ");
        this.projectTable.setPosicio(1, THREE, "End date                     ");
        this.projectTable.setPosicio(1, FOUR, "Duration         " + "\n");
        checkInvariants();
    }
    /**
     * Creates the table where the sub projects will be displayed.
     */
    public final void createSubProjectsTable() {
        this.subProjectTable = new Taula(1, FOUR, true, false);
        this.subProjectTable.setPosicio(1, 1, "Project    ");
        this.subProjectTable.setPosicio(1, 2, "Start date                   ");
        this.subProjectTable.setPosicio(1, THREE,
                "End date                     ");
        this.subProjectTable.setPosicio(1, FOUR, "Duration" + "\n");
        checkInvariants();
    }
    /**
     * Creates the table where the tasks will be displayed.
     */
    public final void createTasksTable() {
        this.taskTable = new Taula(1, FOUR, true, false);
        this.taskTable.setPosicio(1, 1, "Task    ");
        this.taskTable.setPosicio(1, 2, "Start date                   ");
        this.taskTable.setPosicio(1, THREE, "End date                     ");
        this.taskTable.setPosicio(1, FOUR, "Duration" + "\n");
        checkInvariants();
    }
    /**
     * Creates the table where the intervals will be displayed.
     */
    public final void createIntervalsTable() {
        this.intervalTable = new Taula(1, FIVE, true, false);
        this.intervalTable.setPosicio(1, 1, "Task    ");
        this.intervalTable.setPosicio(1, 2, "Interval     ");
        this.intervalTable.setPosicio(1, THREE,
                "Start date                   ");
        this.intervalTable.setPosicio(1, FOUR, "End date                     ");
        this.intervalTable.setPosicio(1, FIVE, "Duration" + "\n");
        checkInvariants();
    }
    /**
     * Adds a new element to the list of elements of this report.
     */
    public final void addElementToReport(final Element newElement)
            throws Exception {
        if (newElement == null) {
            throw new IllegalArgumentException(
                    "newElement null in Report addElmentToReport");
        }
        //to check in post
        final int numElements = this.element.size();
        this.element.add(newElement);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
    }
    /**
     * Adds a new title for the report.
     */
    public final void addTitle(final Title title) throws Exception {
        if (title == null) {
            throw new IllegalArgumentException(
                    "title null in Report addElmentToReport");
        }
        //to check in post
        final int numElements = this.element.size();
        addElementToReport(title);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
    }
    /**
     * Adds a new text to the report.
     */
    public final void addText(final Text text) throws Exception {
        if (text == null) {
            throw new IllegalArgumentException(
                    "text null in Report addElmentToReport");
        }
      //to check in post
        final int numElements = this.element.size();
        addElementToReport(text);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
    }
    /**
     * Adds a new separator to the report.
     */
    public final void addSeparator() throws Exception {
        //to check in post
        final int numElements = this.element.size();
        addElementToReport(separator);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
    }
    /**
     * Adds a new empty line to the report.
     */
    public final void addEmptyLine() throws Exception {
      //to check in post
        final int numElements = this.element.size();
        addElementToReport(emptyLine);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
    }
    /**
     * Adds a new table to the report.
     */
    public final void addTable(final Taula table) throws Exception {
        if (table == null) {
            throw new IllegalArgumentException(
                    "table null in Report addElmentToReport");
            }
      //to check in post
        final int numElements = this.element.size();
        addElementToReport(table);
        //post
        assert (this.element.size() == numElements + 1)
        : "problem adding element";
        checkInvariants();
        }
}
