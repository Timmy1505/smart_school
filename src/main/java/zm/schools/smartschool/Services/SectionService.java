package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.SectionDto;

public interface SectionService {
   
    SectionDto createSection(SectionDto sectionDto);

    SectionDto getSectionById(Long id);

    List<SectionDto> listOfSections();

    void deleteSection(Long id);
    // Other CRUD operations can be added here
}
