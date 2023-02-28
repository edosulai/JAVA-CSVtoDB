package com.sulai;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, CsvException {
        Connection connection = MySqlConnection.getConnection();
        EmployeeRepository repository = new EmployeeRepository(connection);

        String csvFilePath = "datasets.csv";
        try {
            List<String[]> records = readCsvFile(csvFilePath);
            System.out.println(records.get(0));
            for (String[] record : records) {
                int id = Integer.parseInt(record[0]);
                String name = record[1];
                float salary = Float.parseFloat(record[2]);
                Employee employee = new Employee(id, name, salary);
                repository.add(employee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        connection.close();
    }

    public static List<String[]> readCsvFile(String filePath) throws IOException, CsvException {
        FileReader fileReader = new FileReader(filePath);
        CSVReader csvReader = new CSVReader(fileReader);
        csvReader.skip(1);
        List<String[]> records = csvReader.readAll();
        csvReader.close();
        fileReader.close();
        return records;
    }
}
