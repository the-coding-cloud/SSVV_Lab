package ssvv.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import ssvv.example.domain.Student;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentFileRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void NumberOfStudentsInRepoIs2()
    {
        AtomicReference<Integer> studentCounter = new AtomicReference<>(0);
        Validator validator = new StudentValidator();
        StudentFileRepository rep = new StudentFileRepository(validator,"studenti.txt");
        rep.findAll().forEach(student -> { studentCounter.getAndSet(studentCounter.get() + 1);});
        assertTrue( studentCounter.get() == 2 );
    }
    @Test
    public void AddStudentSuccessfully()
    {
        AtomicReference<Integer> studentCounter = new AtomicReference<>(0);
        Validator studentValidator = new StudentValidator();
        Validator temaValidator = new TemaValidator();
        Validator notaValidator = new NotaValidator();

        StudentXMLRepository studRep = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository temaRep = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository notaRep = new NotaXMLRepository(notaValidator, "note.xml");
        Service service = new Service(studRep,temaRep,notaRep);

        String id = "alksdjnfTest12";
        service.deleteStudent(id);
        assertTrue( service.saveStudent(id,"TestGood",231) == 1 );

    }
    @Test
    public void FirstStudentIsConsistent()
    {

        Validator validator = new StudentValidator();
        StudentFileRepository rep = new StudentFileRepository(validator,"studenti.txt");
        Iterator<Student> iterator =rep.findAll().iterator();
        assertEquals("1", iterator.next().getID());
        assertEquals("2", iterator.next().getID());
    }
}
