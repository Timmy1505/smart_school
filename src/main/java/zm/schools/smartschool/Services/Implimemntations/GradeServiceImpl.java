package zm.schools.smartschool.Services.Implimemntations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.MasterGradeDTO;
import zm.schools.smartschool.DTOs.SectionDto;
import zm.schools.smartschool.Exceptions.ResourceNotFoundException;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Services.GradeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GradeDTO createGrade(GradeDTO gradeDTO) {
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        grade = gradeRepository.save(grade);
        return modelMapper.map(grade, GradeDTO.class);
    }

    @Override
    public GradeDTO updateGrade(Long id, GradeDTO gradeDTO) {
        Grade existingGrade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", id));
        modelMapper.map(gradeDTO, existingGrade);
        Grade updatedGrade = gradeRepository.save(existingGrade);
        return modelMapper.map(updatedGrade, GradeDTO.class);
    }

    @Override
    public GradeDTO getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", id));
        return modelMapper.map(grade, GradeDTO.class);
    }

@Override
public List<GradeDTO> getAllGrades() {
    return gradeRepository.findAll().stream()
            .map(grade -> {
                // Map MasterGrade to MasterGradeDTO
                MasterGradeDTO masterGradeDTO = modelMapper.map(grade.getMasterGrade(), MasterGradeDTO.class);

                // Construct GradeDTO
                return new GradeDTO(
                        grade.getId(),
                        grade.getSchool() != null ? grade.getSchool().getId() : null, // Handle potential null School
                        masterGradeDTO
                );
            })
            .collect(Collectors.toList());
}


    @Override
    public void deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", id));
        gradeRepository.delete(grade);
    }
}
