import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String logoutButton = request.getParameter("logout");

        //enkel bedoeld om uit te loggen. de  sessie wordt ongeldig verklaard
        if ("logout".equals(logoutButton)) {
            HttpSession session=request.getSession();
            session.invalidate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
