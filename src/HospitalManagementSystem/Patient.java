package HospitalManagementSystem;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;
    public Patient(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public void addPatient(){
        System.out.print("Enter Patient Name:");
        String name=scanner.next();

        System.out.print("Enter Patient age:");
        int age=scanner.nextInt();

        System.out.print("Enter Patient Gender:");
        String gender=scanner.next();

        try {
                String query="insert into patients(name,age,gender) values(?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows= preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("Patient added successfully!!");
            }
            else {
                System.out.println("Failed to add Patient!!");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query="select * from patients";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultset=preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+--------------+----------------+---------+------------+");
            System.out.println("| Patient Id   | Name           | Age     | Gender    |");
            System.out.println("+--------------+----------------+---------+------------+");
            while(resultset.next()){
                int id=resultset.getInt("id");
                String name=resultset.getString("name");
                int age=resultset.getInt("age");
                String gender=resultset.getString("gender");
                System.out.printf("| %-12d | %-14s | %-7d | %-8s  |\n",id,name,age,gender);
                System.out.println("+--------------+----------------+---------+------------+");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query="select * from patients where id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultset=preparedStatement.executeQuery();
            if (resultset.next()){
                return true;
            }
            else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
