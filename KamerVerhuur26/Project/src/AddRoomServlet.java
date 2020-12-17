import Objects.Gebruiker;
import Objects.Kamer;
import Objects.Verhuurder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet("/AddRoomServlet")
/**
 * Deze servlet genereert een html pagina waarmee de gebruiker een nieuwe kamer kan registreren in het systeem.
 */
public class AddRoomServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printTable(response);
    }

    /**
     * Print de tabel die de zoekresultaten toont
     * @param response
     * @throws IOException
     */
    private void printTable (HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<h1>Kamer toevoegen</h1>");
        //
        out.println("<p><a href=" + "/logout.html" + ">uitloggen</a></p>");
        out.println("<form action="+ "'/SubmitRoomServlet'"  + "method='post'>");
        out.println(" Oppervlakte:<br>");
        out.println("<input type=\"text\" name=\"addOppervlakte\"><br>");
        out.println(" Huur:<br>");
        out.println(" <input type=\"text\" name=\"addHuur\"><br>");
        out.println(" plaats:<br>");
        out.println(" <input type=\"text\" name=\"addPlaats\"><br>");
        out.println("<input type=\"submit\" value=\"submit\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }


}
