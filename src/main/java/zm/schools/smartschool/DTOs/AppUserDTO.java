package zm.schools.smartschool.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import zm.schools.smartschool.Enums.Gender;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppUserDTO {

  
    private String firstName;
    private String lastName;
    private Gender gender;
    private String dateOfBirth;
    private AddressDto address;
    private Long schoolId;
 
}
