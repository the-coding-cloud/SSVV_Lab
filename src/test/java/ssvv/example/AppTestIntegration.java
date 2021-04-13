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
        char[] fill = new char [Integer.MAX_VALUE / 1000];
        Arrays.fill(fill, 'a');
        maxLenString = new String(fill);
    }

    /**
     * Rigorous Test :-)
     */

    @Test
    public void AddStudent_Successfully()
    {
        String id = "1234";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, "StudentTest", 231));
    }

    @Test
    public void AddAssignment_Successfully(){
        service.deleteTema("123");
        assertEquals(1, service.saveTema("123", "tema", 5, 2));
    }

    @Test
    public void AddGrade_Successfully(){
        assertEquals(1, service.saveNota("1234", "123", 9, 3, "yes"));
    }

    @Test
    public void AddStudentAssigGrade_Successfully(){
        service.deleteTema("123");
        service.deleteStudent("1234");
        assertEquals(1, service.saveStudent("1234", "StudentTest", 231));
        assertEquals(1, service.saveTema("123", "tema", 5, 2));
        assertEquals(1, service.saveNota("1234", "123", 9, 3, "yes"));
    }


}
