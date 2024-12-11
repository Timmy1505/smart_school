package zm.schools.smartschool.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardianDto {
    private Long id;
    private String name;
    private String phoneNumber;
    private AddressDto address;
    
    public GuardianDto(String name, String phoneNumber, AddressDto address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}