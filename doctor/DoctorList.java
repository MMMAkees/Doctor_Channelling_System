package doctor;

import java.util.ArrayList;

public class DoctorList {

    private ArrayList<Doctor> doctors;  // Stores all doctors added by receptionist

    public DoctorList() {
        doctors = new ArrayList<>();
    }

    // Add a new doctor (done by receptionist)
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        System.out.println("Doctor added successfully: " + doctor.getDoctorName());
    }

    // Get all doctors
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    // Display all doctors
    public void displayDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("No doctors available in the system.");
            return;
        }

        System.out.println("----- Available Doctors -----");
        for (Doctor d : doctors) {
            System.out.println(d);
        }
    }

    // Search doctor by specialization
    public Doctor searchBySpecialization(String specialization) {
        for (Doctor d : doctors) {
            if (d.getSpecialization().equalsIgnoreCase(specialization)) {
                return d;
            }
        }
        return null;
    }

    // Search doctor by name
    public Doctor searchByName(String name) {
        for (Doctor d : doctors) {
            if (d.getDoctorName().equalsIgnoreCase(name)) {
                return d;
            }
        }
        return null;
    }

    public int size() {
        return doctors.size();
    }
}
