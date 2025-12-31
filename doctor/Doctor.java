package doctor;

public class Doctor {

    private String doctorName;
    private String specialization;
    private double consultationFee;
    private String timeSlot;

    public Doctor(String doctorName, String specialization, double consultationFee, String timeSlot) {
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.timeSlot = timeSlot;  
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
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

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return "Doctor Name: " + doctorName +
               ", Specialization: " + specialization +
               ", Fee: Rs." + consultationFee +
               ", Time Slot: " + timeSlot;
    }
}