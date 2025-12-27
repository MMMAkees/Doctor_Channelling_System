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

    // Cancel appointment by patient name
    public boolean cancelAppointment(String patientName) {
        Appointment removed = cancelAppointmentAndReturn(patientName);
        return removed != null;
    }

    // Cancel and return removed appointment
    public Appointment cancelAppointmentAndReturn(String patientName) {
        if (isEmpty()) return null;

        Appointment removed = null;
        Appointment[] newQueue = new Appointment[capacity];
        int newRear = -1;
        int newSize = 0;

        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            Appointment appt = queue[index];

            if (appt.getPatient().getName().equalsIgnoreCase(patientName) && removed == null) {
                removed = appt; // remove first match
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

    // Alias method
    public Appointment removeAndReturn(String patientName) {
        return cancelAppointmentAndReturn(patientName);
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

    // Check if empty
    public boolean isEmpty() { return size == 0; }

    // Check if full
    public boolean isFull() { return size == capacity; }

    // Get current size
    public int getSize() { return size; }

    // Get appointment at specific index (for viewing without removing)
    public Appointment getAtIndex(int index) {
        if (index < 0 || index >= size) return null;
        return queue[(front + index) % capacity];
    }
}
