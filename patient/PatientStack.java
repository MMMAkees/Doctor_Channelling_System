package patient;

public class PatientStack {

    private PatientNode top;

    public PatientStack() {
        this.top = null;
    }

    // Push a patient onto the stack
    public void push(Patient patient) {

        PatientNode newNode = new PatientNode(patient);
        newNode.setNext(top);
        top = newNode;
            
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
    // Display patients from newest → oldest in a table format
    public void displayNewestToOldest() {
        if (isEmpty()) {
            System.out.println("No patient records available.");
            return;
        }

        int count = size();
        patient.Patient[] patientArray = new patient.Patient[count]; 
    
        patient.PatientNode temp = top;
        int index = 0;
        while (temp != null) {
            patientArray[index++] = temp.getData();
            temp = temp.getNext();
        }

        System.out.println("\n--- Registered Patient List (Newest to Oldest) ---");
        System.out.println("+-----+----------------------+-----+-------------------------+");
        System.out.println("| No. | Name                 | Age | Email                   |");
        System.out.println("+-----+----------------------+-----+-------------------------+");

        for (int i = 0; i < patientArray.length; i++) {
            patient.Patient p = patientArray[i];
            System.out.printf("| %-3d | %-20s | %-3d | %-23s |\n",
                (i + 1), p.getName(), p.getAge(), p.getEmail());
        }
        System.out.println("+-----+----------------------+-----+-------------------------+");
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
