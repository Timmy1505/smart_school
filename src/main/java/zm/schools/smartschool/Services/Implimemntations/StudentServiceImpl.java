package zm.schools.smartschool.Services.Implimemntations;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.AddressDto;
import zm.schools.smartschool.DTOs.AppUserDTO;
import zm.schools.smartschool.DTOs.GuardianDto;
import zm.schools.smartschool.DTOs.StudentDTO;
import zm.schools.smartschool.DTOs.StudentResponseDTO;
import zm.schools.smartschool.Exceptions.InvalidExaminationNumberException;
import zm.schools.smartschool.Models.Address;
import zm.schools.smartschool.Models.AppUser;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Guardian;
import zm.schools.smartschool.Models.Section;
import zm.schools.smartschool.Models.Student;
import zm.schools.smartschool.Repositories.AppUserRepository;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.GuardianRepository;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Repositories.SectionsRepository;
import zm.schools.smartschool.Repositories.StudentRepository;
import zm.schools.smartschool.Models.School;
import zm.schools.smartschool.Services.StudentService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AppUserRepository appUserRepository;
    private final GradeRepository gradeRepository;
    private final SectionsRepository sectionRepository;
    private final GuardianRepository guardianRepository;
    private final SchoolRepository schoolRepository;

    @Override
    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        // Retrieve and validate School, Grade, and Section
        AppUserDTO appUserDTO = studentDTO.getAppUser();

        if (appUserDTO.getSchoolId() == null) {
            throw new IllegalArgumentException("School ID must not be null");
        }
        if (studentDTO.getGradeId() == null) {
            throw new IllegalArgumentException("Grade ID must not be null");
        }
        if (studentDTO.getSectionId() == null) {
            throw new IllegalArgumentException("Section ID must not be null");
        }

        Optional<School> optionalSchool = schoolRepository.findById(appUserDTO.getSchoolId());
        Optional<Grade> optionalGrade = gradeRepository.findById(studentDTO.getGradeId());
        Optional<Section> optionalSection = sectionRepository.findById(studentDTO.getSectionId());

        if (!optionalSchool.isPresent()) {
            throw new IllegalArgumentException("Invalid School ID");
        }
        if (!optionalGrade.isPresent()) {
            throw new IllegalArgumentException("Invalid Grade ID");
        }
        if (!optionalSection.isPresent()) {
            throw new IllegalArgumentException("Invalid Section ID");
        }

        // Retrieve entities from Optionals
        School school = optionalSchool.get();
        Grade grade = optionalGrade.get();
        Section section = optionalSection.get();

        // Validate examination number for grades 8-12
        String gradeName = grade.getMasterGrade().getName();
        if ((gradeName.equals("8") || gradeName.equals("9") || gradeName.equals("10") || gradeName.equals("11")
                || gradeName.equals("12")) &&
                (studentDTO.getExaminationNumber() == null || studentDTO.getExaminationNumber().isEmpty())) {
            throw new InvalidExaminationNumberException(
                    "Examination number cannot be null or empty for students in grade 8 and above");
        }

        // Create AppUser entity
        AppUser appUser = new AppUser();
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setGender(appUserDTO.getGender());
        appUser.setDateOfBirth(appUserDTO.getDateOfBirth());

        // Convert AddressDto to Address
        Address address = new Address();
        if (appUserDTO.getAddress() != null) {
            address.setHouseNumber(appUserDTO.getAddress().getHouseNumber());
            address.setStreetName(appUserDTO.getAddress().getStreetName());
        }
        appUser.setAddress(address);
        appUser.setSchool(school);

        
        // Create Student entity and save
        Student student = new Student();
        student.setAppUser(appUser);
        student.setGrade(grade);
        student.setSection(section);
        student.setExaminationNumber(studentDTO.getExaminationNumber());

        // Create and associate guardian if provided
        Guardian guardian = null;
        if (studentDTO.getGuardianDto() != null) {
            Optional<Guardian> optionalGuardian = guardianRepository.findByName(studentDTO.getGuardianDto().getName());
            if (!optionalGuardian.isPresent()) {
                guardian = new Guardian();
                guardian.setName(studentDTO.getGuardianDto().getName());
                guardian.setPhoneNumber(studentDTO.getGuardianDto().getPhoneNumber());

                // Convert Guardian's AddressDto to Address
                Address guardianAddress = new Address();
                AddressDto guardianAddressDto = studentDTO.getGuardianDto().getAddress();
                if (guardianAddressDto != null) {
                    guardianAddress.setHouseNumber(guardianAddressDto.getHouseNumber());
                    guardianAddress.setStreetName(guardianAddressDto.getStreetName());
                }
                guardian.setAddress(guardianAddress);

                // Save new guardian
                guardian = guardianRepository.save(guardian);
            } else {
                guardian = optionalGuardian.get();
            }

            // Set guardian to AppUser
            student.setGuardian(guardian);
        }

        //save student
        student = studentRepository.save(student); 

        // Save AppUser entity
        appUser = appUserRepository.save(appUser);


        // Return response DTO with saved details
        return new StudentResponseDTO(
            student.getId(),
            new AppUserDTO(
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getGender(),
                appUser.getDateOfBirth(),
                new AddressDto(appUser.getAddress().getHouseNumber(), appUser.getAddress().getStreetName()),
                appUser.getSchool().getId()
            ),
            grade.getMasterGrade().getName(),
            section.getName(),
            student.getExaminationNumber(),
            student.getGuardian() != null 
                ? new GuardianDto(
                    student.getGuardian().getName(),
                    student.getGuardian().getPhoneNumber(),
                    new AddressDto(
                        student.getGuardian().getAddress().getHouseNumber(),
                        student.getGuardian().getAddress().getStreetName())
                ) 
                : null
        );
    }

    @Override
    public StudentResponseDTO getStudentByExaminationNumber(String examinationNumber) {
        // Retrieve the student by examination number, or throw an exception if not found
        Student student = studentRepository.findByExaminationNumber(examinationNumber)
                .orElseThrow(() -> new IllegalArgumentException("No student found with the given examination number"));
    
        // Retrieve associated AppUser and Guardian
        AppUser appUser = student.getAppUser();
        Guardian guardian = student.getGuardian();
    
        // Create GuardianDto if guardian exists, otherwise set to null
        GuardianDto guardianDto = (guardian != null) ? new GuardianDto(
                guardian.getName(),
                guardian.getPhoneNumber(),
                new AddressDto(
                        guardian.getAddress().getHouseNumber(),
                        guardian.getAddress().getStreetName()
                )
        ) : null;
    
        // Create AppUserDTO
        AppUserDTO appUserDTO = new AppUserDTO(
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getGender(),
                appUser.getDateOfBirth(),
                new AddressDto(
                        appUser.getAddress().getHouseNumber(),
                        appUser.getAddress().getStreetName()
                ),
                appUser.getSchool().getId()
              
        );
    
        // Return the fully populated StudentResponseDTO
        return new StudentResponseDTO(
                student.getId(),
                appUserDTO,
                student.getGrade().getMasterGrade().getName(),
                student.getSection().getName(),
                student.getExaminationNumber(),
                guardianDto
        );
    }
    

    @Override
    public StudentResponseDTO updateStudentByExaminationNumber(String examinationNumber, StudentDTO studentDTO) {
        // Retrieve the student by examination number, or throw an exception if not found
        Student student = studentRepository.findByExaminationNumber(examinationNumber)
                .orElseThrow(() -> new IllegalArgumentException("No student found with the given examination number"));
    
        // Retrieve the embedded AppUserDTO from the StudentDTO
        AppUserDTO appUserDTO = studentDTO.getAppUser();
    
        // Validate and retrieve School, Grade, and Section entities
        School school = schoolRepository.findById(appUserDTO.getSchoolId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid School ID"));
        Grade grade = gradeRepository.findById(studentDTO.getGradeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Grade ID"));
        Section section = sectionRepository.findById(studentDTO.getSectionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Section ID"));
    
        // Update the AppUser entity associated with the student
        AppUser appUser = student.getAppUser();
        appUser.setFirstName(appUserDTO.getFirstName());
        appUser.setLastName(appUserDTO.getLastName());
        appUser.setGender(appUserDTO.getGender());
        appUser.setDateOfBirth(appUserDTO.getDateOfBirth());
    
        // Update the Address of the AppUser if provided
        if (appUserDTO.getAddress() != null) {
            Address address = appUser.getAddress();
            address.setHouseNumber(appUserDTO.getAddress().getHouseNumber());
            address.setStreetName(appUserDTO.getAddress().getStreetName());
            appUser.setAddress(address);
        }
    
        // Update the School association of the AppUser
        appUser.setSchool(school);
    
        // Update and associate the Guardian if provided on the StudentDTO
        Guardian guardian = null;
        if (studentDTO.getGuardianDto() != null) {
            // Attempt to find an existing Guardian by name
            Optional<Guardian> optionalGuardian = guardianRepository.findByName(studentDTO.getGuardianDto().getName());
            if (optionalGuardian.isPresent()) {
                guardian = optionalGuardian.get();
            } else {
                // Create a new Guardian if one does not exist
                guardian = new Guardian();
                guardian.setName(studentDTO.getGuardianDto().getName());
                guardian.setPhoneNumber(studentDTO.getGuardianDto().getPhoneNumber());
    
                // Update the Address of the Guardian if provided
                AddressDto guardianAddressDto = studentDTO.getGuardianDto().getAddress();
                if (guardianAddressDto != null) {
                    Address guardianAddress = new Address();
                    guardianAddress.setHouseNumber(guardianAddressDto.getHouseNumber());
                    guardianAddress.setStreetName(guardianAddressDto.getStreetName());
                    guardian.setAddress(guardianAddress);
                }
    
                // Save the new Guardian
                guardian = guardianRepository.save(guardian);
            }
    
            // Set the Guardian on the Student entity
            student.setGuardian(guardian);
        }
    
        // Save the updated AppUser entity
        appUserRepository.save(appUser);
    
        // Update Student entity and save
        student.setGrade(grade);
        student.setSection(section);
        student.setExaminationNumber(studentDTO.getExaminationNumber());
        studentRepository.save(student);
    
        // Return updated response DTO with saved details
        GuardianDto updatedGuardianDto = guardian != null ? new GuardianDto(
                guardian.getName(),
                guardian.getPhoneNumber(),
                new AddressDto(guardian.getAddress().getHouseNumber(), guardian.getAddress().getStreetName())) : null;
    
        AppUserDTO updatedAppUserDTO = new AppUserDTO(
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getGender(),
                appUser.getDateOfBirth(),
                new AddressDto(appUser.getAddress().getHouseNumber(), appUser.getAddress().getStreetName()),
                appUser.getSchool().getId());
    
        return new StudentResponseDTO(
                student.getId(),
                updatedAppUserDTO,
                student.getGrade().getMasterGrade().getName(),
                student.getSection().getName(),
                student.getExaminationNumber(),
                updatedGuardianDto);
    }
    }
