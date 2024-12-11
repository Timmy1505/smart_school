package zm.schools.smartschool.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolName;
    private String centreNumber;
    private String poBox;
    private Boolean isPrivate;
    private String category;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Grade> grades = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "academic_session_id", referencedColumnName = "id")
    private AcademicSession academicSession;

    @OneToMany(mappedBy = "school")
    @JsonBackReference
    private List<Fee> fees = new ArrayList<>();

    public School(String schoolName, String centreNumber, String poBox, Boolean isPrivate, String category) {
        this.schoolName = schoolName;
        this.centreNumber = centreNumber;
        this.poBox = poBox;
        this.isPrivate = isPrivate;
        this.category = category;
    }

    
}

