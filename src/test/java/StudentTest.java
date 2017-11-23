
import verarbeitung.Student;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.*;

import java.time.LocalDate;

/**
 * Created by Tobias on 26/10/17.
 */
public class StudentTest {
    Student s = new Student("hans", "Friedrich","Land",LocalDate.of(2000,9,1),"HTW","AI",LocalDate.of(2020,6,20), 3, true);

    @Test
    public void studienbescheinigungAktuell() throws Exception {
        s.studienbescheinigung = 4;
        assertTrue("Test erfolgreich",s.aktuelleStudienBescheinigung);
        s.studienbescheinigung = 3;
        assertTrue("Test erfolgreich",s.aktuelleStudienBescheinigung);
        s.studienbescheinigung = 2;
        assertFalse("Test erfolgreich",s.aktuelleStudienBescheinigung);
    }
}