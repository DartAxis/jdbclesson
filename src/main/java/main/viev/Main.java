package main.viev;

import main.model.Employee;
import main.service.EmployeeService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static EmployeeService service = new EmployeeService();
    private static String path = "C:\\Users\\user\\IdeaProjects\\jdbclesson\\src\\main\\resources\\employees.csv";


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equals("4")) {
            System.out.println("1) Показать список всех сотрудников в базе");
            System.out.println("2) Обновить базу из файла");
            System.out.println("3) Удалить сотрудника");
            System.out.println("4) Выход");
            command = scanner.nextLine();
            switch (command) {
                case "1":
                    showAllEmployees();
                    break;
                case "2":
                    updateEmployeeFromCSV();
                    break;
                case "3":
                    deleteEmployee();
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    System.err.println("Команда не распознана!!!!!!!");
                    break;

            }
        }
    }

    private static void showAllEmployees() throws SQLException {
        List<Employee> result = service.getAll();
        if (!result.isEmpty()) {
            result.forEach(System.out::println);
        }
    }

    private static void updateEmployeeFromCSV() throws SQLException {
        if (service.updateEmployeeFromFile(path)) {
            showAllEmployees();
        } else {
            System.err.println("Что-то пошло не так!!!");
        }

    }

    private static void deleteEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите id сотрудника:");
        service.deleteById(scanner.nextInt());
        showAllEmployees();
    }
}
