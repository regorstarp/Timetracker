/**
 * Elements contain information of the report
 * which will be written by a given format
 */


public abstract class Element {
    /**
     * Accepts a format visitor that will write the
     * element content to a file in the given format.
     */
    abstract void acceptVisitor(Format format) throws Exception;
}
