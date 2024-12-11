package zm.schools.smartschool.Exceptions;

public class InvalidExaminationNumberException extends RuntimeException {

    public InvalidExaminationNumberException(String message) {
        super(message);
    }

    public InvalidExaminationNumberException(String centreNumber, String examinationNumber) {
        super("Invalid examination number '" + examinationNumber + "'. " +
              "The first 6 digits must match the centre number '" + centreNumber + "'.");
    }
}