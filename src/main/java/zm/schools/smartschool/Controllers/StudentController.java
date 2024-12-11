package zm.schools.smartschool.Controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.StudentDTO;
import zm.schools.smartschool.DTOs.StudentResponseDTO;
import zm.schools.smartschool.Services.StudentService;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService; 

   
   @PostMapping("/create")
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        StudentResponseDTO createdStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/examinationNumber/{examinationNumber}")
    public ResponseEntity<StudentResponseDTO> getStudentByExaminationNumber(@PathVariable String examinationNumber) {
        StudentResponseDTO studentResponseDTO = studentService.getStudentByExaminationNumber(examinationNumber);
        return ResponseEntity.ok(studentResponseDTO);
    }

    @PutMapping("/examinationNumber/{examinationNumber}")
    public ResponseEntity<StudentResponseDTO> updateStudentByExaminationNumber(@PathVariable String examinationNumber, @RequestBody StudentDTO studentDTO) {
        StudentResponseDTO updatedStudentResponseDTO = studentService.updateStudentByExaminationNumber(examinationNumber, studentDTO);
        return ResponseEntity.ok(updatedStudentResponseDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
