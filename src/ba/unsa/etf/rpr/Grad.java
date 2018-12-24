package ba.unsa.etf.rpr;

public class Grad {
    String naziv;
    int brojStanovnika;
    Drzava drzava;
    public String getNaziv() {
        return naziv;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setNaziv(String ime) {
        naziv= new String(ime);
    }

    public void setBrojStanovnika(int i) {
        brojStanovnika = i;
    }

    public void setDrzava(Drzava francuska) {
        drzava=francuska;
    }

    public int getBrojStanovnika() {
        return brojStanovnika;
    }
}
