public class Stylist {
    private double id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private  Db db=new Db();
    public  Stylist(double id,String fname,String lname,String phone,String email){
        this.id=id;
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.email=email;
    }
    public double getId(){
        return this.id;
    }
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getEmail(){
        return this.email;
    }

}
