package services;

import patient.Patient;
import doctor.Doctor;
import appointment.Appointment;

public class NotificationService {

    // ----------------------------
    // Notify Patient Registration
    // ----------------------------
    public void notifyPatientRegistered(Patient patient) {
        System.out.println("\n[NOTIFICATION] Patient Registered Successfully!");
        System.out.println("Patient: " + patient.getName() + ", Age: " + patient.getAge());
    }

    // ----------------------------
    // Notify Doctor Addition
    // ----------------------------
    public void notifyDoctorAdded(Doctor doctor) {
        System.out.println("\n[NOTIFICATION] Doctor Added Successfully!");
        System.out.println("Doctor: " + doctor.getDoctorName() + 
                           ", Specialization: " + doctor.getSpecialization());
    }

    // ----------------------------
    // Notify Appointment Confirmation
    // ----------------------------
    public void notifyAppointmentCreated(Appointment appointment) {
        System.out.println("\n[NOTIFICATION] Appointment Confirmed!");
        System.out.println(appointment);
    }

    // ----------------------------
    // Notify General Success Message
    // ----------------------------
    public void success(String message) {
        System.out.println("\n[SUCCESS] " + message);
    }

    // ----------------------------
    // Notify Warning
    // ----------------------------
    public void warning(String message) {
        System.out.println("\n[WARNING] " + message);
    }

    // ----------------------------
    // Notify Error
    // ----------------------------
    public void error(String message) {
        System.out.println("\n[ERROR] " + message);
    }
}
