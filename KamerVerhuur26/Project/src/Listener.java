import Objects.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
public class Listener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Load dummy data
        RoomRentalModel model = new RoomRentalModel();
        //initiate servlet context
        ServletContext sc = sce.getServletContext();
        //
        sc.setAttribute("Gebruikers", model.getGebruikers());
        sc.setAttribute("Kamers", model.getKamers());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
