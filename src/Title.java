/**
 * Represents a string title
 */


public class Title extends Element {
    /**
     * Font size of the title.
     * @uml.property  name="separator"
     */
    private int size;
    /**
     * Whether or not the title is centered.
     * @uml.property  name="separator"
     */
    private boolean center;
    /**
     * Number SIX constant.
     */
    public static final int SIX = 6;
    /**
     * Creates a new title element with a given title string, size
     * and if the title needs to be centered.
     */
    public Title(final String aTitle, final int aSize, final boolean isCenter) {
        if (aTitle == null) {
            throw new IllegalArgumentException(
                    "aTitle null in title Title constructor");
        }
        assert (aSize >= 1 && aSize <= SIX) : "aSize incorrect";
        setTitle(aTitle);
        setSize(aSize);
        setCenter(isCenter);
        checkInvariants();
    }
    private void checkInvariants() {
        assert (getTitle() != null) : "title is null";
    }
    /**
     * String of text of the title.
     * @uml.property  name="title"
     */
    private String title;
    /**
     * Accepts a format visitor that will write the
     * element content to a file in the given format.
     */
    @Override
    final void acceptVisitor(final Format format) throws Exception {
        if (format == null) {
            throw new IllegalArgumentException(
                    "format null in Title acceptVisitor");
        }
        //to check in post
        String preTitle = getTitle();
        int preSize = getSize();
        boolean preIsCenter = isCenter();
        format.visitTitle(this);
        //post
        assert (preTitle == getTitle()) : "title string changed";
        assert (preSize == getSize()) : "title size changed";
        assert (preIsCenter == isCenter()) : "title center changed";
        checkInvariants();
    }
    /**
     * Gets the title string.
     */
    public final String getTitle() {
        // TODO Auto-generated method stub
        return this.title;
    }
    /**
     * Sets the title string.
     */
    public final void setTitle(final String aTitle) {
        this.title = aTitle;
    }

    /**
     * Gets if the title needs to be centered.
     */
    public final boolean isCenter() {
        return center;
    }

    /**
     * Sets if the title needs to be centered.
     */
    public final void setCenter(final boolean isCenter) {
        this.center = isCenter;
    }

    /**
     * Gets the size of the title.
     */
    public final int getSize() {
        return size;
    }

    /**
     * Sets the size of the title.
     */
    public final void setSize(final int aSize) {
        this.size = aSize;
    }

}
