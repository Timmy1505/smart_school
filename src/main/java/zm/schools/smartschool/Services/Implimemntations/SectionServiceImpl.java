package zm.schools.smartschool.Services.Implimemntations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.SectionDto;
import zm.schools.smartschool.Models.School;
import zm.schools.smartschool.Models.Section;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Repositories.SectionsRepository;
import zm.schools.smartschool.Services.SectionService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionsRepository sectionRepository;
    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SectionDto createSection(SectionDto sectionDto) {
        // Create and save the section
        Section section = new Section();
        section.setName(sectionDto.getName());
        section.setDescription(sectionDto.getDescription());

        School school = schoolRepository.findById(sectionDto.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found"));
        section.setSchool(school);

        Section savedSection = sectionRepository.save(section);

        // Return the saved section as DTO
        return modelMapper.map(savedSection, SectionDto.class);
    }
    

    @Override
    public SectionDto getSectionById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section ID: " + id));
        return modelMapper.map(section, SectionDto.class);
    }

    @Override
    public List<SectionDto> listOfSections() {
        return sectionRepository.findAll().stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSection(Long id) {
        if (!sectionRepository.existsById(id)) {
            throw new IllegalArgumentException("Invalid section ID: " + id);
        }
        sectionRepository.deleteById(id);
    }
}

