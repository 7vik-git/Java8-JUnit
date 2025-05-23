package com.gevernova;

import com.gevernova.leavemanagment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaveServiceTest {

    private LeaveService service;
    private LeavePolicy basicPolicy;

    @BeforeEach
    void setUp() {
        service = new LeaveService();
        basicPolicy = (emp, leave) -> emp.getRemainingLeaves() > 0;
    }

    @Test
    void testApplyValidLeave() throws Exception {
        Employee e = new Employee("Alice", 10);
        Leave leave = new Leave(LocalDate.now().plusDays(1), "Casual");
        service.applyLeave(e, leave, basicPolicy);
        assertEquals(9, e.getRemainingLeaves());
    }

    @Test
    void testLowBalanceEmployeeFilter() {
        Employee e1 = new Employee("John", 10);
        Employee e2 = new Employee("Doe", 10);
        for (int i = 0; i < 6; i++) {
            e2.applyLeave(new Leave(LocalDate.now().plusDays(i + 1), "Sick"));
        }
        List<Employee> lowBalance = service.getLowBalanceEmployees(Arrays.asList(e1, e2));
        assertEquals(1, lowBalance.size());
        assertEquals("Doe", lowBalance.get(0).getName());
    }

    @Test
    void testLeaveLimitExceededThrows() {
        Employee e = new Employee("Bob", 0);
        Leave leave = new Leave(LocalDate.now().plusDays(1), "Casual");
        assertThrows(LeaveLimitExceededException.class, () ->
                service.applyLeave(e, leave, basicPolicy));
    }

    @Test
    void testPastLeaveDateThrows() {
        Employee e = new Employee("Clara", 5);
        Leave leave = new Leave(LocalDate.now().minusDays(2), "Sick");
        assertThrows(InvalidLeaveDateException.class, () ->
                service.applyLeave(e, leave, basicPolicy));
    }
}
