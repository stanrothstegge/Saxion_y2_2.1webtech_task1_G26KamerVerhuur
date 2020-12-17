import Objects.Kamer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/ShowRoomsServlet")
public class ShowRoomsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response, String currentUser) throws ServletException, IOException {

        //haalt een array met alle kamers op
        ArrayList<Kamer> kamers = (ArrayList<Kamer>) getServletContext().getAttribute("Kamers");
        //maak aak een array voor mijn kamers
        ArrayList<Kamer> mijnKamers = new ArrayList<Kamer>();
        //als er helemaal geen kamers zijn heeft u ook geen kamer
        if (kamers.size() == 0) {
            response.getWriter().println("u heeft geen kamers");
        } else {
            for (Kamer kamer : kamers) {
                //als de gebruikersnaam van de verhuurder van de desbetreffende kamer overeenkomt met de gebruikersnaam
                //van de ingelogde gebruiker(opgeslagen in "currentUser"), dan is de kamer van de ingelogde gebruiker.
                if (kamer.getVerhuurder().getUserName().equals(currentUser)) {
                    mijnKamers.add(kamer);
                }
            }
        }
        //print een lijst met mijn kamers
        printTable(response, mijnKamers);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //maak een session aan en haal de currentUser op. geef deze door aan de doPost() methode
        HttpSession session = request.getSession(false);
        String currentUser = (String) session.getAttribute("currentUser");
        doPost(request, response, currentUser);
    }

    /**
     * genereert een tabel met alle kamers die op naam staan van de huidige ingelogde gebruiker.
     *
     */
    private void printTable(HttpServletResponse response, ArrayList<Kamer> mijnKamers) throws IOException {
        //todo knop toevoegen naar addroom.html
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head>");
        styleTable(out);
        out.println("</head>");
        out.println("<h1>Zoekresultaten</h1>");
        out.println("<p> u heeft " + mijnKamers.size() + " kamers " + "</p>");
        out.println("<a href=" + "/AddRoomServlet" + ">voeg een nieuwe kamer toe</a>");
        out.println("<a href=" + "/ShowPersonsServlet" + ">bekijk gebruikers</a>");
        out.println("<p><a href=" + "/logout.html" + ">uitloggen</a></p>");
        out.println("<table style='width:100%'>");
        //print table head
        out.println("<tr>");
        out.println("<th>plaats</th>");
        out.println("<th>oppervlakte</th>");
        out.println("<th>prijs</th>");
        out.println("</tr>");
        //print rows
        for (Kamer kamer : mijnKamers) {
            printTableRow(out, kamer.getHuur(), kamer.getOppervlakte(), kamer.getPlaats());
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }


    private void printTableRow(PrintWriter i, int prijs, int oppervlak, String plaats) {
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
    private void styleTable(PrintWriter out) {
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
