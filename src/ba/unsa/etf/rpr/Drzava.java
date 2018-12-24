package ba.unsa.etf.rpr;

public class Drzava {
    public Grad getGlavniGrad() {
        return glavniGrad;
    }

    Grad glavniGrad;
    String naziv;
    public void setGlavniGrad(Grad sarajevo) {
        glavniGrad=sarajevo;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String bosna_i_hercegovina) {
        naziv= new String(bosna_i_hercegovina);
    }
    public Drzava(){}
}
