package Objects;


/**
 * Deze class wordt gebruikt om een kamer object op te slaan binnen het systeem.
 * In dit object worden onder andere opgeslagen:
 *  - de oppervlakte in m2
 *  - de huur
 *  - de plaats waar de kamer in gevestigd is.
 *  - de verhuurder die de kamer wil verhuren.
 */
public class Kamer {
    private int oppervlakte;
    private int huur;
    private String plaats;
    private Verhuurder verhuurder;

    public Kamer(int oppervlakte, int huur, String plaats, Verhuurder verhuurder) {
        this.oppervlakte = oppervlakte;
        this.huur = huur;
        this.plaats = plaats;
        this.verhuurder = verhuurder;
    }

    public int getOppervlakte() {
        return oppervlakte;
    }

    public int getHuur() {
        return huur;
    }

    public String getPlaats() {
        return plaats;
    }

    public Verhuurder getVerhuurder() {
        return verhuurder;
    }
}
