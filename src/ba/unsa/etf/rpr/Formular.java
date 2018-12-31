package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Formular  implements Initializable {


    public Button obrisiBtn;
    public TextField obrisi;
    public Button dodajGrad;
    public Button dodajDrzavu;
    public TableView drzave;
    public TableColumn naziv;
    public TableView gradovi;
    public TableColumn ime;
    public TableColumn brojStanovnika;
    private ObservableList<Drzava> drz = FXCollections.observableArrayList();
    private ObservableList<Grad> grd = FXCollections.observableArrayList();
    private GeografijaDAO geo;
    public Formular(){
        GeografijaDAO.removeInstance();
        File dbfile = new File("resources\\baza.db");
        dbfile.delete();
        geo = GeografijaDAO.getInstance();
        obrisi= new TextField();
        dodajDrzavu = new Button();
        obrisiBtn = new Button();
        dodajGrad = new Button();
        drzave = new TableView();
        naziv = new TableColumn();
        gradovi = new TableView();
        ime = new TableColumn();
        brojStanovnika = new TableColumn();

    }
    public void brisi(ActionEvent actionEvent) {
        geo.obrisiDrzavu(obrisi.textProperty().get());
        drz.clear();
        drz.addAll(geo.drzave());
        grd.clear();
        grd.addAll(geo.gradovi());
    }

    public void dodajG(ActionEvent actionEvent) {
        Stage secStage= new Stage();
        Grad g = new Grad();
        Drzava d = new Drzava();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("dodaj.fxml"));
        loader.setController(new Dodaj());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        } secStage.setTitle("Dodaj");
        secStage.setScene(new Scene(root, 300, 275));
        secStage.show();
        secStage.setOnCloseRequest(event ->
        { drz.clear();
            drz.addAll(geo.drzave());
            grd.clear();
            grd.addAll(geo.gradovi());
        });

    }

    public void dodajD(ActionEvent actionEvent) {
        Stage secStage= new Stage();
        Grad g = new Grad();
        Drzava d = new Drzava();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dodaj.fxml"));
        loader.setController(new Dodaj());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        } secStage.setTitle("Dodaj");
        secStage.setScene(new Scene(root, 300, 275));
        secStage.show();
        secStage.setOnCloseRequest(event ->
        {         drz.clear();
            drz.addAll(geo.drzave());
            grd.clear();
            grd.addAll(geo.gradovi());
            ;
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drz.clear();
        drz.addAll(geo.drzave());
        grd.clear();
        grd.addAll(geo.gradovi());
        drzave.setItems(drz);
        gradovi.setItems(grd);
        naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        brojStanovnika.setCellValueFactory(new PropertyValueFactory<>("brojStanovnika"));


    }
}
