package com.example.employeemanagementsystem.Controller;


import com.example.employeemanagementsystem.Api.ApiResponse;
import com.example.employeemanagementsystem.Model.Employees;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employees> employees = new ArrayList<>();


    //    1 . Retrieves a list of all employees.
    @GetMapping("/get")
    public ArrayList<Employees> getAllEmployees() {
        return employees;
    }

    //   2. Adds a new employee to the system
    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employees employee, Errors errors) {
        if (errors.hasErrors()) {
            String massageError = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(massageError));
        }
        employees.add(employee);
        return ResponseEntity.status(200).body("Addend successfully!");
    }

    // 3.  Updates an existing employee's information
    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployeeInfo(@PathVariable int id, @RequestBody @Valid Employees employee, Errors errors) {
        if (errors.hasErrors()) {
            String massageError = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(massageError));
        }
        for (Employees e : employees) {
            if (e.getId() == id) {
                e.setId(employee.getId());
                e.setName(employee.getName());
                e.setEmail(employee.getEmail());
                e.setAge(employee.getAge());
                e.setPosition(employee.getPosition());
                e.setPhoneNumber(employee.getPhoneNumber());
                e.setOnLeave(employee.isOnLeave());
                e.setHireDate(employee.getHireDate());
                e.setAnnualLeave(employee.getAnnualLeave());
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse("Update successfully!!"));
    }

    //    4. Deletes an employee from the system.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable int id) {

        for (Employees e : employees) {
            if (e.getId() == id) {
                employees.remove(e);
                return ResponseEntity.status(200).body(new ApiResponse("Delete successfully!!"));
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("Employee not found!"));
    }

    // 5 . Search Employees by Position
    @GetMapping("/search")
    public ResponseEntity searchByPosition(@RequestParam String position) {
        ArrayList<Employees> employeesByPosition = new ArrayList<>();
        for (Employees e : employees) {
            if (e.getPosition().equals(position)) {
                employeesByPosition.add(e);
            }
        }
        return ResponseEntity.status(200).body(employeesByPosition);
    }

    // 6. Get Employees by Age Range
    @GetMapping("/get-by-age")
    public ResponseEntity getByAge(@RequestParam int age) {
        ArrayList<Employees> employeesByAeg = new ArrayList<>();
        for (Employees e : employees) {
            if (e.getAge() == age) {
                employeesByAeg.add(e);
            }
        }
        return ResponseEntity.status(200).body(employeesByAeg);
    }

    // 7. Apply for annual leave
    @GetMapping("/apply-annual-leave/{id}")
    public ResponseEntity applyAnnualLeave(@PathVariable int id) {
        for (Employees e : employees) {
            if (e.getId() == id && e.isOnLeave() == false && e.getAnnualLeave() >= 1) {
                e.setOnLeave(true);
                int currentAnnualLeave = e.getAnnualLeave() - 1;
                e.setAnnualLeave(currentAnnualLeave);
                return ResponseEntity.status(200).body(new ApiResponse("Your leave has been approved."));

            }
        }
        return ResponseEntity.status(404).body("Employee not found!");

    }

    // 8. Get Employees with No Annual Leave
    @GetMapping("/get-no-annualLeave")
    public ResponseEntity getNoAnnualLeave() {
        ArrayList<Employees> employeesWithNoAnnualLeave = new ArrayList<>();

        for (Employees e : employees) {
            if (e.getAnnualLeave() == 0) {
                employeesWithNoAnnualLeave.add(e);
            }
        }
        return ResponseEntity.status(200).body(employeesWithNoAnnualLeave);
    }

    // 9. Promote Employee
    @GetMapping("/promote-employee")
    public ResponseEntity promoteEmployee(@RequestParam int id_supervisor, @RequestParam int id_employee) {
        for (Employees e : employees) {
            if (e.getId() == id_supervisor && e.getPosition().equals("Supervisor")) {
                if (e.getId() == id_employee && e.getAge() <= 30 && e.isOnLeave() == false) {
                    e.setPosition("Supervisor");
                    return ResponseEntity.status(200).body(new ApiResponse("You have been promoted to Supervisor."));
                }
            }
        }
        return ResponseEntity.status(404).body(new ApiResponse("Employee not found!"));
    }
}
