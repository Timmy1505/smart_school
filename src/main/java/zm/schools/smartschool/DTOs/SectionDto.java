package zm.schools.smartschool.DTOs;

import java.util.List;

import lombok.Data;

import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SectionDto {
    private String name;
    private String description;
    private Long schoolId = 1L;
}
