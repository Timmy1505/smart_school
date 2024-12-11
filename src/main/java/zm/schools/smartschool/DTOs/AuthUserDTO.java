package zm.schools.smartschool.DTOs;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;



@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthUserDTO {

    private Long id;
    private String employeeNumber;
    private String nrc;
    private String roleName; // Role name passed from the controller
    private String password;
    private String phoneNumber;
    private Boolean locked;
    private Boolean enabled;
    private AppUserDTO appUser;
    private List<Long> gradeIds; // List of grade IDs if the user is a teacher
    private List<Long> subjectIds; // List of subject IDs if the user is a teacher
}


