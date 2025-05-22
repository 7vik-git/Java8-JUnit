package com.gevernova.leavemanagment;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LeaveService {

    public void applyLeave(Employee emp, Leave leave, LeavePolicy policy) throws Exception {
        if (leave.getDate().isBefore(LocalDate.now())) {
            throw new InvalidLeaveDateException("Cannot apply leave for past date");
        }
        if (!policy.isLeaveAllowed(emp, leave)) {
            throw new LeaveLimitExceededException("Leave limit exceeded");
        }
        emp.applyLeave(leave);
    }

    public List<Employee> getLowBalanceEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(e -> e.getRemainingLeaves() < 5)
                .collect(Collectors.toList());
    }
}

