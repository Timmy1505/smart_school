package zm.schools.smartschool.Services.Implimemntations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.FeeDTO;
import zm.schools.smartschool.Models.Fee;
import zm.schools.smartschool.Repositories.FeeRepository;
import zm.schools.smartschool.Services.FeeService;

@Service
@AllArgsConstructor
public class FeeServiceImpl implements FeeService {
    private final FeeRepository feeRepository;
    private final ModelMapper modelMapper;

    @Override
    public FeeDTO createFee(FeeDTO feeDTO) {
        Fee fee = modelMapper.map(feeDTO, Fee.class);
        // Perform additional operations if needed
        Fee savedFee = feeRepository.save(fee);
        return modelMapper.map(savedFee, FeeDTO.class);
    }


    @Override
    public FeeDTO updateFee(Long feeId, FeeDTO feeDTO) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Fee ID"));
        modelMapper.map(feeDTO, fee);
        Fee updatedFee = feeRepository.save(fee);
        return modelMapper.map(updatedFee, FeeDTO.class);
    }

    @Override
    public void deleteFee(Long feeId) {
        feeRepository.deleteById(feeId);
    }

    @Override
    public FeeDTO getFee(Long feeId) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Fee ID"));
        return modelMapper.map(fee, FeeDTO.class);
    }

    @Override
    public List<FeeDTO> getAllFees() {
        List<Fee> fees = feeRepository.findAll();
        return fees.stream()
                .map(fee -> modelMapper.map(fee, FeeDTO.class))
                .collect(Collectors.toList());
    }
}
