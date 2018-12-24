package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.Drzava;
import ba.unsa.etf.rpr.GeografijaDAO;
import ba.unsa.etf.rpr.Grad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    protected static GeografijaDAO baza;
    public static void main(String[] args) throws SQLException {
        GeografijaDAO aa = GeografijaDAO.getInstance();

    }

    private static void glavniGrad() {
        GeografijaDAO baza = GeografijaDAO.getInstance();
        String drzava;
        System.out.print("Unesite ime drzave: ");
        Scanner tok = new Scanner(System.in);
        drzava = tok.nextLine();
        Grad grad = baza.glavniGrad(drzava);
        System.out.println(grad.getNaziv());
    }

    public static String ispisiGradove() {
        baza = GeografijaDAO.getInstance();
        ArrayList<Grad> gradovi = baza.gradovi();
        StringBuilder res= new StringBuilder();
        for(Grad x : gradovi){
            res.append(x.getNaziv()).append(" (").append(x.getDrzava().getNaziv()).append(") - ").append(x.getBrojStanovnika()).append("\n");
        }
        return res.toString();
    }
}