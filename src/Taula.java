import java.util.ArrayList;

public class Taula extends Element {
    private boolean firstRowHeader;
    private boolean firstColHeader;
    private int nfiles;

    public final int getNfiles() {
        return nfiles;
    }
    protected final void setNfiles(final int numFiles) {
        this.nfiles = numFiles;
    }
    private int ncolumnes;
    public final int getNcolumnes() {
        return ncolumnes;
    }
    protected final void setNcolumnes(final int numColumnes) {
        this.ncolumnes = numColumnes;
    }
    @SuppressWarnings("rawtypes")
    private ArrayList taula = null;
    @SuppressWarnings("rawtypes")
    public final ArrayList getTaula() {
        return taula;
    }
    @SuppressWarnings({ "rawtypes" })
    public final void setTaula(final ArrayList aTaula) {
        this.taula = aTaula;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Taula(final int numFiles, final int numColumnes,
            final boolean isFirstRowHeader,
            final boolean isFirstColHeader) {
        setNfiles(numFiles);
        setNcolumnes(numColumnes);
        setFirstRowHeader(isFirstRowHeader);
        setFirstColHeader(isFirstColHeader);
        ArrayList t = new ArrayList();
        for (int i = 0; i < numFiles; i++) {
            ArrayList fila = new ArrayList();
            for (int j = 0; j < numColumnes; j++) {
                // fila.add(new String());
                fila.add(null);
            }
            t.add(fila);
        }
        setTaula(t);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void afegeixFila() {
        int numColumnes = getNcolumnes();
        ArrayList fila = new ArrayList();
        for (int j = 0; j < numColumnes; j++) {
            // fila.add(new String());
            fila.add(null);
        }
        getTaula().add(fila);
        setNfiles(getNfiles() + 1);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void afegeixFila(final ArrayList llistaStrings) {
        getTaula().add(llistaStrings);
        setNfiles(getNfiles() + 1);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void setPosicio(final int fila, final int columna,
            final String str) {
        // numerem de 1 ... n i no de 0 ... n-1
        ((ArrayList) getTaula().get(fila - 1)).set(columna - 1, str);
    }
    @SuppressWarnings("rawtypes")
    public final String getPosicio(final int fila, final int columna) {
        return (String) (
                (ArrayList) getTaula().get(fila - 1)).get(columna - 1);
    }
    public final void imprimeix() {
        System.out.println(this.getTaula());
    }
    @Override
    final void acceptVisitor(final Format format) throws Exception {
        if (format == null) {
            throw new IllegalArgumentException(
                    "format null in Taula acceptVisitor");
        }
        format.visitTable(this);
    }
    /**
     * @return the firstRowHeader
     */
    public final boolean isFirstRowHeader() {
        return firstRowHeader;
    }
    /**
     * @param firstRowHeader the firstRowHeader to set
     */
    public final void setFirstRowHeader(final boolean isFirstRowHeader) {
        this.firstRowHeader = isFirstRowHeader;
    }
    /**
     * @return the firstColHeader
     */
    public final boolean isFirstColHeader() {
        return firstColHeader;
    }
    /**
     * @param firstColHeader the firstColHeader to set
     */
        public final void setFirstColHeader(final boolean isFirstColHeader) {
            this.firstColHeader = isFirstColHeader;
        }
    }
