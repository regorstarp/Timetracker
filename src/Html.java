

/**
 * Saves the given elements in html format into
 * a web page and
 * writes them into an html file.
 */

public class Html extends Format {
    /**
     * Number SIX constant.
     */
    public static final int SIX = 6;
    /**
     * Creates a new html format by creating a new
     * web page where the report information will
     * be stored and then written into a file.
     */
    public Html() {
        setExtension(".html");
        setPaginaWeb(new PaginaWeb());
        checkInvariants();
    }
    /**
     * Visits a given title and saves it to
     * the web page.
     */
    @Override
    final void visitTitle(final Title title) throws Exception {
        if (title == null) {
            throw new IllegalArgumentException(
                    "title null in Html visitTitle");
        }
        //pre
        assert (getPaginaWeb() != null) : "null web page";
        assert (title.getSize() >= 1 && title.getSize() <= SIX)
        : "bad title size";
        assert (title.isCenter() || !title.isCenter()) : "bad iscenter value";
        this.paginaWeb.afegeixHeader(title.getTitle(),
                title.getSize(), title.isCenter());
        checkInvariants();
    }
    /**
     * Visits a given separator and saves it to
     * the web page.
     */
    @Override
    final void visitSeparator(final Separator separator) throws Exception {
        if (separator == null) {
            throw new IllegalArgumentException(
                    "separator null in Html visitSeparator");
        }
        //pre
        assert (getPaginaWeb() != null) : "null web page";
        this.paginaWeb.afegeixLiniaSeparacio();
        checkInvariants();
    }
    /**
     * Visits a given table and saves it to
     * the web page.
     */
    @Override
    final void visitTable(final Taula table) throws Exception {
        if (table == null) {
            throw new IllegalArgumentException(
                    "table null in Html visitTable");
        }
        //pre
        assert (getPaginaWeb() != null) : "null web page";
        assert (table.getTaula() != null) : "null table";
        assert (table.isFirstRowHeader() || !table.isFirstRowHeader())
        : "is first row header error";
        assert (table.isFirstColHeader() || !table.isFirstColHeader())
        : "is first col header error";
        this.paginaWeb.afegeixTaula(table.getTaula(),
                table.isFirstRowHeader(), table.isFirstColHeader());
        checkInvariants();
    }
    /**
     * Visits a given text and saves it to
     * the web page.
     */
    @Override
    final void visitText(final Text text) throws Exception {
        if (text == null) {
            throw new IllegalArgumentException(
                    "text null in Html visitText");
        }
        //pre
        assert (getPaginaWeb() != null) : "null web page";
        this.paginaWeb.afegeixTextNormal(text.getText());
        this.paginaWeb.afegeixSaltDeLinia();
        checkInvariants();
    }
    /**
     * Creates a new html file with the given filename
     * and then writes the web page in the file.
     */
    @Override
    final void writeReport() {
        //pre
        assert (getFileName() != null) : "null filename";
        assert (getPaginaWeb() != null) : "null web page";
        this.paginaWeb.afegeixSaltDeLinia();
        getOut().print(this.paginaWeb.getPaginaWeb());
        getOut().close();
        checkInvariants();
    }
    /**
     * Checks that all the class invariants are always respected
     */
    private void checkInvariants() {
        assert (getPaginaWeb() != null) : "null web page";
        assert (getExtension() != null) : "null extension";
    }
    /**
     * Visits a given empty line and saves it to
     * the web page.
     */
    @Override
    final void visitEmptyLine(final EmptyLine emptyLine) throws Exception {
        if (emptyLine == null) {
            throw new IllegalArgumentException(
                    "emptyLine null in Html visitEmptyLine");
        }
        //pre
        assert (getPaginaWeb() != null) : "null web page";
        this.paginaWeb.afegeixSaltDeLinia();
        checkInvariants();
    }
    /**
     * Web page of the html report
     * @uml.property  name="paginaWeb"
     * @uml.associationEnd  inverse="html:PaginaWeb"
     */
    private PaginaWeb paginaWeb;
    /**
     * Getter of the property <tt>paginaWeb</tt>
     * @return  Returns the paginaWeb.
     * @uml.property  name="paginaWeb"
     */
    public final PaginaWeb getPaginaWeb() {
        return paginaWeb;
    }
    /**
     * Setter of the property <tt>paginaWeb</tt>
     * @param paginaWeb  The paginaWeb to set.
     * @uml.property  name="paginaWeb"
     */
    public final void setPaginaWeb(final PaginaWeb aPaginaWeb) {
        this.paginaWeb = aPaginaWeb;
    }
    }
