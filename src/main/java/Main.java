import domain.Student;
import repository.StudentRepository;
import repository.StudentSqRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentSqRepository();

        Student studentNew = Student.builder().
                name("Tom").
                age(35).
                build();

        studentRepository.save(studentNew);
        System.out.println(studentRepository.findAll());
    }
}
