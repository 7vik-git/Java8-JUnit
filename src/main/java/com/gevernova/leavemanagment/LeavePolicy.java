package com.gevernova.leavemanagment;

@FunctionalInterface
public interface LeavePolicy {
    boolean isLeaveAllowed(Employee employee, Leave leave);
}

