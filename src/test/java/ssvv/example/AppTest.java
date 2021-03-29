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
public class AppTest
{
    final Validator<Student> studentValidator = new StudentValidator();
    final Validator<Tema> temaValidator = new TemaValidator();
    final Validator<Nota> notaValidator = new NotaValidator();
    final StudentXMLRepository studentRepo = new StudentXMLRepository(studentValidator, "studenti.xml");
    final TemaXMLRepository temaRepo = new TemaXMLRepository(temaValidator, "teme.xml");
    final NotaXMLRepository notaRepo = new NotaXMLRepository(notaValidator, "note.xml");
    final Service service = new Service(studentRepo, temaRepo, notaRepo);
    String maxLenString;

    public AppTest() {
        char[] fill = new char [Integer.MAX_VALUE / 1000];
        Arrays.fill(fill, 'a');
        maxLenString = new String(fill);
    }

    /**
     * Rigorous Test :-)
     */

    @Test
    public void AddStudentSuccessfully()
    {
        String id = "alksdjnfTest12";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, "TestGood", 231));
    }

    @Test
    public void AddStudent_ValidID()
    {
        String id = "123";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, "TestGood", 231));
    }

    @Test
    public void AddStudent_NullID()
    {
        assertEquals(0, service.saveStudent(null, "TestGood", 231));
    }

    @Test
    public void AddStudent_NullName()
    {
        String id = "123";
        service.deleteStudent(id);
        assertEquals(0, service.saveStudent(id, null, 231));
    }

    @Test
    public void AddStudent_DuplicateID()
    {
        String id = "123";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, "John", 231));
        assertEquals(0, service.saveStudent(id, "John", 231));
    }

    @Test
    public void AddStudent_IDEmptyString()
    {
        String id = "";
        service.deleteStudent(id);
        assertEquals(0, service.saveStudent(id, "TestGood", 231));
    }

    @Test
    public void AddStudent_NameEmptyString()
    {
        String id = "12";
        String name = "";
        service.deleteStudent(id);
        assertEquals(0, service.saveStudent(id, name, 231));
    }

    @Test
    public void AddStudent_GroupLessThan111()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(0, service.saveStudent(id, name, 110));
    }

    @Test
    public void AddStudent_GroupEqualTo111()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 111));
    }

    @Test
    public void AddStudent_GroupMoreThan111()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 112));
    }

    @Test
    public void AddStudent_GroupLessThan937()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 936));
    }

    @Test
    public void AddStudent_GroupEqualTo937()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 937));
    }

    @Test
    public void AddStudent_GroupMoreThan937()
    {
        String id = "12";
        String name = "Test";
        service.deleteStudent(id);
        assertEquals(0, service.saveStudent(id, name, 938));
    }

}
