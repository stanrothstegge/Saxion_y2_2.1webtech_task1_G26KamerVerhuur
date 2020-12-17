import Objects.Gebruiker;
import Objects.Huurder;
import Objects.Kamer;
import Objects.Verhuurder;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Servlet genereert een pagina waar alle gebruikers op worden getoond.
 */
@WebServlet("/ShowPersonsServlet")
public class ShowPersonsServlet extends HttpServlet{
//Naam voor cookie
private final String pagecounter = "pageCounter";
//Tijd in sec dat de cookie geldig is
private final int cookieLife = 5000;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response, String currentUser) throws ServletException, IOException {


        if (hasCookie(request)) {
            updateCookie(request, response);
        } else {
            createNewCookie(response);
          }
        //maakt een array en vult deze met alle gebruikers
        ArrayList<Gebruiker> gebruikers = (ArrayList<Gebruiker>) getServletContext().getAttribute("Gebruikers");
        //arrays voor huurders en verhuurders
        ArrayList<Gebruiker> verhuurders = new ArrayList<>();
        ArrayList<Gebruiker> huurders = new ArrayList<>();

        //kijkt of de gebruiker een huurder of verhuurder is en plaatst de gebruikers in de vooraf aangemaakte arrays
        for (int i = 0; i < gebruikers.size(); i++) {
            if(gebruikers.get(i) instanceof Verhuurder){
                verhuurders.add(gebruikers.get(i));
            }else if(gebruikers.get(i) instanceof Huurder){
                huurders.add(gebruikers.get(i));
            }
        }

        //print een lijst met gebruikers, geeft de arrays en de waarde "currentUser" mee
        printTable(response, request, verhuurders, huurders, currentUser);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //maak een session aan en haal de currentUser op. geef deze door aan de doPost() methode
        HttpSession session =request.getSession(false);
        String currentUser = (String)session.getAttribute("currentUser");
        //om de cookies ook echt per gebruikersnaam bij te houden, werkte niet want de pagecounter is final
//        this.pagecounter = pagecounter + currentUser;
        doPost(request, response, currentUser);
    }

    /**
     * genereert een tabel met alle gebruikers, eerst de verhuurders en daarna de huurders.
     * ook wordt er bijgehouden hoeveel gebruikers er zijn en wordt de naam van de huidige gebruiker getoond
     *
     */
    private void printTable (HttpServletResponse response, HttpServletRequest request, ArrayList<Gebruiker> verhuurders, ArrayList<Gebruiker> huurders, String currentUser) throws IOException {
        //todo cookies gebruiken om bij te houden hoevaak de huidige gebruiker hier heeft ingelogd
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head>");
        styleTable(out);
        out.println("</head>");
        out.println("<h1>Zoekresultaten</h1>");
        out.println("<p> Hoi " + currentUser + "</p>");
        out.println("<p> er zijn " + (verhuurders.size() + huurders.size()) + " gebruikers gevonden" + "</p>");
        out.println("<p> U heeft deze pagina " + getVisits(request) + " keer bezocht" + "</p>");
        out.println("<a href=" + "/logout.html" + ">uitloggen</a>");
        out.println("<table style='width:100%'>");
        //print table head
        out.println("<tr>");
        out.println("<th>volledige naam</th>");
        out.println("<th>gebruikersnaam</th>");
        out.println("<th>rol</th>");
        out.println("</tr>");
        //print rows
        for (Gebruiker gebruiker : verhuurders){
            printTableRow(out, gebruiker.getFirstName() + " " + gebruiker.getLastName(), gebruiker.getUserName(), "Verhuurder");
        }
        for (Gebruiker gebruiker : huurders){
            printTableRow(out, gebruiker.getFirstName() + " " + gebruiker.getLastName(), gebruiker.getUserName(), "Huurder");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }


    private void printTableRow(PrintWriter i, String volledigeNaam, String gebruikersNaam, String rol){
        i.println("<tr>");
        i.println("<td>" + volledigeNaam + "</td>");
        i.println("<td>" + gebruikersNaam + "</td>");
        i.println("<td>" + rol + "</td>");
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


    private boolean hasCookie(HttpServletRequest request){
        //Returns an array containing all of the Cookie objects the client
        Cookie[] cookies  = request.getCookies();
        if(cookies.length == 0){
            return false;
        } else {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                if(cookieName.equals(pagecounter) && (Integer.parseInt(cookieValue) > 0)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Create a new cookie object to count the number of visits.
     * @param response
     * @return
     */
    private void createNewCookie(HttpServletResponse response){
        Cookie personCounter = new Cookie(pagecounter, "1");
        personCounter.setMaxAge(cookieLife);
        response.addCookie(personCounter);
    }

    private void updateCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookieById(request, pagecounter);
        //parse de waarde van de cookie
        int value = Integer.parseInt(cookie.getValue());
        value++;
        //update de cookie
        Cookie personCounter = new Cookie(pagecounter, String.valueOf(value));
        personCounter.setPath("/");
        personCounter.setMaxAge(cookieLife);
        response.addCookie(personCounter);
    }

    private int getVisits(HttpServletRequest request) {
        Cookie ck = getCookieById(request, pagecounter);
        return Integer.parseInt(ck.getValue());

    }

    private Cookie getCookieById(HttpServletRequest request,String id){
        //Haal alle cookies van de client op.
        Cookie[] cookies = request.getCookies();
        // Kijk of er een cookie bestaat met ons id.
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            String cookieName = cookie.getName();
            if (cookieName.equals(id))
                return cookie;
        }
        return null;
    }
}
