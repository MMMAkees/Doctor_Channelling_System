package services;

import patient.Patient;
import doctor.Doctor;
import doctor.DoctorList;

public class SearchService {

    // -------------------------------
    // Search Patient by Name
    // -------------------------------
    public Patient searchPatientByName(Patient[] patients, String name) {

        if (patients == null) {
            System.out.println("Patient list is empty!");
            return null;
        }

        for (Patient p : patients) {
            if (p != null && p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null; // Not found
    }

    // -------------------------------
    // Search Doctor by Name
    // -------------------------------
    public Doctor searchDoctorByName(DoctorList doctorList, String name) {

        if (doctorList == null || doctorList.getDoctors().isEmpty()) {
            System.out.println("No doctors available!");
            return null;
        }

        for (Doctor d : doctorList.getDoctors()) {
            if (d.getDoctorName().equalsIgnoreCase(name)) {
                return d;
            }
        }

        return null; // Not found
    }

    // -------------------------------
    // Search Doctor by Specialization
    // -------------------------------
    public Doctor searchDoctorBySpecialization(DoctorList doctorList, String specialization) {

        if (doctorList == null || doctorList.getDoctors().isEmpty()) {
            System.out.println("No doctors available!");
            return null;
        }

        for (Doctor d : doctorList.getDoctors()) {
            if (d.getSpecialization().equalsIgnoreCase(specialization)) {
                return d;
            }
        }

        return null; 
    }
}
