package main.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import main.model.Employee;
import main.repository.EmployeeRepository;

import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;

public class EmployeeService {

    private final EmployeeRepository repository = new EmployeeRepository();

    public List<Employee> getAll() throws SQLException {
        return repository.getAll();
    }

    public Employee getById(Integer id) throws SQLException {
        return repository.getById(id);
    }

    public boolean deleteById(Integer id) throws SQLException {
        Employee temp = getById(id);
        return repository.delete(temp);
    }

    public boolean updateEmployeeFromFile(String path) {
        try {
            List<Employee> listInDB = repository.getAll();
            List<Employee> listFromFile = getEmployeeFromCSV(path);
            Map<String, Employee> mapFromDB = mapFromList(listInDB);
            Map<String, Employee> mapFromFile = mapFromList(listFromFile);

            for (Employee employee : listInDB) {
                if (mapFromFile.get(employee.getTabNum()) == null) {
                    employee.setActive(false);
                    repository.update(employee);
                }
            }
            for (Employee employee : listFromFile) {
                if (mapFromDB.get(employee.getTabNum()) == null) {
                    employee.setActive(true);
                    repository.add(employee);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private List<Employee> getEmployeeFromCSV(String path) {
        List<Employee> result = new ArrayList<>();
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path)).withCSVParser(csvParser).withSkipLines(1).build()) {
            List<String[]> lines = csvReader.readAll();
            lines.forEach(x -> {
                Employee tempEmployee = new Employee(x[0], x[1], x[2]);
                tempEmployee.setActive(true);
                result.add(tempEmployee);
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    private Map<String, Employee> mapFromList(List<Employee> employees) {
        Map<String, Employee> result = new HashMap<>();
        for (Employee employee : employees) {
            result.put(employee.getTabNum(), employee);
        }
        return result;
    }

}
