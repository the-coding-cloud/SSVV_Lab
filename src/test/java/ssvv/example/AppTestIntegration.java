package ssvv.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for simple App.
 */
public class AppTestIntegration
{
    final Validator<Student> studentValidator = new StudentValidator();
    final Validator<Tema> temaValidator = new TemaValidator();
    final Validator<Nota> notaValidator = new NotaValidator();
    final StudentXMLRepository studentRepo = new StudentXMLRepository(studentValidator, "test-studenti.xml");
    final TemaXMLRepository temaRepo = new TemaXMLRepository(temaValidator, "test-teme.xml");
    final NotaXMLRepository notaRepo = new NotaXMLRepository(notaValidator, "test-note.xml");
    final Service service = new Service(studentRepo, temaRepo, notaRepo);
    String maxLenString;

    public AppTestIntegration() {

    }

    private void resetTestRepos(){
        temaRepo.resetXMLFile();
        notaRepo.resetXMLFile();
        studentRepo.resetXMLFile();
    }

    @Test
    public void AddStudent_Successfully()
    {
        resetTestRepos();
        assertEquals(1, service.saveStudent("1", "StudentTest", 231));
    }

    @Test
    public void AddAssignment_Successfully(){
        resetTestRepos();
        assertEquals(1, service.saveTema("123", "tema", 5, 2));
    }

    @Test
    public void AddGrade_Successfully(){
        resetTestRepos();
        service.saveStudent("1234", "StudentTest", 231);
        service.saveTema("123", "tema", 5, 2);

        assertEquals(1, service.saveNota("1234", "123", 9, 3, "yes"));
    }

    @Test
    public void AddStudentAssigGrade_Successfully(){
        resetTestRepos();

        assertEquals(1, service.saveStudent("12345", "StudentTest", 231));
        assertEquals(1, service.saveTema("1234", "tema", 5, 2));
        assertEquals(1, service.saveNota("12345", "1234", 9, 3, "yes"));
    }

    @Test
    public void AddStudent_Incremental()
    {
        resetTestRepos();
        assertEquals(1, service.saveStudent("1`", "StudentTest", 231));
    }

    @Test
    public void AddStudentAssignment_Incremental()
    {
        resetTestRepos();

        assertEquals(1, service.saveStudent("1", "Incremental Test", 231));
        assertEquals(1, service.saveTema("1", "tema", 3, 1));
    }

    @Test
    public void AddStudentAssignmentGrade_Incremental()
    {
        resetTestRepos();

        assertEquals(1, service.saveStudent("1", "Incremental Test", 231));
        assertEquals(1, service.saveTema("1", "tema", 3, 1));

        assertEquals(1, service.saveNota("1", "1", 9, 2, "ok"));
    }
}
