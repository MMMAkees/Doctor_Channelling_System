package appointment;

public class AppointmentQueue {

    private Appointment[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    // Constructor
    public AppointmentQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new Appointment[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    // Enqueue (book) appointment
    public boolean enqueue(Appointment appointment) {
        if (isFull()) {
            System.out.println("Appointment queue is full!");
            return false;
        }
        rear = (rear + 1) % capacity;
        queue[rear] = appointment;
        size++;
        return true;
    }

    // Dequeue (remove) appointment
    public Appointment dequeue() {
        if (isEmpty()) return null;

        Appointment removed = queue[front];
        front = (front + 1) % capacity;
        size--;
        return removed;
    }

    // Check if slot is booked for a doctor
    public boolean isBooked(String doctorName, String timeSlot) {
        if (isEmpty()) return false;

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment appt = queue[index];

            if (appt.getDoctor().getDoctorName().equalsIgnoreCase(doctorName) &&
                appt.getTimeSlot().equalsIgnoreCase(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    // Get all appointments for a specific patient (Used for specific cancellation/reschedule)
    public Appointment[] getAppointmentsForPatient(String patientName) {
        int count = 0;
        // First pass to count matches
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            if (queue[index].getPatient().getName().equalsIgnoreCase(patientName)) {
                count++;
            }
        }

        Appointment[] matches = new Appointment[count];
        int matchIdx = 0;
        // Second pass to fill the array
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            if (queue[index].getPatient().getName().equalsIgnoreCase(patientName)) {
                matches[matchIdx++] = queue[index];
            }
        }
        return matches;
    }

    // Cancel and return removed appointment by name
        public Appointment removeAndReturn(String patientName) {
        if (isEmpty()) return null;

        Appointment removed = null;
        Appointment[] newQueue = new Appointment[capacity];
        int newRear = -1;
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment appt = queue[index];

            if (appt.getPatient().getName().equalsIgnoreCase(patientName) && removed == null) {
                removed = appt; 
            } else {
                newQueue[++newRear] = appt;
                newSize++;
            }
        }

        if (removed != null) {
            this.queue = newQueue;
            this.front = 0;
            this.rear = newRear;
            this.size = newSize;
        }

        return removed;
    }

    // Cancel a specific appointment object (Useful for choosing from a list)
    public boolean cancelSpecificAppointment(Appointment toCancel) {
        if (isEmpty() || toCancel == null) return false;

        boolean found = false;
        Appointment[] newQueue = new Appointment[capacity];
        int newRear = -1;
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment appt = queue[index];

            if (appt == toCancel && !found) {
                found = true;
            } else {
                newQueue[++newRear] = appt;
                newSize++;
            }
        }

        if (found) {
            this.queue = newQueue;
            this.front = 0;
            this.rear = newRear;
            this.size = newSize;
        }

        return found;
    }

    // Display all appointments
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No appointments booked.");
            return;
        }

        System.out.println("\n--- Current Appointments ---");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            System.out.println(queue[index]);
        }
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size == capacity; }
    public int getSize() { return size; }

    public Appointment getAtIndex(int index) {
        if (index < 0 || index >= size) return null;
        return queue[(front + index) % capacity];
    }
}