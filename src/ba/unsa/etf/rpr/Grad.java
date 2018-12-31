package ba.unsa.etf.rpr;

import java.util.Comparator;

public class Grad {
    String ime;
    int brojStanovnika;
    Drzava drzava;
    public Grad(){
        ime ="";
        brojStanovnika=0;
    }
    public class CustomComparator implements Comparator<Grad> {
        @Override
        public int compare(Grad o1, Grad o2) {
            return o1.brojStanovnika-o2.brojStanovnika;
        }
    }
    public String getIme() {
        return ime;
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void setIme(String ime) {
        this.ime = new String(ime);
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
