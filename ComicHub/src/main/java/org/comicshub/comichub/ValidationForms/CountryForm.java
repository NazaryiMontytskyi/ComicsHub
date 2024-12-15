package org.comicshub.comichub.ValidationForms;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryForm {

    @NotEmpty
    @Size(min = 1, max = 5)
    String shortName;

    @NotEmpty
    @Size(min = 2, max = 25)
    String longName;

}
