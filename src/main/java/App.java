import com.sun.security.ntlm.Client;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


public class App {
    static Db db=new Db();
    static Map<String,Object> model=new HashMap<String,Object>();
    public static void main(String[]args){
        get("/",(req,res)->{
            model.put("stylists",null);
            model.put("stylists",db.all());
            model.put("template","templates/stylist.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        post("/addStylist",(req,res)->{
            String id=req.queryParams("id");
            Stylist stylist=new Stylist(
                    Double.parseDouble(id),
                    req.queryParams("fname"),
                    req.queryParams("lname"),
                    req.queryParams("phone"),
                    req.queryParams("email")
            );
            System.out.println(stylist.getPhone());
            if(stylist.save()){
                model.put("message","Successfully Added ");
            }
            else{
                model.put("message","Stylist Exists");
            }
            res.redirect("/");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        post("/addClient",(req,res)->{
            System.out.println(req.queryParams("stylist"));
            Client client=new Client(
                    1,
                    req.queryParams("fname"),
                    req.queryParams("lname"),
                    req.queryParams("phone"),
                    req.queryParams("email"),
                    Double.parseDouble(req.queryParams("stylist"))
            );
            client.save();
            res.redirect("/getDetails/"+req.queryParams("stylist"));
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());
    }
}
