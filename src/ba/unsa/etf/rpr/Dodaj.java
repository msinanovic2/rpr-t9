package ba.unsa.etf.rpr;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Dodaj implements Initializable {
    public TextField graaaad;
    public TextField drzavaa;
    public TextField brojStanovnika;
    public Label drzavaLabela;
    public Label gradLabela;
    public Label stanovnici;
    public Button dodajDr;
    public Button dodajGr;
    public Grad g;
    public Drzava d;
    GeografijaDAO geo;
    public Dodaj(){
        geo = GeografijaDAO.getInstance();
        graaaad = new TextField();
        drzavaa = new TextField();
        brojStanovnika = new TextField();
        drzavaLabela = new Label();
        gradLabela = new Label();
        stanovnici = new Label();
        dodajDr = new Button();
        dodajGr = new Button();
        g = new Grad();
        d = new Drzava();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ubaci(ActionEvent actionEvent) {

         d.setNaziv(drzavaa.getText());
         g.setIme(graaaad.getText());
         g.setBrojStanovnika(Integer.parseInt( brojStanovnika.textProperty().get()));
         g.setDrzava(d);
         geo.dodajGrad(g);
    }
    public void ubaci1(ActionEvent actionEvent) {
        d.setNaziv(drzavaa.textProperty().get());
        d.setGlavniGrad(g);
        g.setIme(graaaad.textProperty().get());
        g.setBrojStanovnika(Integer.parseInt( brojStanovnika.textProperty().get()));
        g.setDrzava(d);
        geo.dodajDrzavu(d);
    }
}
