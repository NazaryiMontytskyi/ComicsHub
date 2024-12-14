package org.comicshub.comichub.ValidationForms;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicForm {

    @Size(min = 5, max = 35, message = "The title of comic has to be at least 5 symbols and 35 symbols as limit")
    @NotEmpty
    private String title;

    @Size(min = 20, max = 250, message = "The description of comic has to contain at least 20 symbols and 250 symbols as limit")
    @NotEmpty
    private String description;

    @NotEmpty
    @Size(min = 5, max = 35, message = "The name of the author has to contain from 5 to 35 symbols")
    private String author;

    private MultipartFile image;

    private MultipartFile pdfFile;

    public boolean isImageCorrect(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && contentType.startsWith("image/")) {
            return true;
        }
        return false;
    }

    public boolean isPdfFileCorrect(MultipartFile file) {
        return file.getContentType().toLowerCase().contains("pdf");
    }

}
