package com.sulai;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmployeeRepositoryTest {

    private Connection connection;
    private EmployeeRepository repository;
    private Employee employee;

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = MySqlConnection.getConnection();
        repository = new EmployeeRepository(connection);

        employee = new Employee(1, "John Doe", (float) 5000.0);
        repository.add(employee);
    }

    @After
    public void tearDown() throws SQLException {
        repository.delete(employee.getId());
        connection.close();
    }

    @Test
    public void testSave() throws SQLException {
        Employee savedEmployee = repository.getById(employee.getId());
        assertNotNull(savedEmployee);
        assertEquals(employee.getName(), savedEmployee.getName());
        assertEquals(employee.getSalary(), savedEmployee.getSalary(), 0.001);
    }

    @Test
    public void testGetById() throws SQLException {
        Employee foundEmployee = repository.getById(employee.getId());
        assertNotNull(foundEmployee);
        assertEquals(employee.getName(), foundEmployee.getName());
        assertEquals(employee.getSalary(), foundEmployee.getSalary(), 0.001);
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Employee> employees = repository.getAll();
        assertNotNull(employees);
        assertEquals(1, employees.size());
        assertEquals(employee.getName(), employees.get(0).getName());
        assertEquals(employee.getSalary(), employees.get(0).getSalary(), 0.001);
    }

    @Test
    public void testUpdate() throws SQLException {
        employee.setName("Jane Doe");
        employee.setSalary((float) 6000.0);
        repository.update(employee);
        Employee updatedEmployee = repository.getById(employee.getId());
        assertNotNull(updatedEmployee);
        assertEquals(employee.getName(), updatedEmployee.getName());
        assertEquals(employee.getSalary(), updatedEmployee.getSalary(), 0.001);
    }

    @Test
    public void testDelete() throws SQLException {
        repository.delete(employee.getId());
        Employee deletedEmployee = repository.getById(employee.getId());
        assertNull(deletedEmployee);
    }

}