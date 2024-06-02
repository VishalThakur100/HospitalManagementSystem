package HospitalManagementSystem;

import javax.print.Doc;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hospital";
    private static final String username="root";
    private static final String password="Vish@l$$003";

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner=new Scanner(System.in);
        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Patient patient=new Patient(connection,scanner);
            Doctors doctors=new Doctors(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.print("Enter Your Choice:");
                int choice=scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctors.viesDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //
                        bookAppointment(patient,doctors,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("Thankyou for Using Hospital Management System. Hope you have a good Day😊.");
                        return;
                    default:
                        System.out.println("Please Enter Valid Choice");
                        System.out.println();
                        break;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctors doctors,Connection connection,Scanner scanner){
        System.out.println("Enter Patient Id:");
        int patientId=scanner.nextInt();
        System.out.println("Enter Doctor Id:");
        int doctorId=scanner.nextInt();
        System.out.println("Enter Appointment Date (yyyy-mm-dd):");
        String appointmentDate=scanner.nextLine();
        if (patient.getPatientById(patientId)&& doctors.getDoctorById(doctorId)){
            if (checkDoctorAvailabiblity(doctorId,appointmentDate,connection)){
                String appointmentquery="insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
                try{
                    PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected=preparedStatement.executeUpdate();
                    if (rowsAffected>0){
                        System.out.println("Appointment Booked");
                    }else {
                        System.out.println("Failed to Book Appointment");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("Doctor not Available on this Date!!!");
            }
        }else {
            System.out.println("Either patient or doctor not exists!!!");
        }
    }

    public static boolean checkDoctorAvailabiblity(int doctorid,String appointmentdate,Connection connection){
        String query="select count(*) from appointments where doctor_id=? and appointment_date=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorid);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.next()){
                int count=resultSet.getInt(1);
                if (count==0){
                    return true;
                }
                else {
                    return false;
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
