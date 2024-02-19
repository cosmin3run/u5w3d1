package epicode.u5w3d1.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.u5w3d1.entities.Employee;
import epicode.u5w3d1.exceptions.BadRequestException;
import epicode.u5w3d1.exceptions.NotFoundException;
import epicode.u5w3d1.payloads.NewEmployeeDTO;
import epicode.u5w3d1.repositories.EmployeeDAO;
import epicode.u5w3d1.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private EmployeeDAO employeeDAO;


    //SAVE EMPLOYEE IN DB
    public Employee saveEmployee(NewEmployeeDTO payload) throws IOException{
        employeeDAO.findByEmail(payload.email()).ifPresent(employee -> {
            throw new BadRequestException("Email '" + payload.email() + "' is already in use");
        });
        employeeDAO.findByUsername(payload.username()).ifPresent(employee -> {
            throw new BadRequestException("'" + payload.username() + "' already in use, try another username" );
        });
        Employee newEmployee = new Employee();
        newEmployee.setUsername(payload.username());
        newEmployee.setName(payload.name());
        newEmployee.setSurname(payload.surname());
        newEmployee.setEmail(payload.email());
        return employeeDAO.save(newEmployee);
    }

    //GET ALL EMPLOYEES
    public Page<Employee> getEmployees(int page, int size, String sort){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return employeeDAO.findAll(pageable);
    }

    //FIND EMPLOYEE BY ID
    public Employee findById(UUID id) {
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //UPDATE EMPLOYEE IN DB
    public Employee findByIdAndUpdate(UUID id, NewEmployeeDTO payload){
        Employee found = this.findById(id);

        found.setUsername(payload.username());
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        return employeeDAO.save(found);
    }

    //DELETE EMPLOYEE FROM DB
    public void findByIDAndDelete(UUID id){
        Employee found = this.findById(id);
        employeeDAO.delete(found);
    }

    //UPLOAD PROFILE PIC IN DB
    public Employee uploadProfilePicture(UUID id, MultipartFile file) throws IOException{
        Employee found = this.findById(id);
        String profilePicURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setProfileImg(profilePicURL);
        return employeeDAO.save(found);
    }



}
