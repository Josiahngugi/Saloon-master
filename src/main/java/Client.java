public  class Client{
    private double id;
    private String fname;;
    private String lname;
    private String phone;
    private String email;
    private double stylist;
    private  Db db=new Db();
    public  Client(double id,String fname,String lname,String phone,String email,double stylist){
        this.id=id;
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.email=email;
        this.stylist=stylist;
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
    public double getStylist(){
        return this.stylist;
    }

    public  boolean  save(){
            db.getCon().createQuery("INSERT INTO client (fname,lname,phone,email,stylist) VALUES(:fname,:lname,:phone,:email,:stylist)")
                    .addParameter("fname",fname)
                    .addParameter("lname",lname)
                    .addParameter("phone",phone)
                    .addParameter("email",email)
                    .addParameter("stylist",stylist)
                    .executeUpdate();
            return true;
        }
    }

