package task1;
import java.sql.*;
import java.util.Scanner;
import java.util.Random;
public class Task1 {
    private static final int min=1000;
    private static final int max=9999;
    public static class User{
        private String username;
        private String password;
        Scanner sc=new Scanner(System.in);
        public User(){
            
        }
        public String getUsername(){
            System.out.println("Enter Username : ");
            username=sc.nextLine();
            return username;
        }
        public String getPassword(){
            System.out.println("Enter Password : ");
            password=sc.nextLine();
            return password;
        }
    }
    public static class PnrRecord{
        private int pnrNumber;
        private String passengerName;
        private String trainNumber;
        private String classType;
        private String journeyDate; 
        private String from;
        private String to;
        Scanner sc=new Scanner(System.in);
        public int getpnrNumber(){
            Random random=new Random();
            pnrNumber=random.nextInt((max - min) + 1)+min;
            return pnrNumber;
        }
        public String getPassengerName(){
            System.out.println("Enter the Passenger name : ");
            passengerName=sc.nextLine();
            return passengerName;
        }
        public String gettrainNumber(){
            System.out.println("Enter the train number : ");
            trainNumber=sc.nextLine();
            return trainNumber;
        }
        public String getclassType(){
            System.out.println("Enter the class type : ");
            classType=sc.nextLine();
            return classType;
        }
        public String getjourneyDate(){
            System.out.println("Enter the journey date as 'YYYY-MM-DD' format : ");
            journeyDate=sc.nextLine();
            return journeyDate;
        }
        public String getfrom(){
            System.out.println("Enter the starting place : ");
            from=sc.nextLine();
            return from;
        }
        public String getto(){
            System.out.println("Enter the destination place : ");
            to=sc.nextLine();
            return to;
        }
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        User u1=new User();
        String username=u1.getUsername();
        String password=u1.getPassword();
        String url="jdbc:mysql://localhost:3306/Geetha";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            try(Connection connection=DriverManager.getConnection(url,username,password)){
                System.out.println("User Connection Granted.\n");
                while(true){
                    String InsertQuery = "insert into reservations values(?,?,?,?,?,?,?)";
                    String DeleteQuery="DELETE FROM reservations WHERE pnr_number=?";
                    String ShowQuery="select *from reservations";
                    System.out.println("Enter the Choice.\n");
                    System.out.println("1.Insert Record.\n");
                    System.out.println("2.Delete Record.\n");
                    System.out.println("3.Show All Records.\n");
                    System.out.println("4.Exit.\n");
                    int choice=sc.nextInt();
                    if(choice==1){
                        PnrRecord p1=new PnrRecord();
                        int pnr_number=p1.getpnrNumber();
                        String passengerName = p1.getPassengerName();
                        String trainNumber=p1.gettrainNumber();
                        String classType=p1.getclassType();
                        String journeyDate=p1.getjourneyDate();
                        String getfrom=p1.getfrom();
                        String getto=p1.getto();
                        try(PreparedStatement preparedStatement=connection.prepareStatement(InsertQuery)){
                            preparedStatement.setInt(1,pnr_number);
                            preparedStatement.setString(2,passengerName);
                            preparedStatement.setString(3,trainNumber);
                            preparedStatement.setString(4,classType);
                            preparedStatement.setString(5,journeyDate);
                            preparedStatement.setString(6,getfrom);
                            preparedStatement.setString(7,getto);
                            int rowsAffected=preparedStatement.executeUpdate();
                            if(rowsAffected>0){
                                System.out.println("Records added Successfully");
                            }
                            else{
                                System.out.println("No Records Were Added");
                            }
                        }
                        catch(SQLException e){
                            System.out.println("SQLException : "+e.getMessage());
                        }
                    }
                    else if(choice==2){
                        System.out.println("Enter the PNR number to delete the Record");
                        int pnrNumber=sc.nextInt();
                        try(PreparedStatement preparedStatement = connection.prepareStatement(DeleteQuery)){
                            preparedStatement.setInt(1,pnrNumber);
                            int rowsAffected=preparedStatement.executeUpdate();
                            if(rowsAffected>0){
                                System.out.println("Record deleted Successfully");
                            }
                            else{
                                System.out.println("No Records Were Deleted");
                            }
                        }
                        catch(SQLException e){
                            System.out.println("SQLException : "+e.getMessage());
                        }
                    }
                    else if(choice==3){
                        try(PreparedStatement preparedStatement=connection.prepareStatement(ShowQuery);
                           ResultSet resultSet=preparedStatement.executeQuery()){
                            System.out.println("\n all records printing\n");
                            while(resultSet.next()){
                                String pnrNumber =resultSet.getString("pnr_number");
                                String passengerName =resultSet.getString("passenger_name");
                                String trainNumber =resultSet.getString("train_number");
                                String classType =resultSet.getString("class_type");
                                String journeyDate =resultSet.getString("journey_date");
                                String fromLocation =resultSet.getString("from_location");
                                String toLocation =resultSet.getString("to_location");
                                System.out.println("PNR Number : "+pnrNumber);
                                System.out.println("Passenger Name : "+passengerName);
                                System.out.println("Train Number : "+trainNumber);
                                System.out.println("Class Type : "+classType);
                                System.out.println("Journey Date : "+journeyDate);
                                System.out.println("From Location : "+fromLocation);
                                System.out.println("To Location : "+toLocation);
                                System.out.println("-------------------------");
                            }
                        }
                           catch(SQLException e){
                                System.err.println("SQLException : "+e.getMessage());   
                           }
                    }
                        else if(choice==4){
                                System.out.println("Exiting the Program.\n");
                                break;
                        }
                                else{
                                System.out.println("Invalid Choice Entered.\n");
                                }
                }
            }
                catch(SQLException e){
                        System.out.println("SQLException : "+e.getMessage());
                        }
                        
        }
            catch(ClassNotFoundException e){
                System.out.println("Error Loading JDBC Driver "+e.getMessage());
            }
            sc.close();
    } 
}
