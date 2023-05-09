import java.util.Scanner;
import java.io.*;
import java.util.*;
public class Main {

    public static void FirstTask(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до вхідного файлу: ");
        String inputFilePath = scanner.nextLine();

        File inputFile = new File(inputFilePath);
        while (!inputFile.exists()) {
            System.out.println("Файл не знайдено. Введіть шлях до існуючого файлу: ");
            inputFilePath = scanner.nextLine();
            inputFile = new File(inputFilePath);
        }

        List<Double> numbers = readNumbersFromFile(inputFile);
        Collections.sort(numbers); // Сортуємо числа за зростанням

        int totalNumbers = numbers.size();
        int sumCount = Math.min(10, totalNumbers); // Кількість чисел для підрахунку суми

        double sum = 0;
        for (int i = totalNumbers - 1; i >= totalNumbers - sumCount; i--) {
            sum += numbers.get(i);
        }

        System.out.print("Введіть шлях до вихідного файлу: ");
        String outputFilePath = scanner.nextLine();
        File outputFile = new File(outputFilePath);
        createOutputFile(outputFile);

        writeNumbersToFile(numbers, outputFile); // Записуємо відсортовані числа в файл
        writeSumToFile(sum, sumCount, numbers, outputFile); // Записуємо суму та числа, з яких вона складається

        System.out.println("Операція завершена успішно.");
    }
    private static List<Double> readNumbersFromFile(File inputFile) {
        List<Double> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                double number = Double.parseDouble(line);
                numbers.add(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numbers;
    }

    private static void createOutputFile(File outputFile) {
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
                System.out.println("Створено новий файл: " + outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Файл існує: " + outputFile.getAbsolutePath());
            System.out.print("Оберіть дію (1 - перезаписати, 2 - дописати в кінець): ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1) {
                try (PrintWriter writer = new PrintWriter(outputFile)) {
                    writer.write(""); // Очищаємо файл для перезапису
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void writeNumbersToFile(List<Double> numbers, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write("Відсортовані числа:");
            writer.newLine();
            for (Double number : numbers) {
                writer.write(number.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeSumToFile(double sum, int sumCount, List<Double> numbers, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.newLine();
            writer.write("Сума найбільших " + sumCount + " чисел: " + sum);
            writer.newLine();
            writer.write("Числа, з яких складається сума:");
            writer.newLine();
            for (int i = numbers.size() - 1; i >= numbers.size() - sumCount; i--) {
                writer.write(numbers.get(i).toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void SecondTask(){
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        String inputFilePath = getInputFilePath(consoleReader);
        String outputFilePath = getOutputFilePath(consoleReader);

        File inputFile = new File(inputFilePath);
        File outputFile = new File(outputFilePath);

        while (!inputFile.exists()) {
            System.out.println("Файл не знайдено. Введіть шлях до існуючого файлу: ");
            inputFilePath = getInputFilePath(consoleReader);
            inputFile = new File(inputFilePath);
        }

        processFile(inputFile, outputFile);

        System.out.println("Операція завершена успішно.");
    }

    private static String getInputFilePath(BufferedReader consoleReader) {
        System.out.print("Введіть шлях до вхідного файлу: ");
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getOutputFilePath(BufferedReader consoleReader) {
        System.out.print("Введіть шлях до вихідного файлу: ");
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void processFile(File inputFile, File outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Видаляємо зайві пробіли з початку та кінця рядка
                String[] words = line.split("\\s+"); // Розбиваємо рядок на слова за допомогою регулярного виразу "\\s+" (один або більше пропусків)

                for (String word : words) {
                    if (!word.isEmpty()) { // Ігноруємо порожні слова
                        writer.write(word);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println(" Lab4 Java ");

        char answer;

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. First task");
            System.out.println("2. Second task");
            System.out.println("E. Exit\n");
            System.out.print("Choose an option:");

            answer = scanner.nextLine().charAt(0);

            switch (answer) {
                case '1': FirstTask();
                    break;
                case '2': SecondTask();
                    break;
                case 'e':
                case 'E':
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (answer != 'e' && answer != 'E');
    }
}
