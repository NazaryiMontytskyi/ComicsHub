package org.comicshub.comichub.Services;

import org.comicshub.comichub.Models.PDFFile;
import org.comicshub.comichub.Repositories.PdfFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfService {

    PdfFileRepository pdfFileRepository;

    @Autowired
    public PdfService(PdfFileRepository pdfFileRepository) {
        this.pdfFileRepository = pdfFileRepository;
    }

    public List<PDFFile> findByAll(){
        return pdfFileRepository.findAll();
    }

    public PDFFile save(PDFFile pdfFile) {
        return pdfFileRepository.save(pdfFile);
    }

    public PDFFile findById(long id) {
        return pdfFileRepository.findById(id);
    }

    public PDFFile deleteById(long id)
    {
        return this.pdfFileRepository.deleteById(id);
    }

}
