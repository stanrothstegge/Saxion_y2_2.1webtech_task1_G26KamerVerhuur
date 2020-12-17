import Objects.Kamer;
import Objects.RoomRentalModel;
import Objects.Verhuurder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Servlet slaat een nieuwe kamer op in het systeem.
 */
@WebServlet("/SubmitRoomServlet")
public class SubmitRoomServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Haal de waarden op uit de html file
        String tempHuur = request.getParameter("addHuur");
        String tempOppervlak = request.getParameter("addOppervlakte");
        String plaats = request.getParameter("addPlaats");
        //de waarden mogen niet leeg zijn
        if(!tempHuur.isEmpty() && !tempOppervlak.isEmpty() && !plaats.isEmpty()){
                //de int velden moeten ook echt integers zijn
            try{
                int huur = Integer.parseInt(tempHuur);
                int oppervlak = Integer.parseInt(tempOppervlak);
                Verhuurder gebruiker1 = new Verhuurder("firstname1", "lastname1", "username1", "password");
                Kamer tmp = new Kamer(huur, oppervlak, plaats, gebruiker1);
                //als alles juist is word de nieuwe kamer toegevoegd
                ((ArrayList<Kamer>) getServletContext().getAttribute("Kamers")).add(tmp);
                //ten aller tijde wordt de gebruiker naar het drukker op de submitknop teruggestuurd naar het kameroverzicht
                response.sendRedirect("/ShowRoomsServlet");
            }catch (NumberFormatException nfe){
                response.sendRedirect("/ShowRoomsServlet");
            }
        }else{
            response.sendRedirect("/ShowRoomsServlet");
        }

    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
