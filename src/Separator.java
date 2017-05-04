/**
 * Represents a separator string
 */


public class Separator extends Element {
    /**
     * String of text of the separator.
     * @uml.property  name="separator"
     */
    private String separator = "--------------------------"
    + "------------------------------------------------------------------------"
    + "----";
    /**
     * Creates a new separator
     */
    public Separator() {
        checkInvariants();
    }
    /**
     * Accepts a format visitor that will write the
     * element content to a file in the given format.
     */
    @Override
    final void acceptVisitor(final Format format) throws Exception {
        if (format == null) {
            throw new IllegalArgumentException(
                    "format null in Separator acceptVisitor");
        }
        //to check in post
        String preSeparator = getSeparator();
        format.visitSeparator(this);
        //post
        assert (preSeparator == getSeparator()) : "separator string changed";
        checkInvariants();
    }
    /**
     * Checks that all the class invariants are always respected
     */
    private void checkInvariants() {
        assert (getSeparator() != null) : "null separator";
    }
    /**
     * Gets the separator string.
     */
    public final String getSeparator() {
        return separator;
    }
}
