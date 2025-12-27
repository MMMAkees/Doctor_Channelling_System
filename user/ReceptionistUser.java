package user;

import java.util.Scanner;

import patient.Patient;
import patient.PatientStack;
import doctor.Doctor;
import doctor.DoctorList;
import appointment.Appointment;
import appointment.AppointmentQueue;
import appointment.AppointmentHistoryStack;
import services.SearchService;

public class ReceptionistUser extends User {

    private DoctorList doctorList;
    private PatientStack patientStack;
    private AppointmentQueue appointmentQueue;
    private AppointmentHistoryStack historyStack;
    private SearchService searchService;

    public ReceptionistUser(String username, String password,
                            DoctorList doctorList,
                            PatientStack patientStack,
                            AppointmentQueue appointmentQueue,
                            AppointmentHistoryStack historyStack) {

        super(username, password);
        this.doctorList = doctorList;
        this.patientStack = patientStack;
        this.appointmentQueue = appointmentQueue;
        this.historyStack = historyStack;
        this.searchService = new SearchService();
    }

    // =====================================================
    // Register New Patient (Stack - Linked List)
    // =====================================================
    public void registerPatient() {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n--- Patient Registration ---");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Mobile: ");
            String mobile = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("City: ");
            String city = sc.nextLine();

            System.out.print("Medical History: ");
            String history = sc.nextLine();

            Patient patient = new Patient(name, age, mobile, email, city, history);
            patientStack.push(patient);

            System.out.println("Patient registered successfully.");

        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
        } finally {
            System.out.println("Registration process completed.");
        }
    }

    // =====================================================
    // Doctor Registration
    // =====================================================
    public void addDoctor() {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n--- Doctor Registration ---");

            System.out.print("Doctor Name: ");
            String name = sc.nextLine();

            System.out.print("Specialization: ");
            String specialization = sc.nextLine();

            System.out.print("Consultation Fee: ");
            double fee = Double.parseDouble(sc.nextLine());

            Doctor doctor = new Doctor(name, specialization, fee);
            doctorList.addDoctor(doctor);

            System.out.println("Doctor added successfully.");

        } catch (Exception e) {
            System.out.println("Doctor registration failed.");
        }
    }

    // =====================================================
    // Cancel Appointment + History
    // =====================================================
    public void cancelAppointment() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Patient Name to Cancel: ");
        String name = sc.nextLine();

        Appointment cancelled = appointmentQueue.cancelAppointmentAndReturn(name);

        if (cancelled != null) {
            historyStack.push(cancelled);
            System.out.println("Appointment cancelled and saved in history.");
        } else {
            System.out.println("No appointment found.");
        }
    }

    // =====================================================
    // View Appointment History (Stack)
    // =====================================================
    public void viewAppointmentHistory() {
        historyStack.displayHistory();
    }
}
