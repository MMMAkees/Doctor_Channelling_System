package appointment;

public class AppointmentHistoryStack {

    private Appointment[] history;
    private int top;
    private int capacity;

    // Constructor
    public AppointmentHistoryStack(int capacity) {
        this.capacity = capacity;
        this.history = new Appointment[capacity];
        this.top = -1;
    }

    // Push appointment to history
    public void push(Appointment appointment) {
        if (top == capacity - 1) {
            // If stack is full, shift elements down (remove oldest)
            for (int i = 0; i < top; i++) {
                history[i] = history[i + 1];
            }
            history[top] = appointment;
        } else {
            history[++top] = appointment;
        }
    }

    // NEW: Add multiple appointments to history
    public void pushAll(Appointment[] appointments) {
        if (appointments == null) return;
        
        for (Appointment appt : appointments) {
            if (appt != null) {
                push(appt);
            }
        }
    }

    // Peek latest appointment
    public Appointment peek() {
        if (isEmpty()) return null;
        return history[top];
    }

    // Pop latest appointment
    public Appointment pop() {
        if (isEmpty()) return null;
        return history[top--];
    }

    // Display all appointment history (latest → oldest)
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("No appointment history available.");
            return;
        }

        System.out.println("\n" + "=".repeat(80));
        System.out.println(" APPOINTMENT HISTORY (Latest → Oldest)");
        System.out.println("=".repeat(80));
        System.out.printf("%-5s %-20s %-20s %-20s %-15s\n", "No.", "Patient", "Doctor", "Specialization", "Time Slot");
        System.out.println("-".repeat(80));
        
        int count = 1;
        for (int i = top; i >= 0; i--) {
            Appointment appt = history[i];
            if (appt != null) {
                System.out.printf("%-5d %-20s %-20s %-20s %-15s\n", 
                                  count++,
                                  appt.getPatient().getName(),
                                  appt.getDoctor().getDoctorName(),
                                  appt.getDoctor().getSpecialization(),
                                  appt.getTimeSlot());
            }
        }
        System.out.println("=".repeat(80));
        System.out.println("Total History Records: " + (top + 1));
    }

    // NEW: Display history for specific patient
    public void displayHistoryForPatient(String patientName) {
        if (isEmpty()) {
            System.out.println("No appointment history available.");
            return;
        }

        boolean found = false;
        System.out.println("\n--- Appointment History for " + patientName + " ---");
        
        int count = 1;
        for (int i = top; i >= 0; i--) {
            Appointment appt = history[i];
            if (appt != null && appt.getPatient().getName().equalsIgnoreCase(patientName)) {
                System.out.println(count + ". " + appt);
                found = true;
                count++;
            }
        }
        
        if (!found) {
            System.out.println("No history found for patient: " + patientName);
        }
    }

    // Check if empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Get all appointments for a specific patient
    public Appointment[] getHistoryForPatient(String patientName) {
        Appointment[] result = new Appointment[top + 1];
        int count = 0;
        
        for (int i = 0; i <= top; i++) {
            Appointment appt = history[i];
            if (appt != null && appt.getPatient().getName().equalsIgnoreCase(patientName)) {
                result[count++] = appt;
            }
        }
        
        // Return only the filled portion
        Appointment[] finalResult = new Appointment[count];
        System.arraycopy(result, 0, finalResult, 0, count);
        return finalResult;
    }

    // Convert to array for reading without modifying stack
    public Appointment[] toArray() {
        Appointment[] arr = new Appointment[top + 1];
        for (int i = 0; i <= top; i++) {
            arr[i] = history[i];
        }
        return arr;
    }

    // Get current size
    public int size() {
        return top + 1;
    }

    // NEW: Clear history
    public void clear() {
        for (int i = 0; i <= top; i++) {
            history[i] = null;
        }
        top = -1;
    }
}