package patient;

public class PatientStack {

    private PatientNode top;   // Top of the stack

    public PatientStack() {
        this.top = null;
    }

    // Push a patient onto the stack
    public void push(Patient patient) {

        PatientNode newNode = new PatientNode(patient);
        newNode.setNext(top);      // Link new node to previous top
        top = newNode;             // New node becomes top
    }

    // Pop the newest patient from the stack
    public Patient pop() {

        if (isEmpty()) {
            System.out.println("Stack Underflow! No patient to remove.");
            return null;
        }

        Patient removed = top.getData();
        top = top.getNext();
        return removed;
    }

    // View the newest patient without removing
    public Patient peek() {

        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return null;
        }

        return top.getData();
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Display patients from newest → oldest
    public void displayNewestToOldest() {

        if (isEmpty()) {
            System.out.println("No patient records available.");
            return;
        }

        PatientNode temp = top;

        System.out.println("Patient List (Newest → Oldest):");
        while (temp != null) {
            System.out.println(temp.getData());
            temp = temp.getNext();
        }
    }

    // Count total patients in the stack
    public int size() {
        int count = 0;
        PatientNode temp = top;

        while (temp != null) {
            count++;
            temp = temp.getNext();
        }

        return count;
    }
}
