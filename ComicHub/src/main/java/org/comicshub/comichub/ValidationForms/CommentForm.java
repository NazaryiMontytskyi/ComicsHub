package org.comicshub.comichub.ValidationForms;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {

    @Size(min = 3, max = 50)
    @NotEmpty
    String comment;

}
