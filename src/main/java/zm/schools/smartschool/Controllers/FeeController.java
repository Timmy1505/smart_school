package zm.schools.smartschool.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.FeeDTO;
import zm.schools.smartschool.Services.FeeService;

@RestController
@RequestMapping("/api/fees")
@AllArgsConstructor
public class FeeController {
    private final FeeService feeService;

    @PostMapping
    public ResponseEntity<FeeDTO> createFee(@RequestBody FeeDTO feeDTO) {
        FeeDTO createdFee = feeService.createFee(feeDTO);
        return new ResponseEntity<>(createdFee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeDTO> updateFee(@PathVariable Long id, @RequestBody FeeDTO feeDTO) {
        FeeDTO updatedFee = feeService.updateFee(id, feeDTO);
        return new ResponseEntity<>(updatedFee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        feeService.deleteFee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeDTO> getFee(@PathVariable Long id) {
        FeeDTO feeDTO = feeService.getFee(id);
        return new ResponseEntity<>(feeDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FeeDTO>> getAllFees() {
        List<FeeDTO> fees = feeService.getAllFees();
        return new ResponseEntity<>(fees, HttpStatus.OK);
    }
}
