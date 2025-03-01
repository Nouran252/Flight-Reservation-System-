
public abstract class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    public User(int id, String name, String email, String phone,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password=password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        if (name != null && !name.isEmpty())
        {
            this.name = name;
        }
        else
        {
            throw new IllegalArgumentException("Please enter a name.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Please enter your email address .");
        }
    }
    public void setPassword(String password) {
        if (password != null && password.length()>=8) {
            this.password = password;
        }else {
            throw new IllegalArgumentException("Please enter valid password .");
        }
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.length() == 11 ) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Please enter a valid 11-digit phone number.");
        }
    }


    // Abstract Method
    public abstract void displayInfo();

 // login function
 public static String login(String email, String password) {
     for (Admin admin : Main.admins) {
         if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
             System.out.println("Welcome,  " + admin.getName() + "!");
             System.out.println("-----------------------");
             return "Admin";

         }
     }

     for (Passenger passenger : Main.passengers) {
         if (passenger.getEmail().equals(email) && passenger.getPassword().equals(password)) {
             System.out.println("Welcome,  " + passenger.getName() + "!");
             System.out.println("-----------------------");
             return "Passenger";
         }
     }
     return "-1";

 }

    //logout method
    public  static void logout(User current)
    {
        System.out.println(current.name+" has logged out.");
    }
}