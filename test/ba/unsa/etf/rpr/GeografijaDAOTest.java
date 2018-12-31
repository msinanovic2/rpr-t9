package ba.unsa.etf.rpr;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GeografijaDAOTest {

    @Test
    void regenerateFile() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
        ArrayList<Grad> gradovi = dao.gradovi();
        assertEquals("London", gradovi.get(0).getIme());
        assertEquals("Francuska", gradovi.get(1).getDrzava().getNaziv());
    }

    @Test
    void glavniGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad nepoznat = dao.glavniGrad("Bosna i Hercegovina");
        assertNull(nepoznat);
        Grad bech = dao.glavniGrad("Austrija");
        assertEquals("Beč", bech.getIme());
    }

    @Test
    void obrisiDrzavu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
        // Nepostojeća država, neće se desiti ništa
        dao.obrisiDrzavu("Kina");
        ArrayList<Grad> gradovi = dao.gradovi();
        assertEquals("Pariz", gradovi.get(1).getIme());
        assertEquals("Austrija", gradovi.get(2).getDrzava().getNaziv());
    }

    @Test
    void obrisiDrzavu2() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();

        // Nema gradova Beč i Graz koji su iz austrije
        dao.obrisiDrzavu("Austrija");

        ArrayList<Grad> gradovi = dao.gradovi();
        assertEquals(3, gradovi.size());
        assertEquals("London", gradovi.get(0).getIme());
        assertEquals("Pariz", gradovi.get(1).getIme());
        assertEquals("Manchester", gradovi.get(2).getIme());
    }

    @Test
    void dodajGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Drzava francuska = dao.nadjiDrzavu("Francuska");
        Grad grad = new Grad();
        grad.setIme("Marseille");
        grad.setBrojStanovnika(869815);
        grad.setDrzava(francuska);
        dao.dodajGrad(grad);

        // Marsej je veći od Manchestera i Graza, ali manji od Pariza, Londona i Beča
        ArrayList<Grad> gradovi = dao.gradovi();
        assertEquals("Marseille", gradovi.get(3).getIme());
    }

    @Test
    void dodajDrzavu() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        Grad sarajevo = new Grad();
        sarajevo.setIme("Sarajevo");
        sarajevo.setBrojStanovnika(500000);
        Drzava bih = new Drzava();
        bih.setNaziv("Bosna i Hercegovina");
        bih.setGlavniGrad(sarajevo);
        sarajevo.setDrzava(bih);

        GeografijaDAO dao = GeografijaDAO.getInstance();
        dao.dodajDrzavu(bih);
        dao.dodajGrad(sarajevo);

        // Provjera
        Grad proba = dao.glavniGrad("Bosna i Hercegovina");
        assertEquals("Sarajevo", proba.getIme());
        assertEquals(500000, proba.getBrojStanovnika());
        assertEquals("Bosna i Hercegovina", proba.getDrzava().getNaziv());
    }

    @Test
    void izmijeniGrad() {
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        GeografijaDAO dao = GeografijaDAO.getInstance();
        Grad bech = dao.glavniGrad("Austrija");
        bech.setIme("Vienna");
        dao.izmijeniGrad(bech);

        ArrayList<Grad> gradovi = dao.gradovi();
        assertEquals("Vienna", gradovi.get(2).getIme());
    }
}
