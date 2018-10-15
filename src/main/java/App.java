import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


public class App {
    static DB db=new DB();
    static Map<String,Object> model=new HashMap<String,Object>();
    public static void main(String[]args){
        get("/",(req,res)->{
            model.put("stylists",null);
            model.put("stylists",db.allData());
            model.put("template","templates/stylist.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        },new VelocityTemplateEngine());
    }
}
