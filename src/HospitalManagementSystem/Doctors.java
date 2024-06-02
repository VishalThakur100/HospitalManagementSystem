package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

    private Connection connection;

    public Doctors(Connection connection){
        this.connection=connection;
    }

    public void viesDoctors(){
        String query="select * from doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultset=preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+--------------+-----------------------+------------------+");
            System.out.println("| Doctor Id    | Doctor Name           | Specialization    |");
            System.out.println("+--------------+-----------------------+------------------+");
            while(resultset.next()){
                int id=resultset.getInt("id");
                String name=resultset.getString("name");
               String specialization=resultset.getString("specialization");
                System.out.printf("| %-12d | %-21s | %-16s |\n",id,name,specialization);
                System.out.println("+--------------+-----------------------+------------------+");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query="select * from doctors where id=?";
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
