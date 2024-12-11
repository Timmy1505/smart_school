package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.SchoolDto;

public interface SchoolService {
    SchoolDto createSchool(SchoolDto schoolDto);

    SchoolDto getSchoolById(Long id);

    List<SchoolDto> getAllSchools();

    SchoolDto updateSchool(Long id, SchoolDto schoolDto);
}
