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

}
