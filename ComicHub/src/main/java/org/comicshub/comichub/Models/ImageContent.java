package org.comicshub.comichub.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class ImageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "file_content")
    private byte[] fileContent;

    public void fromMultipartFile(MultipartFile file) throws IOException {
        this.fileName = file.getName();
        this.originalFileName = file.getOriginalFilename();
        this.contentType = file.getContentType();
        this.fileContent = file.getBytes();
    }

}
