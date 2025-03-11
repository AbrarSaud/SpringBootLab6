package com.example.employeemanagementsystem.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employees {
    @NotNull(message = "The 'ID' must be NOT Null")
    @Min(value = 2, message = "The 'ID' must be more than 2 characters")
    private int id;

    @NotNull(message = "The 'Name' must be NOT Null")
    @Size(min = 2, max = 200, message = "The 'Name' must be more than 4 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The 'Name' Must contain only characters ")
    private String name;

    @Email()
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "The 'Phone Number' must consist of exactly 10 digits and must start with '05'")
    private String phoneNumber;


    @NotNull(message = "The 'Age' must be NOT Null")
    @Positive
    @Min(value = 26, message = "The 'Age' must be more than 25")
    private int age;

    @NotNull(message = "The 'Position' must be NOT Null")
    @Pattern(regexp = "Supervisor||Coordinator", message = " The 'Position' must be either \"Supervisor\" or \"Coordinator\" only.")
    private String position;
    @AssertFalse(message = "The 'onLeave  must be (false)'")
    private boolean onLeave ;

    @NotNull(message = "The 'hire Date' must be NOT Null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate hireDate;

    @NotNull(message = "The 'Annual Leave' must be NOT Null")
    @PositiveOrZero
    private int annualLeave;

}
