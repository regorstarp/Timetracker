import java.util.List;

/**
 * Detailed, adding to the brief report
 * the time spent in the report timelapse, all subprojects
  * and tasks, and also the information of the intervals. It shows,
  * for each project, subproject, task and interval their
  * start and end time and duration considering the timelapse
  * requested in the report.
 */


public class DetailedReport extends Report {
    /**
     * Creates a new detailed report with a given timelapse, format and
     * list of projects using the Report constructor
     * Then adds the title, timelapse, projects, subprojects,
     * tasks and intervals tables.
     * And proceeds to create the detailed report.
     */
    public DetailedReport(final TimeLapse timelapse, final Format format,
        final List<Activity> projects) throws Exception {
        super(timelapse, format, projects);
        getFormat().setFileName("Detailed Report");
        addTitle(new Title("Detailed Report", 1, true));
        addEmptyLine();
        addSeparator();
        createTimelapseTable();
        createRootProjectsTable();
        createSubProjectsTable();
        createTasksTable();
        createIntervalsTable();
        createReport();
        checkInvariants();
    }
    /**
     * Checks that the class invariants are always respected.
     */
    public final void checkInvariants() {
        assert (getElements() != null) : "null element list";
        assert (!getElements().isEmpty()) : "llista elements buida";
        assert (getTimeLapseTable() != null) : "null timelapse table";
        assert (getIntervalTable() != null) : "null interval table";
        assert (getTaskTable() != null) : "null task table";
        assert (getSubProjectTable() != null) : "null sub project table";
        assert (getProjectTable() != null) : "null project table";
    }
    /**
     * Visits a given project, if it has a not null father it calls
     * visitSubProject, if not it adds it to the projects table
     * if it intersects with the report's timelapse.
     */
    @Override
    public final void visitProject(final Project project) throws Exception {
        if (project == null) {
            throw new IllegalArgumentException(
                    "project null in detailed report visitProject");
        }
        //pre
        assert (project.getTimelapse() != null) : "null project timelapse";
        assert (getProjectTable() != null) : "null project table";
        assert (project.getDuration() >= 0) : "negative duration";
        assert (getProjectTable().getNfiles() >= 0) : "negative number of rows";
        assert (project.getStartDate() != null) : "null start date";
        assert (project.getEndDate() != null) : "null end date";
        if (intersects(project.getTimelapse())) {
            if (project.getFather() != null) {
                visitSubProject(project);
            } else {
                assert (project.getFather() == null);
                getProjectTable().afegeixFila();
                int fila = getProjectTable().getNfiles();
                getProjectTable().setPosicio(fila, 1, project.getName()
                        + "         ");
                getProjectTable().setPosicio(fila, 2,
                        project.getStartDate().toString()
                        + " ");
                getProjectTable().setPosicio(fila, THREE,
                        project.getEndDate().toString()
                        + " ");
                getProjectTable().setPosicio(fila, FOUR,
                        milliSecondsToString(project.getDuration())
                        + " \n");
                for (Activity a: project.getChildActivities()) {
                    a.acceptVisitor(this);
                }
            }
            addEmptyLine();
        }
        checkInvariants();
    }
    /**
     * Visits a given task and it adds it to the tasks table
     * if it intersects with the report's timelapse.
     */
    @Override
    public final void visitTask(final Task task) throws Exception {
        if (task == null) {
            throw new IllegalArgumentException(
                    "task null in detailed report visitTask");
        }
        //pre
        assert (task.getTimelapse() != null) : "null project timelapse";
        assert (getTaskTable() != null) : "null project table";
        assert (task.getDuration() >= 0) : "negative duration";
        assert (getProjectTable().getNfiles() >= 0) : "negative number of rows";
        assert (task.getStartDate() != null) : "null start date";
        assert (task.getEndDate() != null) : "null end date";
        //if task intersects with report and the task has been started.
        if (intersects(task.getTimelapse()) && task.getDuration() > 0) {
            getTaskTable().afegeixFila();
            int fila = getTaskTable().getNfiles();
            getTaskTable().setPosicio(fila, 1, task.getName() + "      ");
            getTaskTable().setPosicio(fila, 2,
                    task.getStartDate().toString() + " ");
            getTaskTable().setPosicio(fila, THREE, task.getEndDate().toString()
                    + " ");
            getTaskTable().setPosicio(fila, FOUR, milliSecondsToString(
                    task.getDuration()) + " \n");
            for (Interval i: task.getIntervals()) {
                i.acceptVisitor(this);
            }
        }
        checkInvariants();
    }
    /**
     * Visits a given interval and it adds it to the intervals table
     * if it intersects with the report's timelapse.
     */
    @Override
    public final void visitInterval(final Interval interval) {
        if (interval == null) {
            throw new IllegalArgumentException(
                    "interval null in detailed report visitInterval");
        }
        //pre
        assert (interval.getTimelapse() != null) : "null project timelapse";
        assert (getIntervalTable() != null) : "null project table";
        assert (interval.getDuration() >= 0) : "negative duration";
        assert (getProjectTable().getNfiles() >= 0) : "negative number of rows";
        assert (interval.getStartDate() != null) : "null start date";
        assert (interval.getEndDate() != null) : "null end date";
        if (intersects(interval.getTimelapse())) {
            getIntervalTable().afegeixFila();
            int fila = getIntervalTable().getNfiles();
            getIntervalTable().setPosicio(fila, 1,
                    interval.getFather().getName()
                    + "      ");
            getIntervalTable().setPosicio(fila, 2, "num          ");
            getIntervalTable().setPosicio(fila, THREE,
                    interval.getStartDate().toString()
                    + " ");
            getIntervalTable().setPosicio(fila, FOUR,
                    interval.getEndDate().toString()
                    + " ");
            getIntervalTable().setPosicio(fila, FIVE,
                    milliSecondsToString(interval.getDuration())
                    + " \n");
        }
        //post
        checkInvariants();
    }
    /**
     * Visits all the activities and intervals of the tree
     * and creates the report structure by adding all the
     * titles and tables to the list of elements.
     */
    public final void createReport()
            throws Exception {
        visitTree();
        addSeparator();
        addTitle(new Title("Root Projects\n", THREE, false));
        addSeparator();
        addEmptyLine();
        getElements().add(getProjectTable());
        addEmptyLine();
        addSeparator();
        addTitle(new Title("SubProjects\n", THREE, false));
        addSeparator();
        addEmptyLine();
        addText(new Text("Only the subprojects with one or more tasks inside "
        + "this timelapse are included on this table.\n"));
        addEmptyLine();
        addTable(getSubProjectTable());
        addEmptyLine();
        addSeparator();
        addTitle(new Title("Tasks\n", THREE, false));
        addSeparator();
        addEmptyLine();
        addText(new Text("This table includes tasks and the projects "
        + "they belong to.\n"));
        addEmptyLine();
        addTable(getTaskTable());
        addEmptyLine();
        addSeparator();
        addTitle(new Title("Intervals\n", THREE, false));
        addSeparator();
        addEmptyLine();
        addText(new Text("This table includes start date, end date and duration"
        + " of all the intervals inside this timelapse and "
                + "the task they belong to.\n"));
        addEmptyLine();
        addTable(getIntervalTable());
        addEmptyLine();
        writeReport();
        checkInvariants();
    }
    /**
     * Visits a given sub project and it adds it to the sub projects table
     * if it intersects with the report's timelapse.
     */
    private void visitSubProject(final Project project) throws Exception {
        if (project == null) {
            throw new IllegalArgumentException(
                    "project null in detailed report visitSubProject");
        }
        //pre
        assert (project.getTimelapse() != null) : "null project timelapse";
        assert (getSubProjectTable() != null) : "null project table";
        assert (project.getDuration() >= 0) : "negative duration";
        assert (getProjectTable().getNfiles() >= 0) : "negative number of rows";
        assert (project.getStartDate() != null) : "null start date";
        assert (project.getEndDate() != null) : "null end date";
        if (intersects(project.getTimelapse())) {
            getSubProjectTable().afegeixFila();
            int fila = getSubProjectTable().getNfiles();
            getSubProjectTable().setPosicio(fila, 1, project.getName()
                    + "         ");
            getSubProjectTable().setPosicio(fila, 2,
                    project.getStartDate().toString() + " ");
            getSubProjectTable().setPosicio(fila, THREE,
                    project.getEndDate().toString()
                    + " ");
            getSubProjectTable().setPosicio(fila, FOUR,
                    milliSecondsToString(project.getDuration())
                    + " \n");
            for (Activity a: project.getChildActivities()) {
                a.acceptVisitor(this);
            }
            addEmptyLine();
        }
        checkInvariants();
    }
}
