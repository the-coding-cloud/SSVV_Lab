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
    public void AddStudent_BVA_LowerBound()
    {
        String id = "1";
        String name = "A";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 111));
    }

    @Test
    public void AddStudent_BVA_AboveLowerBound()
    {
        String id = "12";
        String name = "AB";
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 112));
    }

    @Test
    public void AddStudent_BVA_BelowUpperBound()
    {
        String id = maxLenString.substring(1);
        String name = maxLenString.substring(1);
        service.deleteStudent(id);
        assertEquals(1, service.saveStudent(id, name, 936));
    }

    @Test
    public void AddStudent_GroupEqualTo937()
    {
        String id = maxLenString;
        String name = maxLenString;
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

    @Test
    public void AddAssignment_WBT_1(){
        service.deleteTema("123");
        assertEquals(1, service.saveTema("123", "tema", 5, 2));
    }

    @Test
    public void AddAssignment_WBT_2(){
        // invalid startline
        service.deleteTema("123");
        assertEquals(0, service.saveTema("123", "tema", 2, 5));
    }

    @Test
    public void AddAssignment_WBT_3(){
        // invalid deadline
        service.deleteTema("123");
        assertEquals(0, service.saveTema("123", "tema", -2, 5));
    }

    @Test
    public void AddAssignment_WBT_4(){
        // invalid id
        service.deleteTema("123");
        assertEquals(0, service.saveTema("", "tema", 2, 5));
    }

    @Test
    public void AddAssignment_WBT_5(){
        // invalid description
        service.deleteTema("123");
        assertEquals(0, service.saveTema("123", "", 2, 5));
    }

    @Test
    public void AddAssignment_WBT_6(){
        // duplicate id
        service.deleteTema("123");
        service.saveTema("123", "tema", 5, 2);
        assertEquals(0, service.saveTema("123", "tema", 2, 5));
    }
}
