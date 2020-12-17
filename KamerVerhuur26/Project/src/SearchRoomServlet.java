import Objects.Gebruiker;
import Objects.Huurder;
import Objects.Kamer;
import Objects.Verhuurder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/SearchRoomServlet")
public class SearchRoomServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Haal de oppervlakte en huur op
        int oppervlakte = Integer.parseInt(request.getParameter("oppervlakte"));
        int huur = Integer.parseInt(request.getParameter("huur"));
        //Haal de plaatsnaam op
        String plaats = request.getParameter("plaats");

        //Haal de opgeslagen kamers op
        ArrayList<Kamer> kamers = (ArrayList<Kamer>) getServletContext().getAttribute("Kamers");
        //Hier worden de 'uitgezochte' kamers bijgehouden.
        ArrayList<Kamer> beschikbareKamers = new ArrayList<Kamer>();
        if (kamers.size() == 0) {
            response.getWriter().println("er zijn geen kamers beschikbaar");
        } else {
            System.out.println(kamers.size());
            for (Kamer kamer : kamers) {
                //controleer of de huur voldoet
                if (kamer.getHuur() <= huur) {
                    //controleer of de oppervlakte voldoet
                    if (kamer.getOppervlakte() >= oppervlakte) {
                        if(plaats.equals("")|| plaats.equals(kamer.getPlaats()))
                        beschikbareKamers.add(kamer);
                    }
                }
            }
        }
      printTable(response, beschikbareKamers);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Print de tabel die de zoekresultaten toont
     * @param response
     * @param beschikbareKamers
     * @throws IOException
     */
    private void printTable (HttpServletResponse response, ArrayList<Kamer> beschikbareKamers) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head>");
        styleTable(out);
        out.println("</head>");
        out.println("<h1>Zoekresultaten</h1>");
        out.println("<p><a href=" + "/logout.html" + ">uitloggen</a></p>");
        out.println("<p> er zijn " + beschikbareKamers.size() + " kamers gevonden" + "</p>");
        out.println("<table style='width:100%'>");
        //print table head
        out.println("<tr>");
        out.println("<th>plaats</th>");
        out.println("<th>oppervlakte</th>");
        out.println("<th>prijs</th>");
        out.println("</tr>");
        //print rows
        for (Kamer kamer : beschikbareKamers){
            printTableRow(out, kamer.getHuur(), kamer.getOppervlakte(), kamer.getPlaats());
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Voeg een kamer toe aan de zoekresulaten
     * @param i printwriter
     * @param prijs prijs van de kamer
     * @param oppervlak m2 van de kamer
     * @param plaats plaats van de woning/kamer
     */
    private void printTableRow(PrintWriter i, int prijs, int oppervlak, String plaats){
        i.println("<tr>");
        i.println("<td>" + plaats + "</td>");
        i.println("<td>" + oppervlak + "</td>");
        i.println("<td>" + prijs + "</td>");
        i.println("</tr>");
    }

    /**
     * Zorg dat de tabel er iets beter uitziet.
     * @param out
     */
    private void styleTable(PrintWriter out){
        out.println("<style>\n" +
                "table, th, td {\n" +
                "    border: 1px solid black;\n" +
                "    border-collapse: collapse;\n" +
                "}\n" +
                "th, td {\n" +
                "    padding: 5px;\n" +
                "    text-align: left;    \n" +
                "}\n" +
                "</style>");
    }
}
