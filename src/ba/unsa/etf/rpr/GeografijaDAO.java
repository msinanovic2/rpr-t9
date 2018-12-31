package ba.unsa.etf.rpr;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GeografijaDAO {
    private static GeografijaDAO instance=null;
    private Connection conn;
    PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,gradid,drzavaid;
    private GeografijaDAO(){

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:resources\\baza.db");
            Statement s = conn.createStatement();
            s.execute("Select * from drzava");
        }catch (Exception e){
            Statement s = null;
            try {
                s = conn.createStatement();
                s.execute("CREATE TABLE drzava(id INTEGER PRIMARY KEY ,naziv varchar(255) not null, glavni_grad integer )");
                s.execute("CREATE TABLE grad(id integer primary key, naziv varchar(255), broj_stanovnika INTEGER,drzava integer) ");
                s.execute("insert into drzava Values (1,'Velika Britanija',1)");
                s.execute("insert into drzava Values (2,'Francuska',2)");
                s.execute("insert into drzava Values (3,'Austrija',3)");
                s.execute("insert into grad  Values (1,'London',8825000,1)");
                s.execute("insert into grad  Values (2,'Pariz',2206488,2)");
                s.execute("insert into grad  Values (3,'Beč',1899055,3)");
                s.execute("insert into grad  Values (4,'Manchester',545500,1)");
                s.execute("insert into grad  Values (5,'Graz',280200,3)");
                ps7 = conn.prepareStatement("INSERT INTO grad VALUES(?,?,?,?)");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        try {
            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where id = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");

        }catch (Exception e){

        }


    }
    
    public static void removeInstance() {
        try {
            if(instance != null)
            instance.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        instance = null;
    }
    private static void initialize(){
        instance = new GeografijaDAO();

    }

    public static GeografijaDAO getInstance() {
        if(instance == null) initialize();
        return instance;
    }

    public ArrayList<Grad> gradovi() {
         try {
             instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
             instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
             instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
             instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
             instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where id = ?");
             instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");

         }catch (Exception e){
         }
        Map<Integer, Grad> gradovi = new HashMap<>();
        Map<Integer, Drzava> drzave = new HashMap<>();
        ArrayList<Integer> indeksi = new ArrayList<>();
        try {
            Statement s = conn.createStatement();

            ResultSet tabDrzava = s.executeQuery("Select * from drzava ");
            while (tabDrzava.next()) {
                int id = tabDrzava.getInt(1);
                Drzava d = new Drzava();
                Grad g = new Grad();
                g.setIme(String.valueOf(tabDrzava.getInt(3)));
                d.setGlavniGrad(g);
                String ime = tabDrzava.getString(2);
                d.setNaziv(ime);
                drzave.put(id, d);
                indeksi.add(id);
            }
            ResultSet tabGrad = s.executeQuery("Select * from grad order by broj_stanovnika");
            while (tabGrad.next()) {
                int id = tabDrzava.getInt(1);
                String ime = tabGrad.getString(2);
                Grad g = new Grad();
                g.setIme(ime);
                g.setBrojStanovnika(tabGrad.getInt(3));
                g.setDrzava(drzave.get(tabGrad.getInt(4)));
                gradovi.put(id, g);
            }
            for (int a: indeksi) {
                int grad= Integer.parseInt( drzave.get(a).getGlavniGrad().getIme());
                drzave.get(a).setGlavniGrad(gradovi.get(grad));
            }
        }catch (Exception e){

        }
        ArrayList<Grad >  rezultat = new ArrayList<>();
        rezultat.addAll(gradovi.values());
        rezultat.sort(new Comparator<Grad>() {
            @Override
            public int compare(Grad o1, Grad o2) {
                return -o1.getBrojStanovnika()+o2.getBrojStanovnika();
            }
        });
        return rezultat;
    }

    public Grad glavniGrad(String drzava) {
        try {

            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where id = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");

            int id=-1;
            Statement s = conn.createStatement();
            ResultSet tabDrzava = s.executeQuery("Select * from drzava");
            Drzava drzava1 = new Drzava();
            drzava1.setNaziv(drzava);
            while(tabDrzava.next()){
                if(drzava.equals(tabDrzava.getString(2))){
                    drzava1.setNaziv(drzava);
                    id=tabDrzava.getInt(3);
                    break;
                }
            }
            ps6.setInt(1,id);
            ResultSet rs2 = ps6.executeQuery();
            if(!rs2.next()){
                return null;
            }
            Grad glavniGrad=new Grad();
                glavniGrad.setIme(rs2.getString(2));
                glavniGrad.setDrzava(drzava1);
                glavniGrad.setBrojStanovnika(rs2.getInt(3));
                drzava1.setGlavniGrad(glavniGrad);
            return glavniGrad;
            } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public void obrisiDrzavu(String drzava) {
        int id=0;
        try {


            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where id = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");
        }catch (Exception e){}
        try {
            ps1.setString(1,drzava);
            ResultSet set = ps1.executeQuery();
            if(set.isClosed()){
                return ;
            }
            set.next();
            id=set.getInt(1);
            set.close();
            ps2.setInt(1,id);
            ps2.execute();
            ps3.setInt(1,id);
            ps3.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Drzava nadjiDrzavu(String drzava) {

    try {
        instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
        instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
        instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
        instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
        instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where naziv = ?");
        instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");
    }catch (Exception e){}
        try {

            ps5.setString(1,drzava);
            ResultSet set = ps5.executeQuery();
            if(!set.next()){
                return null;
            }
            Drzava drzava1 = new Drzava();
            int id=0;
            id = set.getInt(3);
            drzava1.setNaziv(set.getString(2));
            Grad glavniGrad = new Grad();
            ps6.setInt(1,id);
            ResultSet res = ps6.executeQuery();
            res.next();
            glavniGrad.setIme(res.getString(2));
            glavniGrad.setBrojStanovnika(res.getInt(3));
            glavniGrad.setDrzava(drzava1);
            return drzava1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void dodajGrad(Grad grad) {
        try {
            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where naziv = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");
        } catch (Exception e){
        }
        try {
            ps1.setString(1,grad.getDrzava().getNaziv());
            ResultSet drzava_grada = ps1.executeQuery();
            if(!drzava_grada.next()) return;
            int idDrzave = drzava_grada.getInt(1);
            PreparedStatement s1 = conn.prepareStatement("Insert into grad values (?,?,?,?)");
            Statement s = conn.createStatement();
            ResultSet aa= s.executeQuery("Select max(id) from grad");
            aa.next();
            int id_grada = aa.getInt(1)+1;
            s1.setInt(1,id_grada);
            s1.setString(2,grad.getIme());
            s1.setInt(3,grad.getBrojStanovnika());
            s1.setInt(4,idDrzave);
            s1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dodajDrzavu(Drzava drzava) {
        try {


            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where naziv = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");
        }catch (Exception e){}

        try {
            ps5.setString(1,drzava.getNaziv());

            ResultSet set = ps5.executeQuery();
            if( set.isClosed()|| !set.next() ){
                Statement stm = conn.createStatement();
                set = stm.executeQuery("select max(id) from drzava");
                ps4.setString(2,drzava.getNaziv());
                set.next();
                int a = set.getInt(1)+1;
                ps4.setInt(1,a);
                set = stm.executeQuery("select max(id) from grad");
                set.next();
                int b=set.getInt(1)+1;
                ps4.setInt(3,b);
                ps4.executeUpdate();
                dodajGrad(drzava.getGlavniGrad());


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void izmijeniGrad(Grad grad) {
        PreparedStatement statement = null;
        try {
            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where  naziv = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");
        }catch (Exception e){
        }
        try {
            ps5.setString(1,grad.getDrzava().getNaziv());
            ResultSet res = ps5.executeQuery();
            int id=-1;
            res.next();
            id=res.getInt(3);
            ps6.setInt(1,id);
            ResultSet set = ps6.executeQuery();
            statement = conn.prepareStatement("update grad set naziv=? where id =?");
            statement.setInt(2,id);
            statement.setString(1,grad.getIme());
            set.close();
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Drzava> drzave(){
        ArrayList<Drzava> drazaveee = new ArrayList<Drzava>();
        try {
            instance.ps1 = instance.conn.prepareStatement("select id from drzava where naziv = ?");
            instance.ps2 = instance.conn.prepareStatement("delete from grad where drzava=?");
            instance.ps3 = instance.conn.prepareStatement("delete from drzava where id=?");
            instance.ps4 = instance.conn.prepareStatement("insert into drzava values (?,?,?)");
            instance.ps5 = instance.conn.prepareStatement("Select * from Drzava where id = ?");
            instance.ps6 = instance.conn.prepareStatement("Select * from grad where id = ?");

        }catch (Exception e){
        }
        Map<Integer, Grad> gradovi = new HashMap<>();
        Map<Integer, Drzava> drzave = new HashMap<>();
        ArrayList<Integer> indeksi = new ArrayList<>();
        ArrayList<Drzava >  rezultat = new ArrayList<>();

        try {
            Statement s = conn.createStatement();

            ResultSet tabDrzava = s.executeQuery("Select * from drzava ");
            while (tabDrzava.next()) {
                int id = tabDrzava.getInt(1);
                Drzava d = new Drzava();
                Grad g = new Grad();
                g.setIme(String.valueOf(tabDrzava.getInt(3)));
                d.setGlavniGrad(g);
                String ime = tabDrzava.getString(2);
                d.setNaziv(ime);
                rezultat.add(d);
                indeksi.add(id);
            }
        }catch (Exception e){

        }
        rezultat.addAll(drzave.values());
        return  rezultat;
    }
    public void test(){}
}