import exceptions.NoDataException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;

public class MainClass {
    public static void main(String[] args) throws Exception{
        System.setProperty("log4j.logger.org.hibernate", "off");
        DataBaseService service = new DataBaseService();
        Scanner scanner = new Scanner(System.in);
        List<String> command;
        String temp = "";
        help();
        while (true) {
            temp = scanner.nextLine();
            command = Arrays.asList(temp.split(" "));
            switch (command.get(0)) {
                case "LIST" -> {
                    SessionFactory sessionFactory = DataBaseService.CreateSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();

                    List<Student> studentList = service.list(session);
                    studentList.forEach(System.out::println);
                    transaction.commit();
                    sessionFactory.close();
                    break;
                }
                case "ADD" -> {
                    SessionFactory sessionFactory = DataBaseService.CreateSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();

                    try {

                        if (command.size() != 6) throw new NoDataException("Данные введены неверно");
                        Student student = new Student();
                        student.setFirstName(command.get(2));
                        student.setLastName(command.get(1));
                        student.setPatronymic(command.get(3));
                        student.setBirthDate(command.get(4));
                        student.setGroupp(command.get(5));
                        service.add(student, session);

                    } catch (IndexOutOfBoundsException e) {
                        e.getMessage();
                    } catch (NoDataException e) {
                        System.out.println(e.getMessage());
                    }


                    transaction.commit();
                    sessionFactory.close();
                    break;
                }
                case "DELETE" -> {
                    SessionFactory sessionFactory = DataBaseService.CreateSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction transaction = session.beginTransaction();

                    try {
                        service.delete(session, Long.valueOf(command.get(1)));
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат ввода");
                    }

                    transaction.commit();
                    sessionFactory.close();
                    break;
                }
                case "EXIT" -> {
                    System.exit(0);
                    break;
                }
                case "HELP" -> {
                    help();
                    break;
                }
                default -> {
                    System.out.println("Введите команду. HELP - увидеть список команд");
                    break;
                }
            }
        }
    }

    public static void help() {
        System.out.println("Список команд:" + System.lineSeparator() +
                "LIST - выводит список всех студентов" + System.lineSeparator() +
                "ADD - добавление студента в базу" + System.lineSeparator() +
                "ADD [Фамилия] [Имя] [Отчество] [Дата рождения DD.MM.YYYY] [Группа]" + System.lineSeparator() +
                "DELETE - удаление студента по id" + System.lineSeparator() +
                "DELETE [id]" + System.lineSeparator() +
                "EXIT - завершение программы");
    }

    public static LinkedList<String> parseCommand(LinkedList<String> list, String command) {
        if(command.length() > 0) {
            list.clear();
            String[] splitCommand = command.split(" ");
            StringBuilder res = new StringBuilder();
            if(splitCommand.length > 1){
                list.add(splitCommand[0]);
                list.add(splitCommand[1]);
                for(int i = 2; i < splitCommand.length; i++) {
                    res.append(splitCommand[i]).append(" ");
                }
            } else {
                list.add(splitCommand[0]);
                for(int i = 1; i < splitCommand.length; i++) {
                    res.append(splitCommand[i]).append(" ");
                }
            }
            list.add(String.valueOf(res).trim());
            res.setLength(0);
            return list;
        } else {
            list.add("");
            return list;
        }
    }

}
