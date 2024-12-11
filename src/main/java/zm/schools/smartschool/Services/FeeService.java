package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.FeeDTO;

public interface FeeService {
    FeeDTO createFee(FeeDTO feeDTO);
    FeeDTO updateFee(Long feeId, FeeDTO feeDTO);
    void deleteFee(Long feeId);
    FeeDTO getFee(Long feeId);
    List<FeeDTO> getAllFees();
}



