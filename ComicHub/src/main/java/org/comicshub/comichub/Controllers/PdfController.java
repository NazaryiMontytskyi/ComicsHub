package org.comicshub.comichub.Controllers;


import org.comicshub.comichub.Models.PDFFile;
import org.comicshub.comichub.Services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Controller
public class PdfController {

    PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }


    @GetMapping("/viewPdf/{fileId}")
    public ResponseEntity<Resource> viewPdf(@PathVariable("fileId") long fileId) {
        PDFFile pdfFile = pdfService.findById(fileId);
        if(pdfFile != null) {
            byte[] fileContent = pdfFile.getFileContent();
            Resource resource = new ByteArrayResource(fileContent);

            String fileName = pdfFile.getOriginFileName();
            try {
                fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

            return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
