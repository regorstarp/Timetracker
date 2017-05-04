/**
 * The Visitor class represents an operation to be performed on the
 * elements of an object structure without changing the
 * classes on which it operates.
 */


public interface Visitor {
    /**
     * The visitor visits a certain project.
     */
    void visitProject(Project project) throws Exception;
    /**
     * The visitor visits a certain task.
     */
    void visitTask(Task task) throws Exception;
    /**
     * The visitor visits a certain interval.
     */
    void visitInterval(Interval interval) throws Exception;

}
