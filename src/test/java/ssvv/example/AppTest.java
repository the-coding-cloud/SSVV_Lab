package ssvv.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import ssvv.example.domain.Student;
import ssvv.example.repository.StudentFileRepository;
import ssvv.example.validation.StudentValidator;
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
    public void FirstStudentIsConsistent()
    {

        Validator validator = new StudentValidator();
        StudentFileRepository rep = new StudentFileRepository(validator,"studenti.txt");
        Iterator<Student> iterator =rep.findAll().iterator();
        assertEquals("1", iterator.next().getID());
        assertEquals("2", iterator.next().getID());
    }
}
