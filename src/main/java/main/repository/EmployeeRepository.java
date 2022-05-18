package main.repository;

import main.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {
    static final String SQLURL = "jdbc:postgresql://192.168.0.140:5432/vpnrdpip";
    static final String user = "admindb";
    static final String password="Qwerty3366";
    private Connection connection;

    public EmployeeRepository() {
        try {
            this.connection = DriverManager.getConnection(SQLURL,user,password);
            System.err.println("Соединение с базой данных установлено!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getAll() throws SQLException {
        List<Employee> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from employees order by id");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            result.add(new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("surname"),resultSet.getString("tabnum"),resultSet.getBoolean("active")));
        }
        statement.close();
        return result;
    }

    public Employee getById(Integer id) throws SQLException {
        String request = "select * from employees where id="+id;
        ResultSet resultSet;
        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY)){
            System.out.println("Выполняем запрос:");
            System.out.println(request);
            resultSet = statement.executeQuery(request);
            resultSet.last();
            System.out.println();
            return new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("surname"),resultSet.getString("tabnum"),resultSet.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(Employee employee){
        String request = String.format("insert into employees(name,surname,tabnum,active) values('%s','%s','%s',%b);",employee.getName(),employee.getSurName(),employee.getTabNum(),employee.getActive());
        return executeBool(request);
    }

    public boolean update(Employee employee){
        String request = String.format("update employees set name='%s',surname='%s',tabnum='%s',active=%b where id=%d;",employee.getName(),employee.getSurName(),employee.getTabNum(),employee.getActive(),employee.getId());
        return executeBool(request);
    }

    public boolean delete(Employee employee){
        String request = String.format("delete from employees where id=%d",employee.getId());
        return executeBool(request);
    }

    private boolean executeBool(String request){
        try(Statement statement = connection.createStatement()) {
            System.out.println("Выполняем запрос:");
            System.out.println(request);
            statement.execute(request);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
