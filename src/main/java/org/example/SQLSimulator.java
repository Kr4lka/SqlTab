package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SQLSimulator {
    private List<String> tableColumns;
    private List<List<String>> tableData;
    private Scanner scanner = new Scanner(System.in);

    public SQLSimulator() {
        tableColumns = new ArrayList<>();
        tableData = new ArrayList<>();

        while (true) {
            System.out.print("Введите команду SQL: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Программа завершена.");
                break;
            }

            executeCommand(command);
        }
    }

    private void executeCommand(String command) {
        String[] parts = command.split(" ");

        if (parts[0].equalsIgnoreCase("CREATE")) {
            createTable(parts);
        } else if (parts[0].equalsIgnoreCase("SELECT")) {
            if (parts.length > 1 && parts[1].equalsIgnoreCase("*")) {
                selectTable();
            } else {
                selectRow(Integer.parseInt(parts[1]));
            }
        } else if (parts[0].equalsIgnoreCase("UPDATE")) {
            updateRow(parts);
        } else if (parts[0].equalsIgnoreCase("DELETE")) {
            deleteRow(Integer.parseInt(parts[1]));
        } else {
            System.out.println("Неверная команда SQL.");
        }
    }

    private void createTable(String[] parts) {
        tableColumns.clear();
        tableData.clear();

        for (int i = 2; i < parts.length; i++) {
            tableColumns.add(parts[i]);
        }

        System.out.print("Введите количество строк: ");
        Scanner scanner = new Scanner(System.in);
        int rowCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < rowCount; i++) {
            System.out.printf("Введите значения для строки %d: ", i + 1);
            String rowData = scanner.nextLine();
            String[] rowValues = rowData.split(",");

            List<String> row = new ArrayList<>();
            for (String value : rowValues) {
                row.add(value.trim());
            }

            tableData.add(row);
        }

        System.out.println("Таблица создана успешно.");
    }

    private void selectTable() {
        if (tableData.isEmpty()) {
            System.out.println("Таблица пуста.");
            return;
        }

        printTableHeader();

        for (List<String> row : tableData) {
            printTableRow(row);
        }
    }

    private void selectRow(int rowIndex) {
        if (rowIndex <= 0 || rowIndex > tableData.size()) {
            System.out.println("Недопустимый индекс строки.");
            return;
        }

        printTableHeader();
        printTableRow(tableData.get(rowIndex -1));
    }

    private void updateRow(String[] parts) {
        int rowIndex = Integer.parseInt(parts[1]);

        if (rowIndex <= 0 || rowIndex > tableData.size()) {
            System.out.println("Недопустимый индекс строки.");
            return;
        }

        List<String> row = tableData.get(rowIndex - 1);

        for (int i = 2; i < parts.length; i += 2) {
            int columnIndex = tableColumns.indexOf(parts[i]);

            if (columnIndex == -1) {
                System.out.println("Недопустимое имя столбца.");
                return;
            }

            row.set(columnIndex, parts[i + 1]);
        }

        System.out.println("Строка успешно обновлена.");
    }

    private void deleteRow(int rowIndex) {
        if (rowIndex <= 0 || rowIndex > tableData.size()) {
            System.out.println("Недопустимый индекс строки.");
            return;
        }

        tableData.remove(rowIndex - 1);
        System.out.println("Строка успешно удалена.");
    }

    private void printTableHeader() {
        for (String column : tableColumns) {
            System.out.print("+" + column + "+\t");
        }
        System.out.println();
    }

    private void printTableRow(List<String> row) {
        for (String value : row) {
            System.out.print("|" + value + "|\t");
        }
        System.out.println();
    }
}

