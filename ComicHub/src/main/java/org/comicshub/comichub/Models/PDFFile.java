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
@Table(name = "pdf_files")
public class PDFFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "origin_file_name")
    private String originFileName;

    @Column(name = "file_format")
    private String fileFormat;

    @Lob
    @Column(name="file_content")
    private byte[] fileContent;

    public void fromMultipartFile(MultipartFile multipart) throws IOException {
        this.fileName = multipart.getName();
        this.originFileName = multipart.getOriginalFilename();
        this.fileContent = multipart.getBytes();
        this.fileFormat = multipart.getContentType();
    }

}
