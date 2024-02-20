package epicode.u5w3d1.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import epicode.u5w3d1.entities.Employee;
import epicode.u5w3d1.exceptions.BadRequestException;
import epicode.u5w3d1.payloads.NewEmployeeDTO;
import epicode.u5w3d1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;


    //GET
    @GetMapping
    public Page<Employee> getEmployee(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "name") String sortBy){
        return employeeService.getEmployees(page,size,sortBy);
    }

    //SAVE EMPLOYEE

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Validated NewEmployeeDTO payload, BindingResult validation) throws Exception{
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.employeeService.saveEmployee(payload);
    }

    //FIND EMPLOYEE BY ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Employee findById(@PathVariable UUID id) {return this.employeeService.findById(id);}


    //UPDATE EMPLOYEE
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")

    public Employee FindByIdAndUpdate(@PathVariable UUID id, @RequestBody NewEmployeeDTO updatedEmployee){
        return this.employeeService.findByIdAndUpdate(id, updatedEmployee);
    }

    //DELETE EMPLOYEE

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID id) {this.employeeService.findByIDAndDelete(id);}


    //UPLOAD PROFILE PICTURE
    @PatchMapping("/{id}/profile_picture")
    public Employee uploadProfilePic(@RequestParam("profile_picture") MultipartFile file, @PathVariable UUID id){
        try{
            return employeeService.uploadProfilePicture(id, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
