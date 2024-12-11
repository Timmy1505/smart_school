package zm.schools.smartschool.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import zm.schools.smartschool.DTOs.AddressDto;
import zm.schools.smartschool.DTOs.AppUserDTO;
import zm.schools.smartschool.DTOs.AuthUserDTO;
import zm.schools.smartschool.DTOs.SchoolDto;
import zm.schools.smartschool.Enums.Gender;
import zm.schools.smartschool.Enums.Term;
import zm.schools.smartschool.Models.Address;
import zm.schools.smartschool.Models.AppUser;
import zm.schools.smartschool.Models.AuthUser;
import zm.schools.smartschool.Models.Role;
import zm.schools.smartschool.Models.School;
import zm.schools.smartschool.Repositories.AppUserRepository;
import zm.schools.smartschool.Repositories.AuthUserRepository;
import zm.schools.smartschool.Repositories.RoleRepository;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Services.AuthUserService;
import zm.schools.smartschool.Services.SchoolService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
@Component
public class AuthUserSeeder implements CommandLineRunner {

   @Autowired
    private AuthUserService authUserService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AppUserRepository appUserRepository;


    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty before seeding
    

            // Create Roles
            Role teacherRole = roleRepository.save(new Role(1L, "Teacher"));
            Role adminRole = roleRepository.save(new Role(2L, "Admin"));
            Role accountantRole = roleRepository.save(new Role(3L, "Accountant"));

            // Create School
            SchoolDto school = new SchoolDto(1L, "Mkushi Secondary", "100010", "84007", false, "SECONDARY", 2024, Term.TERM1, LocalDate.of(2024, 01, 01), LocalDate.of(2024, 12, 31));
            schoolService.createSchool(school);

    
            // Fetch a school for app use to be created
            AddressDto teacherAdress = new AddressDto("P788","Kalewa");
            School school1 = schoolRepository.getReferenceById(1L);
            AppUserDTO teacherUser = new AppUserDTO("timmy", "Tembo",Gender.MALE,"1/05/1999",teacherAdress,1L);
            AuthUserDTO teacher = new AuthUserDTO(null, "E01","4247316/11/1","Admin","password","0777077070",false,true,teacherUser,Collections.emptyList(),Collections.emptyList());
            authUserService.createAuthUser(teacher);
          

            System.out.println("AuthUser seeding completed.");
      
    }
}
