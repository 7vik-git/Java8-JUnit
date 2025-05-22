import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.sudentgrading.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;

public class StudentTest {

    @Test
    void testValidStudentAverageAndGrade() throws Exception {
        Student s = new Student("John", "101", Arrays.asList(90, 80, 85));
        GradeService service = new GradeService();
        String grade = service.getGrade(s, GradingStrategies.basicStrategy);
        assertEquals("A", grade);
        assertEquals(85.0, s.getAverage(), 0.01);
    }

    @Test
    void testGradeBoundaryB() throws Exception {
        Student s = new Student("Mary", "102", Arrays.asList(70, 72, 74));
        GradeService service = new GradeService();
        String grade = service.getGrade(s, GradingStrategies.basicStrategy);
        assertEquals("B", grade);
    }

    @Test
    void testGradeBoundaryC() throws Exception {
        Student s = new Student("Tom", "103", Arrays.asList(50, 55, 52));
        GradeService service = new GradeService();
        String grade = service.getGrade(s, GradingStrategies.basicStrategy);
        assertEquals("C", grade);
    }

    @Test
    void testGradeFail() throws Exception {
        Student s = new Student("Jake", "104", Arrays.asList(30, 40, 45));
        GradeService service = new GradeService();
        String grade = service.getGrade(s, GradingStrategies.basicStrategy);
        assertEquals("Fail", grade);
    }


    @Test
    void testEmptyMarkListThrows() {
        assertThrows(EmptyMarkListException.class, () ->
                new Student("Alice", "105", Collections.emptyList()));
    }

    @Test
    void testInvalidMarkThrows() {
        assertThrows(InvalidMarkException.class, () ->
                new Student("Bob", "106", Arrays.asList(60, -1, 90)));
    }

    @Test
    void testInvalidMarkAbove100Throws() {
        assertThrows(InvalidMarkException.class, () ->
                new Student("Lara", "107", Arrays.asList(101, 90, 80)));
    }
}
