package repository;

import domain.Student;

import java.util.List;

public interface StudentRepository {

    void save (Student student);
    List<Student> findAll();

}
