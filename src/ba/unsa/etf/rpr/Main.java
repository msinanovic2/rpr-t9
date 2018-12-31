package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application {

    protected static GeografijaDAO baza;
    public static void main(String[] args) throws SQLException {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("formular.fxml"));
        loader.setController(new Formular());
        Parent root = loader.load();
        primaryStage.setTitle("Frm");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }

    private static void glavniGrad() {
        GeografijaDAO baza = GeografijaDAO.getInstance();
        String drzava;
        System.out.print("Unesite ime drzave: ");
        Scanner tok = new Scanner(System.in);
        drzava = tok.nextLine();
        Grad grad = baza.glavniGrad(drzava);
        System.out.println(grad.getIme());
    }

    public static String ispisiGradove() {
        baza = GeografijaDAO.getInstance();
        ArrayList<Grad> gradovi = baza.gradovi();
        StringBuilder res= new StringBuilder();
        for(Grad x : gradovi){
            res.append(x.getIme()).append(" (").append(x.getDrzava().getNaziv()).append(") - ").append(x.getBrojStanovnika()).append("\n");
        }
        return res.toString();
    }

}