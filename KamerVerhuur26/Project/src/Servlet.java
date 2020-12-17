import Objects.Gebruiker;
import Objects.Huurder;
import Objects.RoomRentalModel;
import Objects.Verhuurder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Deze sevlet handelt het inlogproces af.
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("inputUsername");
        String password = request.getParameter("inputPassword");

        ArrayList<Gebruiker> gebruikers = (ArrayList<Gebruiker>) getServletContext().getAttribute("Gebruikers");

        boolean bestaandeGebruiker = false;
        //Loop door alle gebruikers
        for (Gebruiker gebruiker : gebruikers){
            //check of de gebruikersnaam & wachtwoord kloppen
            if(gebruiker.getUserName().equals(name) && gebruiker.getPassword().equals(password)) {
                bestaandeGebruiker = true;
                sessionRequest(request, name);
                //Stuur de user naar de juiste pagina, afhankelijk van zijn rol.
                if(gebruiker instanceof Huurder){
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/huurder.html");
                    rd.forward(request, response);
                }else if(gebruiker instanceof Verhuurder){
                    response.sendRedirect("/ShowRoomsServlet");
                }
            }
        }
        //als de gebruiker geen bestaande gebruikersnaam invult dan wordt hij doorverwezen naar de fouteinlog.html pagina
        if(bestaandeGebruiker == false){
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/fouteinlog.html");
            dispatcher.forward(request, response);

            response.getWriter().println("gebruikersnaam of wachtwoord fout");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Maakt een nieuwe sessie aan voor de ingelogde gebruiker
     * @param request
     * @param name
     */
    private void sessionRequest(HttpServletRequest request, String name){
        HttpSession session = request.getSession();
        session.setAttribute("currentUser", name);
    }

}
