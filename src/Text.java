/**
 * Represents a string of text
 */


public class Text extends Element {
    /**
     * String of text.
     * @uml.property  name="text"
     */
    private String text;
    /**
     * Creates a new text element with a given string.
     */
    public Text(final String string) {
        if (string == null) {
            throw new IllegalArgumentException(
                    "null string in text constructor");
        }
        setText(string);
        checkInvariants();
    }
    /**
     * Checks that all the class invariants are always respected
     */
    private void checkInvariants() {
        assert (getText() != null) : "text is null";
    }
    /**
     * Accepts a format visitor that will write the text
     * element content to a file in the given format
     */
    @Override
    final void acceptVisitor(final Format format) throws Exception {
        //to check in post
        String preText = getText();
        if (format == null) {
            throw new IllegalArgumentException(
                    "null format in text acceptVisitor(format)");
        }
        format.visitText(this);
        //post
        assert (preText == getText()) : "text string changed";
        checkInvariants();
    }
    /**
     * Getter of the string of this text element
     */
    public final String getText() {
        return this.text;
    }
    /**
     * Setter of the string of this text element
     */
    public final void setText(final String aText) {
        this.text = aText;
        }

}
