package patient;

public class Patient {

    // ---------------- NEW FIELDS (Login) ----------------
    private String username;
    private String password;

    // ---------------- EXISTING FIELDS ----------------
    private String name;
    private int age;
    private String mobile;
    private String email;
    private String city;
    private String history;

    // ---------------- OLD CONSTRUCTOR (UNCHANGED) ----------------
    public Patient(String name, int age, String mobile,
                   String email, String city, String history) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.history = history;
    }

    // ---------------- NEW CONSTRUCTOR (WITH LOGIN) ----------------
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

    // LOGIN VALIDATION 
    public boolean validateLogin(String u, String p) {
        return username != null && password != null &&
               username.equals(u) && password.equals(p);
    }

    // ---------------- GETTERS ----------------
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

    // ---------------- OPTIONAL SETTERS ----------------
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

    // ---------------- DISPLAY ----------------
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
