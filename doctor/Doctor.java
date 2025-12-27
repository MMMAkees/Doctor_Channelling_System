package doctor;

public class Doctor {

    private String doctorName;
    private String specialization;
    private double consultationFee;

    public Doctor(String doctorName, String specialization, double consultationFee) {
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    @Override
    public String toString() {
        return "Doctor Name: " + doctorName +
               ", Specialization: " + specialization +
               ", Fee: Rs." + consultationFee;
    }
}
