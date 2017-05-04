/**
 * Represents a empty line of text
 */


public class EmptyLine extends Element {
    /**
     * String of text of an empty line.
     * @uml.property  name="emptyLine"
     */
    private String emptyLine = "\n";
    /**
     * Creates a new separator
     */
    public EmptyLine() {
        checkInvariants();
    }
    /**
     * Checks that all the class invariants are always respected
     */
    private void checkInvariants() {
        assert (getEmptyLine() != null) : "empty line is null";
    }
    /**
     * Accepts a format visitor that will write the
     * element content to a file in the given format.
     */
    @Override
    final void acceptVisitor(final Format format) throws Exception {
        //to check in post
        String preEmptyLine = getEmptyLine();
        if (format == null) {
            throw new IllegalArgumentException(
                    "format null in EmptyLine acceptVisitor");
        }
        format.visitEmptyLine(this);
        //post
        assert (preEmptyLine == getEmptyLine()) : "emptLyne string changed";
        checkInvariants();
    }
    /**
     * Gets the empty line string.
     */
    public final String getEmptyLine() {
        return emptyLine;
    }

}
