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
            System.out.println("Appointment history is full!");
            return;
        }
        history[++top] = appointment;
    }

    // Peek latest appointment
    public Appointment peek() {
        if (isEmpty()) return null;
        return history[top];
    }

    // Display all appointment history (latest → oldest)
    public void displayHistory() {
        if (isEmpty()) {
            System.out.println("No appointment history available.");
            return;
        }

        System.out.println("\n--- Appointment History (Latest → Oldest) ---");
        for (int i = top; i >= 0; i--) {
            System.out.println(history[i]);
        }
    }

    // Check if empty
    public boolean isEmpty() {
        return top == -1;
    }

    // Convert to array for reading without modifying stack
    public Appointment[] toArray() {
        Appointment[] arr = new Appointment[top + 1];
        for (int i = 0; i <= top; i++) {
            arr[i] = history[i];
        }
        return arr;
    }

    // Optional: get current size
    public int size() {
        return top + 1;
    }
}
