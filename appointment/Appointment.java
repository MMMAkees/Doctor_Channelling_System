package appointment;

import doctor.Doctor;
import patient.Patient;

public class Appointment {

    private Patient patient;     
    private Doctor doctor;       
    private String timeSlot;     

    public Appointment(Patient patient, Doctor doctor, String timeSlot) {
        this.patient = patient;
        this.doctor = doctor;
        this.timeSlot = timeSlot;
    }

    // Getters
    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    // Setters
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    @Override
    public String toString() {
        return "Appointment Details => " +
                "Patient: " + patient.getName() +
                ", Doctor: " + doctor.getDoctorName() +
                ", Specialization: " + doctor.getSpecialization() +
                ", Time: " + timeSlot +
                ", Fee: Rs." + doctor.getConsultationFee();
    }
}
