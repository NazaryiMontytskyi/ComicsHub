package org.comicshub.comichub.ValidationForms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    @NotEmpty(message = "The name of user can't be empty!")
    @Size(min = 5, max = 20, message = "The name of user must have at least 5 symbols and 20 symbols as maximum")
    String username;

    @NotEmpty(message = "The email can't be empty")
    @Email(message = "The email has to satisfy the pattern of the address")
    String email;

    @NotEmpty(message = "The password can't be empty!")
    @Size(min = 5, max = 25, message = "The password has to be from 5 to 25 symbols")
    String password;

}
