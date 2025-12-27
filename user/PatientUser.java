package user;

import patient.PatientStack;

public class PatientUser extends User {

    private PatientStack patientStack;

    public PatientUser(String username, String password, PatientStack patientStack) {
        super(username, password);
        this.patientStack = patientStack;
    }

    public PatientStack getPatientStack() {
        return patientStack;
    }
}
