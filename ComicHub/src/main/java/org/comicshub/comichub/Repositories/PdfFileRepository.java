package org.comicshub.comichub.Repositories;

import org.comicshub.comichub.Models.PDFFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdfFileRepository extends JpaRepository<PDFFile, Long> {

    @Override
    List<PDFFile> findAll();

    PDFFile findById(long id);

    PDFFile save(PDFFile pdfFile);

    PDFFile deleteById(long id);
}
