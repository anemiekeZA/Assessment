package dataBase;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class employeeInfo {
    public static void main(String[] args) {
 
        String jdbcUrl ="jdbc:sqlite:src/main/resources/employeeDataBase.db";

        String filePath="src/main/resources/Employee File.csv";

        int batchSize = 20;

        Connection connection = null;

        try{
            connection = DriverManager.getConnection(jdbcUrl);
            connection.setAutoCommit(false);

            String createTable= "CREATE TABLE IF NOT EXISTS \"EmployeeInfo\" ( " +
                    "\"employeeguid\"TEXT NOT NULL," +
                    "\"firstname\" TEXT, \"lastname\" TEXT, " +
                    "\"gender\" TEXT, \"email\" TEXT, \"age\"" +
                    " INTEGER, \"birthday\" TEXT, \"active\" TEXT," +
                    " \"street\" TEXT, \"postal\" TEXT, \"province\" TEXT," +
                    " \"city\" TEXT, \"longitude\" REAL, \"latitude\" REAL , " +
                    "PRIMARY KEY(\"employeeguid\"))";

            String packageTable = "CREATE TABLE IF NOT EXISTS \"Packages\" (" +
                    "\"employeeguid\"TEXT NOT NULL," +"\"package\" TEXT," +
                    " \"vendor\" TEXT, \"phone\" TEXT, \"packagecost\" TEXT, \"contractstart\" TEXT," +
                    " \"contractend\" TEXT," +
                    "PRIMARY KEY(\"package\"), FOREIGN KEY(\"employeeguid\") REFERENCES EmployeeInfo(employeeguid))";

            // INSERT INTO EmployeeInfo(employeeguid,firstName,lastName,gender,email,age,birthday,active,street,postal,province,city,longitude,latitude) SELECT {...columns} WHERE NOT EXISTS(SELECT 1 FROM EmployeeInfo WHERE employeeguid = {currentGuid})
            String insertEmployeeData = "INSERT INTO EmployeeInfo(employeeguid,firstName,lastName,gender,email,age,birthday,active,street,postal,province,city,longitude,latitude)" +
                    "SELECT ?,?,?,?,?,?,?,?,?,?,?,?,?,? " +
                    "WHERE NOT EXISTS(SELECT 1 FROM EmployeeInfo WHERE employeeguid = ?)";

            String insertPackageData = "INSERT INTO Packages(employeeguid,package,vendor,phone,packageCost,contractStart,contractEnd) values (?,?,?,?,?,?,?)";

            connection.createStatement().execute(createTable);
            connection.createStatement().execute(packageTable);

            PreparedStatement insertEmployeesStatement = connection.prepareStatement(insertEmployeeData);
            PreparedStatement insertPackagesStatement = connection.prepareStatement(insertPackageData);

            BufferedReader lineReader = new BufferedReader(new FileReader(filePath));

            String lineText = null;
            int employeeBatchCount = 0;
            int packageBatchCount = 0;

            lineReader.readLine();

            while((lineText = lineReader.readLine()) != null){
                employeeBatchCount++;
                packageBatchCount++;

                String[] data = lineText.split(",");

                String employeeguid =data[0];
                String firstName =data[1];
                String lastName=data[2];
                String gender=data[3];
                String email=data[4];
                String age=data[5];
                String birthday=data[6];
                String active=data[7];
                String street=data[8];
                String postal=data[9];
                String province=data[10];
                String city=data[11];
                String longitude=data[12];
                String latitude=data[13];

                String packageName=data[14];
                String vendor=data[15];
                String phone=data[16];
                String packageCost=data[17];
                String contractStart=data[18];
                String contractEnd=data[19];

                // add employee info
                insertEmployeesStatement.setString(1,employeeguid);
                insertEmployeesStatement.setString(2,firstName);
                insertEmployeesStatement.setString(3,lastName);
                insertEmployeesStatement.setString(4,gender);
                insertEmployeesStatement.setString(5,email);
                insertEmployeesStatement.setString(6,age);
                insertEmployeesStatement.setString(7,birthday);
                insertEmployeesStatement.setString(8,active);
                insertEmployeesStatement.setString(9,street);
                insertEmployeesStatement.setString(10,postal);
                insertEmployeesStatement.setString(11,province);
                insertEmployeesStatement.setString(12,city);
                insertEmployeesStatement.setString(13,longitude);
                insertEmployeesStatement.setString(14,latitude);
                insertEmployeesStatement.setString(15,employeeguid);

                // add package info
                insertPackagesStatement.setString(1,employeeguid);
                insertPackagesStatement.setString(2,packageName);
                insertPackagesStatement.setString(3,vendor);
                insertPackagesStatement.setString(4,phone);
                insertPackagesStatement.setString(5,packageCost);
                insertPackagesStatement.setString(6,contractStart);
                insertPackagesStatement.setString(7,contractEnd);

                insertEmployeesStatement.addBatch();
                insertPackagesStatement.addBatch();

                if(employeeBatchCount % batchSize == 0){
                    insertEmployeesStatement.executeBatch();
                }

                if(packageBatchCount % batchSize != 0){
                    insertPackagesStatement.executeBatch();
                }
            }

            // execute partial batch
            if(employeeBatchCount % batchSize != 0){
                insertEmployeesStatement.executeBatch();
            }

            // execute partial batch
            if(packageBatchCount % batchSize != 0){
                insertPackagesStatement.executeBatch();
            }



            lineReader.close();
            connection.commit();
            connection.close();
            System.out.println("Your data has been added successfully");

        } catch(Exception exception){
            exception.printStackTrace();
        }

    }

}
