package main;

import java.util.Scanner;

// Import packages
import patient.Patient;
import patient.PatientStack;
import doctor.Doctor;
import doctor.DoctorList;
import appointment.Appointment;
import appointment.AppointmentQueue;
import appointment.AppointmentHistoryStack;
import appointment.WaitingQueue;
import services.SortingService;
import services.SearchService;
import services.NotificationService;
import user.PatientUser;
import user.ReceptionistUser;

public class MainSystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ---------------- DATA STRUCTURES ----------------
        PatientStack patientStack = new PatientStack();
        DoctorList doctorList = new DoctorList();
        AppointmentQueue appointmentQueue = new AppointmentQueue(20);
        AppointmentHistoryStack historyStack = new AppointmentHistoryStack(50);
        WaitingQueue waitingQueue = new WaitingQueue(20);

        // ---------------- SERVICES ----------------
        SortingService sortingService = new SortingService();
        SearchService searchService = new SearchService();
        NotificationService notify = new NotificationService();

        // ---------------- USERS ----------------
        PatientUser patientUser = new PatientUser("patient", "1234", patientStack);
        ReceptionistUser receptionistUser = new ReceptionistUser(
                "admin", "0000",
                doctorList, patientStack,
                appointmentQueue, historyStack
        );

        boolean running = true;

        while (running) {

            System.out.println("\n===============================");
            System.out.println(" DOCTOR CHANNELLING SYSTEM ");
            System.out.println("===============================");
            System.out.println("1. Patient");
            System.out.println("2. Receptionist");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ================= PATIENT =================
                case 1:
                    patientEntryMenu(sc, patientStack, doctorList,
                            appointmentQueue, waitingQueue,
                            searchService, notify, historyStack);
                    break;

                // ================= RECEPTIONIST =================
                case 2:
                    System.out.print("Username: ");
                    String ru = sc.nextLine();
                    System.out.print("Password: ");
                    String rp = sc.nextLine();

                    if (receptionistUser.login(ru, rp)) {
                        receptionistMenu(sc, doctorList, patientStack,
                                appointmentQueue, historyStack,
                                waitingQueue, sortingService,
                                searchService, notify);
                    } else {
                        notify.error("Invalid receptionist login");
                    }
                    break;

                case 3:
                    running = false;
                    System.out.println("System Closed.");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }

        sc.close();
    }

    // =================================================
    // PATIENT ENTRY MENU (LOGIN / REGISTER)
    // =================================================
    public static void patientEntryMenu(Scanner sc,
                                        PatientStack patientStack,
                                        DoctorList doctorList,
                                        AppointmentQueue appointmentQueue,
                                        WaitingQueue waitingQueue,
                                        SearchService searchService,
                                        NotificationService notify,
                                        AppointmentHistoryStack historyStack) {

        boolean run = true;

        while (run) {
            System.out.println("\n--- PATIENT ACCESS ---");
            System.out.println("1. Patient Login");
            System.out.println("2. Patient Registration");
            System.out.println("3. Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // -------- LOGIN --------
                case 1:
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    Patient loggedPatient = validatePatientLogin(patientStack, u, p);

                    if (loggedPatient != null) {
                        notify.success("Login successful!");
                        patientMenu(sc, patientStack, doctorList,
                                appointmentQueue, waitingQueue,
                                searchService, notify, historyStack);
                    } else {
                        notify.error("Invalid patient credentials");
                    }
                    break;

                // -------- REGISTER --------
                case 2:
                    registerPatientWithLogin(sc, patientStack, notify);
                    break;

                case 3:
                    run = false;
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // =================================================
    // PATIENT MENU (UNCHANGED LOGIC)
    // =================================================
    public static void patientMenu(Scanner sc,
                                   PatientStack patientStack,
                                   DoctorList doctorList,
                                   AppointmentQueue appointmentQueue,
                                   WaitingQueue waitingQueue,
                                   SearchService searchService,
                                   NotificationService notify,
                                   AppointmentHistoryStack historyStack) {

        boolean run = true;

        while (run) {
            System.out.println("\n--- PATIENT MENU ---");
            System.out.println("1. Search Doctor");
            System.out.println("2. Book Appointment");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. View My Appointments");
            System.out.println("5. View Medical History");
            System.out.println("6. View Appointment History");
            System.out.println("7. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: searchDoctorOnly(sc, doctorList, searchService); break;
                case 2: handleBooking(sc, patientStack, doctorList,
                        appointmentQueue, waitingQueue, searchService, notify); break;
                case 3: handleCancellation(sc, appointmentQueue, notify); break;
                case 4: viewMyAppointments(sc, appointmentQueue); break;
                case 5: viewMedicalHistory(sc, patientStack); break;
                case 6: viewMyAppointmentHistory(sc, historyStack); break;
                case 7: run = false; break;
                default: System.out.println("Invalid option");
            }
        }
    }

    // =================================================
    // PATIENT LOGIN VALIDATION
    // =================================================
    public static Patient validatePatientLogin(PatientStack patientStack,
                                               String username, String password) {

        Patient found = null;
        PatientStack temp = new PatientStack();

        while (!patientStack.isEmpty()) {
            Patient p = patientStack.pop();
            if (p.validateLogin(username, password)) {
                found = p;
            }
            temp.push(p);
        }
        while (!temp.isEmpty()) patientStack.push(temp.pop());

        return found;
    }

    // PATIENT REGISTRATION WITH LOGIN
    public static void registerPatientWithLogin(Scanner sc,PatientStack patientStack,NotificationService notify) {

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = sc.nextInt(); sc.nextLine();

        System.out.print("Mobile: ");
        String mobile = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("City: ");
        String city = sc.nextLine();

        System.out.print("Medical History: ");
        String history = sc.nextLine();

        Patient p = new Patient(username, password,name, age, mobile, email, city, history);

        patientStack.push(p);
        notify.success("Patient registered successfully!");
    }


    // ---------------- Shared Methods ----------------

    public static void showWaitingListMessage(PatientStack patientStack,
                                              WaitingQueue waitingQueue,
                                              String username) {
        boolean inWaitingList = false;

        for (int i = 0; i < waitingQueue.getSize(); i++) {
            Patient p = waitingQueue.getAtIndex(i);
            if (p.getName().equalsIgnoreCase(username)) {
                inWaitingList = true;
                break;
            }
        }

        if (inWaitingList) {
            System.out.println("\n>>> NOTE: You are currently in the waiting list for an appointment. <<<\n");
        }
    }

    public static void viewMyAppointments(Scanner sc, AppointmentQueue appointmentQueue) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        if (appointmentQueue.isEmpty()) {
            System.out.println("No appointments booked.");
            return;
        }

        boolean found = false;
        System.out.println("\n--- My Appointments ---");

        for (int i = 0; i < appointmentQueue.getSize(); i++) {
            Appointment appt = appointmentQueue.getAtIndex(i);
            if (appt.getPatient().getName().equalsIgnoreCase(name)) {
                System.out.println(appt);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for " + name);
        }
    }

    public static void viewMyMedicalHistory(Scanner sc, PatientStack patientStack) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        Patient found = null;
        PatientStack temp = new PatientStack();

        while (!patientStack.isEmpty()) {
            Patient p = patientStack.pop();
            if (p.getName().equalsIgnoreCase(name)) {
                found = p;
            }
            temp.push(p);
        }
        while (!temp.isEmpty()) {
            patientStack.push(temp.pop());
        }

        if (found != null) {
            System.out.println("\n--- My Medical History ---");
            System.out.println(found.getHistory());
        } else {
            System.out.println("Patient not found.");
        }
    }

    public static void viewMyAppointmentHistory(Scanner sc, AppointmentHistoryStack historyStack) {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        if (historyStack.isEmpty()) {
            System.out.println("No appointment history available.");
            return;
        }

        boolean found = false;
        System.out.println("\n--- My Appointment History ---");

        Appointment[] arr = historyStack.toArray();
        for (Appointment appt : arr) {
            if (appt.getPatient().getName().equalsIgnoreCase(name)) {
                System.out.println(appt);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointment history found for " + name);
        }
    }   
    
    // ---------------- Receptionist Menu ----------------
    public static void receptionistMenu(Scanner sc,
                                        DoctorList doctorList,
                                        PatientStack patientStack,
                                        AppointmentQueue appointmentQueue,
                                        AppointmentHistoryStack historyStack,
                                        WaitingQueue waitingQueue,
                                        SortingService sortingService,
                                        SearchService searchService,
                                        NotificationService notify) {
        boolean run = true;
        while (run) {
            System.out.println("\n--- RECEPTIONIST MENU ---");
            System.out.println("1. Register Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Book Appointment");
            System.out.println("4. Search Doctor");
            System.out.println("5. Search Patient");
            System.out.println("6. View Patients (Recent)");
            System.out.println("7. Cancel Appointment");
            System.out.println("8. View Appointment History");
            System.out.println("9. View Patient Medical History");
            System.out.println("10. View Doctors");
            System.out.println("11. View Appointments");
            System.out.println("12. Sort Patients by Age");
            System.out.println("13. View Waiting List");
            System.out.println("14. Assign Waiting Patient");
            System.out.println("15. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: registerPatient(sc, patientStack, notify); break;
                case 2: addDoctor(sc, doctorList, notify); break;
                case 3: handleBooking(sc, patientStack, doctorList,
                        appointmentQueue, waitingQueue, searchService, notify); break;
                case 4: searchDoctorOnly(sc, doctorList, searchService); break;
                case 5: searchPatient(sc, patientStack); break;
                case 6: patientStack.displayNewestToOldest(); break;
                case 7: cancelWithHistory(sc, appointmentQueue, historyStack, notify); break;
                case 8: historyStack.displayHistory(); break;
                case 9: viewMedicalHistory(sc, patientStack); break;
                case 10: doctorList.displayDoctors(); break;
                case 11: appointmentQueue.displayAll(); break;
                case 12: sortPatients(patientStack, sortingService); break;
                case 13: waitingQueue.display(); break;
                case 14: assignFromWaiting(sc, waitingQueue, doctorList, appointmentQueue, notify); break;
                case 15: run = false; break;
                default: System.out.println("Invalid option");
            }
        }
    }

    // ---------------- Shared Methods ----------------
    public static void registerPatient(Scanner sc, PatientStack patientStack, NotificationService notify) {
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Age: "); int age = sc.nextInt(); sc.nextLine();
        System.out.print("Mobile: "); String mobile = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("City: "); String city = sc.nextLine();
        System.out.print("Medical History: "); String history = sc.nextLine();
        Patient p = new Patient(name, age, mobile, email, city, history);
        patientStack.push(p);
        notify.notifyPatientRegistered(p);
    }

    public static void addDoctor(Scanner sc, DoctorList doctorList, NotificationService notify) {
        System.out.print("Doctor Name: "); String name = sc.nextLine();
        System.out.print("Specialization: "); String spec = sc.nextLine();
        System.out.print("Consultation Fee: "); double fee = sc.nextDouble(); sc.nextLine();
        Doctor d = new Doctor(name, spec, fee);
        doctorList.addDoctor(d);
        notify.notifyDoctorAdded(d);
    }

    public static void handleBooking(Scanner sc, PatientStack patientStack,
                                     DoctorList doctorList,
                                     AppointmentQueue appointmentQueue,
                                     WaitingQueue waitingQueue,
                                     SearchService searchService,
                                     NotificationService notify) {
        System.out.print("Patient Name: "); String pname = sc.nextLine();
        Patient found = null;
        PatientStack temp = new PatientStack();
        while (!patientStack.isEmpty()) {
            Patient p = patientStack.pop();
            if (p.getName().equalsIgnoreCase(pname)) found = p;
            temp.push(p);
        }
        while (!temp.isEmpty()) patientStack.push(temp.pop());
        if (found == null) { notify.error("Patient not found"); return; }

        doctorList.displayDoctors();
        System.out.print("Doctor Name: "); 
        Doctor doc = doctorList.searchByName(sc.nextLine());
        if (doc == null) { notify.error("Doctor not found"); return; }

        System.out.print("Time Slot: "); String slot = sc.nextLine();
        if (appointmentQueue.isBooked(doc.getDoctorName(), slot)) {
            System.out.print("Slot booked. Add to waiting list? (Y/N): ");
            if (sc.nextLine().equalsIgnoreCase("Y")) {
                waitingQueue.enqueue(found);
                notify.warning("Added to waiting list.");
            }
            return;
        }

        Appointment appt = new Appointment(found, doc, slot);
        appointmentQueue.enqueue(appt);
        notify.notifyAppointmentCreated(appt);
    }

    public static void assignFromWaiting(Scanner sc, WaitingQueue waitingQueue,
                                         DoctorList doctorList,
                                         AppointmentQueue appointmentQueue,
                                         NotificationService notify) {
        if (waitingQueue.isEmpty()) { System.out.println("Waiting list empty."); return; }
        Patient p = waitingQueue.dequeue();
        doctorList.displayDoctors();
        System.out.print("Assign Doctor: "); 
        Doctor doc = doctorList.searchByName(sc.nextLine());
        System.out.print("Time Slot: "); String slot = sc.nextLine();
        if (appointmentQueue.isBooked(doc.getDoctorName(), slot)) {
            notify.error("Slot still booked."); waitingQueue.enqueue(p); return;
        }
        Appointment appt = new Appointment(p, doc, slot);
        appointmentQueue.enqueue(appt);
        notify.success("Waiting patient assigned.");
    }

    public static boolean handleCancellation(Scanner sc,
                                             AppointmentQueue appointmentQueue,
                                             NotificationService notify) {
        System.out.print("Patient Name: "); String name = sc.nextLine();
        if (appointmentQueue.cancelAppointment(name)) {
            notify.success("Appointment cancelled"); return true;
        }
        notify.error("No appointment found"); return false;
    }

    public static void cancelWithHistory(Scanner sc,
                                         AppointmentQueue queue,
                                         AppointmentHistoryStack historyStack,
                                         NotificationService notify) {
        System.out.print("Patient Name: "); String name = sc.nextLine();
        Appointment removed = queue.removeAndReturn(name);
        if (removed != null) { historyStack.push(removed); notify.success("Appointment cancelled and saved to history."); }
        else notify.error("Appointment not found.");
    }

    public static void searchDoctorOnly(Scanner sc, DoctorList doctorList, SearchService searchService) {
        System.out.println("1. By Name\n2. By Specialization\nChoose: ");
        int ch = sc.nextInt(); sc.nextLine();
        Doctor d = (ch == 1) ? searchService.searchDoctorByName(doctorList, sc.nextLine())
                : searchService.searchDoctorBySpecialization(doctorList, sc.nextLine());
        System.out.println(d != null ? d : "Not found");
    }

    public static void searchPatient(Scanner sc, PatientStack patientStack) {
        System.out.print("Patient Name: "); String name = sc.nextLine();
        Patient found = null; PatientStack temp = new PatientStack();
        while (!patientStack.isEmpty()) { Patient p = patientStack.pop(); if (p.getName().equalsIgnoreCase(name)) found = p; temp.push(p); }
        while (!temp.isEmpty()) patientStack.push(temp.pop());
        System.out.println(found != null ? found : "Patient not found.");
    }

    public static void viewMedicalHistory(Scanner sc, PatientStack patientStack) {
        System.out.print("Patient Name: "); String name = sc.nextLine();
        Patient found = null; PatientStack temp = new PatientStack();
        while (!patientStack.isEmpty()) { Patient p = patientStack.pop(); if (p.getName().equalsIgnoreCase(name)) found = p; temp.push(p); }
        while (!temp.isEmpty()) patientStack.push(temp.pop());
        if (found != null) { System.out.println("\n--- Medical History ---"); System.out.println(found.getHistory()); }
        else System.out.println("Patient not found.");
    }

    public static void sortPatients(PatientStack patientStack, SortingService sortingService) {
        int size = patientStack.size(); if (size == 0) return;
        Patient[] arr = new Patient[size]; PatientStack temp = new PatientStack(); int i=0;
        while (!patientStack.isEmpty()) { Patient p=patientStack.pop(); arr[i++]=p; temp.push(p); }
        while (!temp.isEmpty()) patientStack.push(temp.pop());
        Scanner sc=new Scanner(System.in);
        System.out.print("1.Bubble 2.Selection : "); int choice=sc.nextInt();
        if(choice==1) sortingService.bubbleSortByAge(arr); else sortingService.selectionSortByAge(arr);
        for(Patient p: arr) System.out.println(p);
    }
}
