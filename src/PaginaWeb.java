import html.Attribute;
import html.Tag;
import java.util.Collection;
import java.util.Iterator;

public class PaginaWeb {

    /**
     * @uml.property name="paginaWeb"
     */
    private Tag paginaWeb = new Tag("html");
    /**
     * @uml.property name="head"
     */
    private Tag head = new Tag("head");
    /**
     * @uml.property name="body"
     */
    /**
     * Number SIX constant.
     */
    public static final int SIX = 6;
    private Tag body = new Tag("body");
    @SuppressWarnings("unchecked")
    public PaginaWeb() {
        Tag title = new Tag("title");
        title.add("Informe TimeTracker");
        head.add(title);
        paginaWeb.add(head);
        paginaWeb.add(body);
    }
    @SuppressWarnings("unchecked")
    public final void afegeixHeader(final String str, final int mida,
            final boolean centrar) {
        // fa text h1, h2 ... hSIX
    if (mida >= 1 && mida <= SIX) {
        Tag h = new Tag("h" + (new Integer(mida)).toString());
        h.add(str);
        if (centrar) {
            h.addAttribute(new Attribute("style",
                    "text-align: center;"));
        }
        body.add(h);
    }
    }
    @SuppressWarnings("unchecked")
    public final void afegeixTextNormal(final String str) {
        body.add(str);
    }
    @SuppressWarnings("unchecked")
    public final void afegeixSaltDeLinia() {
        body.add(new Tag("br", false));
    }
    @SuppressWarnings("unchecked")
    public final void afegeixTaula(final Collection<?> taula,
            final boolean primeraFilaCapsalera,
            final boolean primeraColumnaCapsalera) {
        // taula es una llista (files) de llistes (columnes),
        //implementat com un arraylist d'arraylists, encara que
        //aqui per mes generalitat hi posem
        // el tipus generic collection
        /*
         * Exemple : taula amb capsalera a la primera fila
         * <table style= "text-align: left; width: 842px;"
         * border="1" cellpadding="2" cellspacing="2">
         *  <tbody>
         *     <tr>
         *  <th style="background-color:
         * rgb(102, 255, 255);">No.</th>
         *  <th style="background-color:
         * rgb(102, 255, 255);">Projecte</th>
         * <th style="background-color:
         * rgb(102, 255, 255);">Data d'inici</th>
         * <th style="background-color:
         * rgb(102, 255, 255);">Data final</th>
         * <th style="background-color:
         * rgb(102, 255, 255);">Temps total</th>
         * </tr>
         * <tr>
         * <td style="background-color:
         * rgb(204, 255, 255);">1</td>
         * <td style="background-color:
         * rgb(204, 255, 255);">P&agrave;gina web personal</td>
         * <td style="background-color:
         * rgb(204, 255, 255);">15/11/200SIX, 19:00h</td>
         * <td style="background-color:
         * rgb(204, 255, 255);">25/11/200SIX, 20:00h</td>
         * <td style="background-color:
         * rgb(204, 255, 255);">25h 45m 0s</td>
         * </tr>
         * </tbody>
         * </table>
         */
        Tag t = new Tag("table");
        t.addAttribute(new Attribute("style", "text-align: left;"
        + "width: 842px;"));
        t.addAttribute(new Attribute("border", "1"));
        t.addAttribute(new Attribute("cellpadding", "2"));
        t.addAttribute(new Attribute("cellspacing", "2"));
        Tag tbody = new Tag("tbody");
        // les cel.les de capsalera tenen fons en blau fosc
        Attribute estilTh = new Attribute("style",
                "background-color: rgb(102, 255, 255);");
        // les cel.les de dades, fons en blau clar
        Attribute estilTd = new Attribute("style",
                "background-color: rgb(204, 255, 255);");
        Iterator<?> itFiles = taula.iterator();
        Iterator<?> itColumnes = null;
        boolean primeraFila = true;
        while (itFiles.hasNext()) {
            Tag tr = new Tag("tr"); // cada fila de la taula
        itColumnes = ((Collection<?>)
                itFiles.next()).iterator();
        boolean primeraColumna = true;
        while (itColumnes.hasNext()) {
            if ((primeraFila && primeraFilaCapsalera)
                    || (primeraColumna
                            && primeraColumnaCapsalera)) {
                // th en comptes de td
                Tag th = new Tag("th");
                th.addAttribute(estilTh);
                th.add(itColumnes.next().toString());
                tr.add(th);
            } else {
                Tag td = new Tag("td");
                td.addAttribute(estilTd);
                td.add(itColumnes.next().toString());
                tr.add(td);
                }
            primeraColumna = false;
            }
            primeraFila = false;
            tbody.add(tr);
        }
        t.add(tbody);
        body.add(t);
    }
    @SuppressWarnings("unchecked")
    public final void afegeixLiniaSeparacio() {
        Tag hr = new Tag("hr");
        hr.addAttribute(new Attribute("style", "width: 100%;"
                + "height: 2px;"));
        // <hr style="width: 100%; height: 2px;">
        body.add(hr);
        }
    public final void escriuPagina() {
        System.out.println(paginaWeb);
    }
    public final Tag getPaginaWeb() {
        return this.paginaWeb;
    }
    }
