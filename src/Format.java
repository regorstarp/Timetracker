import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Responsible for writing the given element in the client's requested
 * format
 * It is a visitor of elements.
 * @author rogerprats
 *
 */
public abstract class Format {
    /**
     * Visits a given title and writes it to a file
     * using the requested format.
     */
    abstract void visitTitle(final Title title) throws Exception;
    /**
     * Visits a given separator and writes it to a file
     * using the requested format.
     */
    abstract void visitSeparator(final Separator separator) throws Exception;
    /**
     * Visits a given table and writes it to a file
     * using the requested format.
     */
    abstract void visitTable(final Taula table) throws Exception;
    /**
     * Visits a given text and writes it to a file
     * using the requested format.
     */
    abstract void visitText(final Text text) throws Exception;
    /**
     * Visits a given empty line and writes it to a file
     * using the requested format.
     */
    abstract void visitEmptyLine(EmptyLine emptyLine) throws Exception;
    /**
     * Writes the report.
     */
    abstract void writeReport();
    /**
     * Gets the name of the file where the report will be saved.
     */
    public final String getFileName() {
        return fileName;
    }
    /**
     * Sets the name of the file where the report will be saved
     */
    public final void setFileName(final String aFileName) {
        this.fileName = aFileName + getExtension();
        try {
            this.setOut(new PrintWriter(
                    new FileOutputStream(getFileName()), false));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Gets the PrintWritter.
     */
    public final PrintWriter getOut() {
        return out;
    }
    /**
     * Sets the PrintWritter.
     * @param out the out to set
     */
    public final void setOut(final PrintWriter aOut) {
        this.out = aOut;
    }
    /**
     * @return the extension
     */
    public final String getExtension() {
        return extension;
    }
    /**
     * @param extension the extension to set
     */
    public final void setExtension(final String aExtension) {
        this.extension = aExtension;
    }
    /**
     * Name of the file where the report will be saved.
     * @uml.property  name="fileName"
     */
    private String fileName;
    /**
     * PrintWriter object used to save elements to a file.
     * @uml.property  name="out"
     */
    private PrintWriter out;
    /**
     * Extension of the file.
     * @uml.property  name="extension"
     */
    private String extension;
}
