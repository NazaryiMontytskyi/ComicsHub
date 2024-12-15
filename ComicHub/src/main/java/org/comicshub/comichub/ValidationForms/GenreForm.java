package org.comicshub.comichub.ValidationForms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreForm {

    @Size(min = 3, max = 25)
    @NotEmpty
    String name;

    @Size(min = 5, max = 40)
    String description;
}
