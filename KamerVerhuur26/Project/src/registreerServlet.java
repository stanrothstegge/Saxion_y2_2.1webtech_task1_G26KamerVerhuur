import Objects.Gebruiker;
import Objects.Huurder;
import Objects.Verhuurder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deze servlet handeld het registratieproecs af.
 */
@WebServlet("/registreerServlet")
public class registreerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String firstname = request.getParameter("registerFirstname");
        String lastname = request.getParameter("registerLastname");
        String username = request.getParameter("registerUsername");
        String password = request.getParameter("registerPassword");
        String rol = request.getParameter("registerUserType");

        ArrayList<Gebruiker> gebruikers = (ArrayList<Gebruiker>) getServletContext().getAttribute("Gebruikers");

        if(noneEmpty(firstname, lastname, username, password, rol)){
            if(!bestaatGebruiker(gebruikers, username)){
                //Maak een tijdelijk object aan om een gebruiker in op te slaan.
                Gebruiker nieuweGebruiker = null;
                //Determine if user is "huurder' or 'verhuurder'
                if (rol.equals("Huurder")) {
                    nieuweGebruiker = new Huurder(firstname, lastname, username, password);
                } else if (rol.equals("Verhuurder")) {
                    nieuweGebruiker = new Verhuurder(firstname, lastname, username, password);
                }
                //voeg nieuwe gebruiker toe aan systeem
                ((ArrayList<Gebruiker>) getServletContext().getAttribute("Gebruikers")).add(nieuweGebruiker);
                //forward naar inlogscherm
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
                dispatcher.forward(request, response);
            }else{
                response.getWriter().println("gebruikersnaam bestaat al");
            }
        }else{
            response.sendRedirect("registreer.html");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    /**
     * Verifieer of de gebruikersnaam uniek is.
     * @param gbrs ArrayList met alle gebruikers
     * @param name De te controleren gebruikersnaam.
     * @return
     */
    private boolean bestaatGebruiker(ArrayList<Gebruiker> gbrs, String name) {
        for (Gebruiker gbr : gbrs) {
            if (gbr.getUserName().equals(name)) {
                return true;
            }
        }
        return false;
    }




    /**
     * Deze methode accepteerd een aantal strings, en bekijkt vervolgens of er geen legen tussen zitten.
     * @param lijstMetStrings strings die vergeleken moeten worden
     * @return true als geen van de strings leeg zijn.
     */
    private boolean noneEmpty(String ... lijstMetStrings){
        for(String input : lijstMetStrings){
            if(input.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
