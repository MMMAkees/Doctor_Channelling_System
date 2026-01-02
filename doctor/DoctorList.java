package doctor;

import java.util.ArrayList;

public class DoctorList {

    private ArrayList<Doctor> doctors; 

    public DoctorList() {
        doctors = new ArrayList<>();
    }

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

        System.out.println("\n--- Available Doctors List ---");
        System.out.println("+-----+----------------------+----------------------+------------+------------+");
        System.out.println("| No. | Doctor Name          | Specialization       | Fee (Rs.)  | Time Slot  |");
        System.out.println("+-----+----------------------+----------------------+------------+------------+");

        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            System.out.printf("| %-3d | %-20s | %-20s | %-10.2f | %-10s |\n",
                    (i + 1), d.getDoctorName(), d.getSpecialization(), d.getConsultationFee(), "09:00 AM");
        }
        System.out.println("+-----+----------------------+----------------------+------------+------------+");
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
