package main;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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

        // ---------------- CREATE SAMPLE CSV FILES ----------------
        createSampleCSVFiles();
        
        // ---------------- DATA STRUCTURES ----------------
        PatientStack patientStack = new PatientStack();
        DoctorList doctorList = new DoctorList();
        AppointmentQueue appointmentQueue = new AppointmentQueue(20);
        AppointmentHistoryStack historyStack = new AppointmentHistoryStack(50);
        WaitingQueue waitingQueue = new WaitingQueue(20);

        // ---------------- LOAD DATA FROM CSV FILES ----------------
        System.out.println("\n" + "=".repeat(50));
        System.out.println("    DOCTOR CHANNELLING SYSTEM INITIALIZATION");
        System.out.println("=".repeat(50));
        
        loadPatientsFromCSV(patientStack, "data/patients.csv");
        loadDoctorsFromCSV(doctorList, "data/doctors.csv");
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         SYSTEM READY");
        System.out.println("=".repeat(50));
        System.out.println("✓ Patients Loaded: " + patientStack.size());
        System.out.println("✓ Doctors Loaded: " + doctorList.size());
        System.out.println("=".repeat(50) + "\n");

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
    // CSV DATA LOADING METHODS
    // =================================================
    
    // Create sample CSV files if they don't exist
    public static void createSampleCSVFiles() {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdir();
                System.out.println("✓ Created 'data' directory");
            }
            
            
            // Optional: Create appointments.csv
            File appointmentsFile = new File("data/appointments.csv");
            if (!appointmentsFile.exists()) {
                PrintWriter writer = new PrintWriter(appointmentsFile);
                writer.println("patient_name,doctor_name,time_slot");
                writer.close();
                System.out.println("✓ Created empty appointments.csv file");
            }
            
        } catch (Exception e) {
            System.out.println("Error creating sample CSV files: " + e.getMessage());
        }
    }
    
    // Load patients from CSV file
    public static void loadPatientsFromCSV(PatientStack patientStack, String filename) {
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            
            int count = 0;
            boolean isFirstLine = true;
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 8) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String name = parts[2].trim();
                    int age = Integer.parseInt(parts[3].trim());
                    String mobile = parts[4].trim();
                    String email = parts[5].trim();
                    String city = parts[6].trim();
                    String history = parts[7].trim();
                    
                    Patient patient = new Patient(username, password, name, age, mobile, email, city, history);
                    patientStack.push(patient);
                    count++;
                }
            }
            fileScanner.close();
            System.out.println("✓ Loaded " + count + " patients from " + filename);
            
        } catch (FileNotFoundException e) {
            System.out.println("⚠ Patient CSV file not found: " + filename);
        } catch (Exception e) {
            System.out.println("❌ Error reading patient CSV file: " + e.getMessage());
        }
    }
    
    // Load doctors from CSV file
    public static void loadDoctorsFromCSV(DoctorList doctorList, String filename) {
        try {
            File file = new File(filename);
            Scanner fileScanner = new Scanner(file);
            
            int count = 0;
            boolean isFirstLine = true;
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                if (line.isEmpty()) {
                    continue;
                }
                
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                String[] parts = line.split(",");
                
                if (parts.length >= 4) {
                    String name = parts[0].trim();
                    String specialization = parts[1].trim();
                    double fee = Double.parseDouble(parts[2].trim());
                    String timeSlot = parts[3].trim();
                    
                    Doctor doctor = new Doctor(name, specialization, fee, timeSlot);
                    doctorList.addDoctor(doctor);
                    count++;
                }
            }
            fileScanner.close();
            System.out.println("✓ Loaded " + count + " doctors from " + filename);
            
        } catch (FileNotFoundException e) {
            System.out.println("⚠ Doctor CSV file not found: " + filename);
        } catch (Exception e) {
            System.out.println("❌ Error reading doctor CSV file: " + e.getMessage());
        }
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

                // LOGIN
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
            System.out.println("4. Reschedule Appointment");
            System.out.println("5. View My Appointments");
            System.out.println("6. View Medical History");
            System.out.println("7. View Appointment History");
            System.out.println("8. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: searchDoctorOnly(sc, doctorList, searchService); break;
                case 2: handleBooking(sc, patientStack, doctorList,
                        appointmentQueue, waitingQueue, searchService, notify); break;
                case 3: handleCancellation(sc, appointmentQueue, waitingQueue, historyStack, notify); break;
                case 4: handleReschedule(sc, appointmentQueue, waitingQueue, notify); break;
                case 5: viewMyAppointments(sc, appointmentQueue); break;
                case 6: viewMedicalHistory(sc, patientStack); break;
                case 7: viewMyAppointmentHistory(sc, historyStack); break;
                case 8: run = false; break;
                default: System.out.println("Invalid option");
            }
        }
    }

    // =================================================
    // PATIENT LOGIN VALIDATION
    public static Patient validatePatientLogin(PatientStack patientStack, String username, String password) {

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
        System.out.println("\n--- My Current Appointments ---");

        for (int i = 0; i < appointmentQueue.getSize(); i++) {
            Appointment appt = appointmentQueue.getAtIndex(i);
            if (appt.getPatient().getName().equalsIgnoreCase(name)) {
                System.out.println("Appointment #" + (i+1) + ": " + appt);
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
        int count = 1;
        for (Appointment appt : arr) {
            if (appt != null && appt.getPatient().getName().equalsIgnoreCase(name)) {
                System.out.println("History #" + count + ": " + appt);
                found = true;
                count++;
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
        System.out.print("Time Slot: "); String timeSlot = sc.nextLine();
        Doctor d = new Doctor(name, spec, fee, timeSlot);
        doctorList.addDoctor(d);
        notify.notifyDoctorAdded(d);
    }

    public static void handleBooking(Scanner sc, PatientStack patientStack, DoctorList doctorList, AppointmentQueue appointmentQueue, WaitingQueue waitingQueue,
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

    public static void handleReschedule(Scanner sc, AppointmentQueue appointmentQueue, WaitingQueue waitingQueue, NotificationService notify) {
        System.out.print("Enter Patient Name: ");
        String name = sc.nextLine();
    
        Appointment removed = appointmentQueue.removeAndReturn(name);
        if (removed != null) {
            waitingQueue.enqueue(removed.getPatient());
            notify.warning("Your current appointment has been removed. You are now in the waiting queue for a new slot.");
        } else {
            notify.error("No active appointment found to reschedule.");
        }
    }

    public static void handleCancellation(Scanner sc, AppointmentQueue appointmentQueue, WaitingQueue waitingQueue, AppointmentHistoryStack historyStack, NotificationService notify) {
        System.out.print("Patient Name: ");
        String name = sc.nextLine();
    
        Appointment removed = appointmentQueue.removeAndReturn(name);
    
        if (removed != null) {
            historyStack.push(removed);
            notify.success("Appointment cancelled and saved in history.");
        
            // AUTOMATIC ASSIGNMENT: Check waiting list
            if (!waitingQueue.isEmpty()) {
                Patient nextPatient = waitingQueue.dequeue();
                // Assign the next patient to the same doctor and slot
                Appointment newAppt = new Appointment(nextPatient, removed.getDoctor(), removed.getTimeSlot());
                appointmentQueue.enqueue(newAppt);
            
                System.out.println("\n[SYSTEM] Slot automatically filled!");
                notify.notifyAppointmentCreated(newAppt);
            }
        } else {
            notify.error("No appointment found.");
        }
    }

    public static void cancelWithHistory(Scanner sc,
                                        AppointmentQueue queue,
                                        AppointmentHistoryStack historyStack,
                                        NotificationService notify) {
        System.out.print("Patient Name: "); 
        String name = sc.nextLine();
    
        // Get all appointments for this patient
        Appointment[] patientAppointments = queue.getAppointmentsForPatient(name);
    
        if (patientAppointments.length == 0) {
            notify.error("No appointments found for " + name);
            return;
        }
    
        // Display appointments with numbers
        System.out.println("\n--- Patient's Appointments ---");
        for (int i = 0; i < patientAppointments.length; i++) {
            System.out.println((i + 1) + ". " + patientAppointments[i]);
        }
    
        System.out.print("\nEnter appointment number to cancel (or 0 to cancel): ");
        int appointmentNum = sc.nextInt();
        sc.nextLine();
    
        if (appointmentNum == 0) {
            notify.info("Cancellation cancelled.");  // This will work after adding info() method
            return;
        }
    
        if (appointmentNum < 1 || appointmentNum > patientAppointments.length) {
            notify.error("Invalid appointment number!");
            return;
        }
    
        // Get the specific appointment to cancel
        Appointment toCancel = patientAppointments[appointmentNum - 1];
    
        // Cancel and add to history
        if (queue.cancelSpecificAppointment(toCancel)) {
            historyStack.push(toCancel);
            notify.success("Appointment cancelled and saved to history.");
        } else {
            notify.error("Cancellation failed!");
        }
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
