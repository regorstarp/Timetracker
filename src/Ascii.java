

/**
 * Writes the given elements in txt format into a txt file.
 */


public class Ascii extends Format {
    /**
     * Creates a new ascii Format and
     * sets the name of the txt file.
     */
    public Ascii() {
        setExtension(".txt");
        checkInvariants();
    }
    /**
     * Check that the class invariants are respected
     */
    private void checkInvariants() {
        assert (getExtension() != null) : "null extension";
    }
    /**
     * Visits a given title and writes it to a txt file.
     */
    @Override
    final void visitTitle(final Title title) throws Exception {
        if (title == null) {
            throw new IllegalArgumentException(
                    "title null in Ascii visitTitle");
        }
        //pre
        assert (getOut() != null) : "null print writer";
        assert (title != null) : "null title";
        assert (title.getTitle() != null) : "null title content";
        getOut().print(title.getTitle());
        checkInvariants();
    }
    /**
     * Visits a given separator and writes it to a txt file.
     */
    @Override
    final void visitSeparator(final Separator separator) throws Exception {
        if (separator == null) {
            throw new IllegalArgumentException(
                    "separator null in ascii visitSeparator");
        }
        //pre
        assert (getOut() != null) : "null print writer";
        assert (separator != null) : "null separator";
        assert (separator.getSeparator() != null) : "null separator content";
        this.getOut().println(separator.getSeparator());
        checkInvariants();
    }
    /**
     * Visits a given table and writes it to a txt file.
     */
    @Override
    final void visitTable(final Taula table) throws Exception {
        if (table == null) {
            throw new IllegalArgumentException(
                    "table null in ascii visitTable");
        }
        //pre
        assert (getOut() != null) : "null print writer";
        assert (table != null) : "null table";
        assert (table.getTaula() != null) : "null table content";
        assert (!table.getTaula().isEmpty()) : "empty table";
        assert (table.getNfiles() >= 0) : "null number of rows";
        assert (table.getNcolumnes() >= 0) : "null number of columns";
        for (int fila = 1; fila <= table.getNfiles(); fila++) {
            for (int col = 1; col <= table.getNcolumnes(); col++) {
                this.getOut().print(table.getPosicio(fila, col));
            }
        }
        checkInvariants();
    }
    /**
     * Visits a given text and writes it to a txt file.
     */
    @Override
    final void visitText(final Text text) throws Exception {
        if (text == null) {
            throw new IllegalArgumentException(
                    "text null in ascii visitText");
        }
        //pre
        assert (getOut() != null) : "null print writer";
        assert (text != null) : "null text";
        assert (text.getText() != null) : "null text content";
        this.getOut().print(text.getText());
        checkInvariants();
    }


    /**
     * Finishes writing the txt report by
     * closing the file.
     */
    @Override
    final void writeReport() {
        //pre
        assert (getOut() != null) : "null print writer";
        this.getOut().close();
        checkInvariants();
    }
    /**
     * Visits a given empty line and writes it to a txt file.
     */
    @Override
    final void visitEmptyLine(final EmptyLine emptyLine) throws Exception {
        if (emptyLine == null) {
            throw new IllegalArgumentException(
                    "emptyLine null in ascii visitemptyLine");
        }
        //pre
        assert (getOut() != null) : "null print writer";
        assert (emptyLine != null) : "null emptyLine";
        assert (emptyLine.getEmptyLine() != null) : "null emptyLine content";
        this.getOut().print(emptyLine.getEmptyLine());
        checkInvariants();
    }

}
