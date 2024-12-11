package zm.schools.smartschool.Configs;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateEditor extends PropertyEditorSupport {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(LocalDate.parse(text, formatter));
    }

    @Override
    public String getAsText() {
        LocalDate date = (LocalDate) getValue();
        return date != null ? date.format(formatter) : "";
    }
}
