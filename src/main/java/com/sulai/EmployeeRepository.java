package com.sulai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
  private Connection connection;

  public EmployeeRepository(Connection connection) {
    this.connection = connection;
  }

  public void add(Employee employee) throws SQLException {
    String sql = "INSERT INTO employees (id, name, salary) VALUES (?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, employee.getId());
    statement.setString(2, employee.getName());
    statement.setFloat(3, employee.getSalary());
    statement.executeUpdate();
  }

  public void update(Employee employee) throws SQLException {
    String sql = "UPDATE employees SET name = ?, salary = ? WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setString(1, employee.getName());
    statement.setFloat(2, employee.getSalary());
    statement.setInt(3, employee.getId());
    statement.executeUpdate();
  }

  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM employees WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, id);
    statement.executeUpdate();
  }

  public List<Employee> getAll() throws SQLException {
    String sql = "SELECT id, name, salary FROM employees";
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);
    List<Employee> employees = new ArrayList<>();
    while (result.next()) {
      int id = result.getInt("id");
      String name = result.getString("name");
      float salary = result.getFloat("salary");
      employees.add(new Employee(id, name, salary));
    }
    return employees;
  }

  public Employee getById(int id) throws SQLException {
    String sql = "SELECT id, name, salary FROM employees WHERE id = ?";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setInt(1, id);
    ResultSet result = statement.executeQuery();
    if (result.next()) {
      String name = result.getString("name");
      float salary = result.getFloat("salary");
      return new Employee(id, name, salary);
    } else {
      return null;
    }
  }
}