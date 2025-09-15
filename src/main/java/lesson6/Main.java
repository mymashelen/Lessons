package lesson6;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Collection<Student> students = new ArrayList<>();
        Student student1 = new Student("Иванов Иван", "АиМ24", 1);
        student1.addGrade("Высшая математика", 3);
        student1.addGrade("Физика", 4);
        student1.addGrade("Химия", 5);
        Student student2 = new Student("Петров Петр", "АиМ24", 1);
        student2.addGrade("Высшая математика", 2);
        student2.addGrade("Физика", 3);
        student2.addGrade("Химия", 3);
        Student student3 = new Student("Данилова Полина", "ЭиБ23", 2);
        student3.addGrade("Экономика", 5);
        student3.addGrade("Философия", 4);
        Student student4 = new Student("Абрамов Данил", "ЭиБ24", 1);
        student4.addGrade("Экономика", 5);
        student4.addGrade("Высшая математика", 4);
        student4.addGrade("История", 4);

        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);

        System.out.println("Список студентов 1 курса до отчисления:");
        printStudents(students, 1);
        removeFailedStudents(students);
        promoteSuccessfulStudents(students);
        System.out.println("Список студентов 2 курса после отчисления и перевода:");
        printStudents(students, 2);

        PhoneDirectory dir = new PhoneDirectory();
        dir.add("Иванов", "8-900-100-20-30");
        dir.add("Иванов", "8-901-200-30-40");
        dir.add("Петров", "8-902-300-40-50");

        System.out.println(dir.get("Иванов"));

        System.out.println(dir.get("Сидоров"));
    }


    public static void removeFailedStudents(Collection<Student> students){
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()){
            Student student = iterator.next();
            if (student.calculateAverageGrade() < 3.0){
                iterator.remove();
            }
        }
    }

    public static void promoteSuccessfulStudents(Collection<Student> students){
        for (Student student: students){
            if (student.calculateAverageGrade() >= 3.0){
                student.setCourse(student.getCourse()+1);
            }
        }
    }

    public static void printStudents(Collection<Student> students, int course){
        boolean found = false;
        for (Student student: students){
            if (student.getCourse() == course){
                System.out.println(student.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("На курсе " + course + " нет студентов.");
        }
    }
}
