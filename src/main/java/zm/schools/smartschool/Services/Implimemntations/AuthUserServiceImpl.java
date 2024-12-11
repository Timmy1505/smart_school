package zm.schools.smartschool.Services.Implimemntations;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.AddressDto;
import zm.schools.smartschool.DTOs.AppUserDTO;
import zm.schools.smartschool.DTOs.AuthUserDTO;
import zm.schools.smartschool.Exceptions.ResourceNotFoundException;
import zm.schools.smartschool.Models.Address;
import zm.schools.smartschool.Models.AppUser;
import zm.schools.smartschool.Models.AuthUser;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Role;
import zm.schools.smartschool.Models.School;
import zm.schools.smartschool.Models.Subject;
import zm.schools.smartschool.Repositories.AppUserRepository;
import zm.schools.smartschool.Repositories.AuthUserRepository;

import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.SubjectRepository;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Repositories.RoleRepository;
import zm.schools.smartschool.Services.AuthUserService;

@Service
@AllArgsConstructor
@Transactional
public class AuthUserServiceImpl implements AuthUserService {
    
    private final AuthUserRepository authUserRepository;
    private final RoleRepository roleRepository;
    private final GradeRepository gradeRepository;
    private final SubjectRepository subjectRepository;
    private final AppUserRepository appUserRepository;
    //TODO: check for passed school id if Exixts.
    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
        Optional<AuthUser> authUserOptional = authUserRepository.findByEmployeeNumber(employeeNumber);
        if (authUserOptional.isPresent()) {
            return authUserOptional.get();
        }
        throw new UsernameNotFoundException(String.format("User with username %s not found", employeeNumber));
    }

    @Override
    public AuthUserDTO createAuthUser(AuthUserDTO authUserDTO) {
        AuthUser authUser = new AuthUser();

        // Map the basic fields
        authUser.setEmployeeNumber(authUserDTO.getEmployeeNumber());
        authUser.setNrc(authUserDTO.getNrc());
        //encode user password

        String encodedPassword =  passwordEncoder.encode(authUserDTO.getPassword());
        authUser.setPassword(encodedPassword);
        authUser.setPhoneNumber(authUserDTO.getPhoneNumber());
        authUser.setLocked(false);
        authUser.setEnabled(authUserDTO.getEnabled());
       
        // Map the AppUser
        AppUserDTO appUserDTO = authUserDTO.getAppUser();
        AppUser appUser = new AppUser();
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setGender(appUserDTO.getGender());
        appUser.setDateOfBirth(appUserDTO.getDateOfBirth());

           //check and return school
           School rtrnedSchool = schoolRepository.getReferenceById(appUserDTO.getSchoolId());
           appUser.setSchool(rtrnedSchool);
       
         //Map address
        Address userAddress = new Address();
        userAddress.setHouseNumber(appUserDTO.getAddress().getHouseNumber());
        userAddress.setStreetName(appUserDTO.getAddress().getHouseNumber());
        
        //set address 
        appUser.setAddress(userAddress);

        appUser = appUserRepository.save(appUser);

     
        authUser.setAppUser(appUser);
        

        // Set the Role
        Role role = roleRepository.findByName(authUserDTO.getRoleName());
        authUser.setAppUserRole(role);

        // Assign grades and subjects if the role is "Teacher"
        if ("Teacher".equalsIgnoreCase(role.getName())) {
            List<Grade> grades = gradeRepository.findAllById(authUserDTO.getGradeIds());
            List<Subject> subjects = subjectRepository.findAllById(authUserDTO.getSubjectIds());
            authUser.assignGradeAndSubject(grades, subjects);
        }

        authUser = authUserRepository.save(authUser);

        return modelMapper.map(authUser, AuthUserDTO.class);
    }

    @Override
    public AuthUserDTO updateAuthUser(Long id, AuthUserDTO authUserDTO) {
        Optional<AuthUser> existingAuthUserOptional = authUserRepository.findById(id);

        if (existingAuthUserOptional.isPresent()) {
            AuthUser authUser = existingAuthUserOptional.get();

            // Update the basic fields
            authUser.setEmployeeNumber(authUserDTO.getEmployeeNumber());
            authUser.setNrc(authUserDTO.getNrc());
            authUser.setPassword(authUserDTO.getPassword());
            authUser.setPhoneNumber(authUserDTO.getPhoneNumber());
            authUser.setLocked(authUserDTO.getLocked());
            authUser.setEnabled(authUserDTO.getEnabled());

            // Update the AppUser
            AppUserDTO appUserDTO = authUserDTO.getAppUser();
            if (appUserDTO != null) {
                AppUser appUser = authUser.getAppUser();
                modelMapper.map(appUserDTO, appUser);

                
                appUser = appUserRepository.save(appUser);
                authUser.setAppUser(appUser);
            }

            // Update the Role
            Role role = roleRepository.findByName(authUserDTO.getRoleName());
            authUser.setAppUserRole(role);

            // Assign grades and subjects if the role is "Teacher"
            if ("Teacher".equalsIgnoreCase(role.getName())) {
                List<Grade> grades = gradeRepository.findAllById(authUserDTO.getGradeIds());
                List<Subject> subjects = subjectRepository.findAllById(authUserDTO.getSubjectIds());
                authUser.assignGradeAndSubject(grades, subjects);
            }

            authUser = authUserRepository.save(authUser);

            return modelMapper.map(authUser, AuthUserDTO.class);
        } else {
            throw new ResourceNotFoundException("Auth User", "id", id);
        }
    } @Override
    public AuthUserDTO getAuthUserById(Long id) {
        AuthUser authUser = authUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return modelMapper.map(authUser, AuthUserDTO.class);
    }

    @Override
    public void deleteAuthUser(Long id) {
        authUserRepository.deleteById(id);
    }



    @Override
    public List<AuthUserDTO> getAllAuthUsers() {
        List<AuthUser> authUsers = authUserRepository.findAll();

        return authUsers.stream()
                        .map(this::convertToAuthUserDTO)
                        .collect(Collectors.toList());
    }

    private AuthUserDTO convertToAuthUserDTO(AuthUser authUser) {
        // Use ModelMapper to map AuthUser to AuthUserDTO
        AuthUserDTO authUserDTO = modelMapper.map(authUser, AuthUserDTO.class);

        // Handle the conditional mapping for gradeIds and subjectIds if the role is "Teacher"
        if ("Teacher".equals(authUser.getAppUserRole().getName())) {
            authUserDTO.setGradeIds(
                authUser.getGrades().stream().map(Grade::getId).collect(Collectors.toList())
            );
            authUserDTO.setSubjectIds(
                authUser.getSubjects().stream().map(Subject::getId).collect(Collectors.toList())
            );
        }

        return authUserDTO;
    }
}


