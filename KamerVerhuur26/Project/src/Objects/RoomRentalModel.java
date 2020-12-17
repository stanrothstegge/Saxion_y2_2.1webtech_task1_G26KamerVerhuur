package Objects;

import java.util.ArrayList;

/**
 * Deze class representeerd het model dat binnen de servletcontext wordt gebruikt om data op te slaan.
 */
public class RoomRentalModel {
    //lijst om alle gebruikers in op te slaan.
    ArrayList<Gebruiker> gebruikers;
    //Lijst om alle kamers in op te slaan.
    ArrayList<Kamer> kamers;

    public RoomRentalModel() {
        this.gebruikers =  new ArrayList<>();
        this.kamers = new ArrayList<>();
        createSomeUsers();
    }

    public ArrayList<Gebruiker> getGebruikers() {
        return gebruikers;
    }

    public ArrayList<Kamer> getKamers() {
        return kamers;
    }

    /**
     * Maak wat dummy data aan.
     */
    private void createSomeUsers(){
        Verhuurder gebruiker1 = new Verhuurder("firstname1", "lastname1", "username1", "password");
        Huurder gebruiker2 = new Huurder("firstname2", "lastname2", "username2", "password");
        Huurder gebruiker3 = new Huurder("firstname3", "lastname3", "username3", "password");

        this.gebruikers.add(gebruiker1);
        this.gebruikers.add(gebruiker2);
        this.gebruikers.add(gebruiker3);

        Kamer kamer1 = new Kamer(15, 200, "Enschede", gebruiker1);
        Kamer kamer2 = new Kamer(11, 260, "Hengelo", gebruiker1);
        Kamer kamer3 = new Kamer(20, 550, "Enschede", gebruiker1);
        Kamer kamer4 = new Kamer(22, 530, "Zwolle", gebruiker1);
        Kamer kamer5 = new Kamer(22, 500, "Enschede", gebruiker1);

        this.kamers.add(kamer1);
        this.kamers.add(kamer2);
        this.kamers.add(kamer3);
        this.kamers.add(kamer4);
        this.kamers.add(kamer5);
    }


}
