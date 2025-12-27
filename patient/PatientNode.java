package patient;

public class PatientNode {

    private Patient data;     // Stores the patient object
    private PatientNode next; // Pointer to the next node in the stack

    public PatientNode(Patient data) {
        this.data = data;
        this.next = null;
    }

    // Getter for patient data
    public Patient getData() {
        return data;
    }

    // Setter for patient data
    public void setData(Patient data) {
        this.data = data;
    }

    // Getter for next node
    public PatientNode getNext() {
        return next;
    }

    // Setter for next node
    public void setNext(PatientNode next) {
        this.next = next;
    }
}
