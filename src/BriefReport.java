import java.util.List;

/**
 * Shows for each root project (the ones that are not childs of other
 * projects, the duration and the startd and end dates of the project.
 * Also it shows the two dates that define the report's timelapse.
 *
 */


public class BriefReport extends Report {
    /**
     * Creates a new briefreport with a given timelapse, format and
     * list of projects using the Report constructor
     * Then adds the title, timelapse table and projects table
     * And proceeds to create the brief report.
     */
    public BriefReport(final TimeLapse timelapse, final Format format,
            final List<Activity> projects) throws Exception {
        super(timelapse, format, projects);
        getFormat().setFileName("Brief Report");
        addTitle(new Title("Brief Report", 1, true));
        addEmptyLine();
        addSeparator();
        createTimelapseTable();
        createRootProjectsTable();
        createReport();
        checkInvariants();
    }
    /**
     * Check that the class invariants are respected
     */
    private void checkInvariants() {
        assert (getElements() != null) : "null element list";
        assert (!getElements().isEmpty()) : "llista elements buida";
        assert (getTimeLapseTable() != null) : "timelapse table null";
        assert (getProjectTable() != null) : "project table null";
    }
    /**
     * Visits a given project and it adds it to the projects table
     * if it intersects with the report's timelapse.
     */
    public final void visitProject(final Project project) throws Exception {
        if (project == null) {
            throw new IllegalArgumentException(
                    "null project in brief Report visit project");
        }
        //pre
        assert (project.getTimelapse() != null) : "null project timelapse";
        assert (getProjectTable() != null) : "null project table";
        assert (project.getDuration() >= 0) : "negative duration";
        assert (getProjectTable().getNfiles() >= 0) : "negative number of rows";
        assert (project.getStartDate() != null) : "null start date";
        assert (project.getEndDate() != null) : "null end date";
        if (intersects(project.getTimelapse()) && project.getDuration() > 0) {
            assert (project.getFather() == null);
            getProjectTable().afegeixFila();
            int fila = getProjectTable().getNfiles();
            getProjectTable().setPosicio(fila, 1,
                    project.getName() + "         ");
            getProjectTable().setPosicio(fila, 2,
                    project.getStartDate().toString()
                    + " ");
            getProjectTable().setPosicio(fila, THREE,
                    project.getEndDate().toString()
                    + " ");
            getProjectTable().setPosicio(fila, FOUR,
                    milliSecondsToString(project.getDuration())
                    + "\n");
            addEmptyLine();
        }
        checkInvariants();
    }
    /**
     * Visits a given task and it adds it to the tasks table
     * if it intersects with the report's timelapse.
     * Not used in brief report
     */
    @Override
    public final void visitTask(final Task task) {
        // TODO Auto-generated method stub
        checkInvariants();
    }
    /**
     * Visits a given interval and it adds it to the intervals table
     * if it intersects with the report's timelapse.
     * Not used in brief report
     */
    @Override
    public final void visitInterval(final Interval interval) {
        // TODO Auto-generated method stub
        checkInvariants();
    }
    /**
     * Visits all the activities and intervals of the tree
     * and creates the report structure by adding the root
     * projects table and title to the list.
     */
    private void createReport() throws Exception {
        //pre
        assert (getElements() != null) : "not null elements list";
        visitTree();
        addSeparator();
        this.getElements().add(new Title("Root Projects\n", THREE, false));
        addSeparator();
        addEmptyLine();
        this.getElements().add(getProjectTable());
        addEmptyLine();
        writeReport();
    }

}
