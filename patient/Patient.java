package patient;

public class Patient {

    private String username;
    private String password;
    private String name;
    private int age;
    private String mobile;
    private String email;
    private String city;
    private String history;

    public Patient(String name, int age, String mobile, String email, String city, String history) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.history = history;
    }


    public Patient(String username, String password, String name, int age, String mobile, String email, String city, String history) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.history = history;
    }


    public boolean validateLogin(String u, String p) {
        return username != null && password != null &&
               username.equals(u) && password.equals(p);
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getHistory() {
        return history;
    }

  
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHistory(String history) {
        this.history = history;
    }

  
    @Override
    public String toString() {
        return "Patient Name: " + name +
               "\nAge: " + age +
               "\nMobile: " + mobile +
               "\nEmail: " + email +
               "\nCity: " + city +
               "\nMedical History: " + history;
    }
}
