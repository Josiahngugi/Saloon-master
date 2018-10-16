import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
public  class App{
    static Db db=new Db();
    static Map<String,Object> model=new HashMap<String,Object>();
    public static void main(String[] args) {
        get("/",(req,res)->{
            model.put("stylists",null);
            model.put("stylists",db.all());
            model.put("template","templates/stylist.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        post("/newStylist",(req,res)->{
            String id=req.queryParams("id");
            Stylist stylist=new Stylist(
                    Double.parseDouble(id),
                    req.queryParams("fname"),
                    req.queryParams("lname"),
                    req.queryParams("phone"),
                    req.queryParams("email")
            );

            res.redirect("/");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        get("/deleteStylist/:stylist",(req,res)->{
            String SQL="DELETE FROM  stylist WHERE id="+req.params(":stylist");
            db.executeCommand(SQL);
            res.redirect("/");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        post("/updateStylist",(req,res)->{
            System.out.println(model.get("id"));
            String str=(String) model.get("id");
            db.getCon().createQuery("UPDATE stylist SET fname=:fname,lname=:lname,phone=:phone,email=:email WHERE id=:id;")
                    .addParameter("fname",req.queryParams("fname"))
                    .addParameter("lname",req.queryParams("lname"))
                    .addParameter("phone",req.queryParams("phone"))
                    .addParameter("email",req.queryParams("email"))
                    .addParameter("id",Double.parseDouble(str))
                    .executeUpdate();

            res.redirect("/");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        get("/getDetails/:id",(req,res)->{
            model.put("stylist",db.getStylist(Double.parseDouble(req.params(":id"))));
            model.put("clients",db.getClients(Double.parseDouble(req.params(":id"))));
            model.put("template","templates/Stylistinfo.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        get("/updateStylist/:stylist/:id",(req,res)->{
            model.put("update",req.params(":stylist"));
            model.put("id",req.params(":id"));
            model.put("template","templates/updateStylist.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());


        post("/newClient",(req,res)->{
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

        get("/getClients",(req,res)->{
            model.put("clients",db.getCon().createQuery("SELECT * FROM client;").executeAndFetch(Client.class));
            model.put("template","templates/clients.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        get("/updateClient/:client/:id",(req,res)->{
            model.put("update",req.params(":client"));
            model.put("id",req.params(":id"));
            model.put("stylists",db.getCon().createQuery("SELECT * FROM stylist;"));
            model.put("template","templates/updateClients.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());

        get("/deleteClient/:client/:stylist",(req,res)->{
            String SQL="DELETE FROM  client WHERE id="+req.params(":client");
            db.executeCommand(SQL);
            res.redirect("/getDetails/"+req.params(":stylist"));
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());
    }
}
