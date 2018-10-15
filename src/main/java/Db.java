import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class Db {
    private Connection con;
    public Db(){
        con=new Sql2o("jdbc:postgresql://localhost:5432/hair_saloon","josiah","@J706643731m").open();
    }
    public Connection getCon(){
        return con;
    }
    public  String executeCommand(String sql){
        con.createQuery(sql).executeUpdate();
        return "Complete";
    }
}
