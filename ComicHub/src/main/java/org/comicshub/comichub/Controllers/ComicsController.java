package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.Country;
import org.comicshub.comichub.Models.Genre;
import org.comicshub.comichub.Models.PDFFile;
import org.comicshub.comichub.Services.ComicsService;
import org.comicshub.comichub.Services.CountriesService;
import org.comicshub.comichub.Services.GenresService;
import org.comicshub.comichub.Services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ComicsController {

    ComicsService comicsService;
    GenresService genresService;
    CountriesService countriesService;
    PdfService pdfService;

    @Autowired
    public ComicsController(ComicsService comicsService, GenresService genresService, CountriesService countriesService,
                            PdfService pdfService) {
        this.comicsService = comicsService;
        this.genresService = genresService;
        this.countriesService = countriesService;
        this.pdfService = pdfService;
    }

    @GetMapping("/comics/index")
    public String index(Model model){
        model.addAttribute("comics", this.comicsService.index());
        return "comics/index";
    }

    @GetMapping("/comics/new")
    public String createComic(Model model){
        model.addAttribute("genres", this.genresService.findAll());
        model.addAttribute("countries", this.countriesService.findAll());
        return "comics/new";
    }


    @PostMapping("/comics/new")
    public String postComic(
            @RequestParam("title") String title,
            @RequestParam("desc") String desc,
            @RequestParam("genre") long genreId,
            @RequestParam("author") String author,
            @RequestParam("country") long countryId,
            @RequestParam("pdf-file") MultipartFile pdfFile
    ) throws IOException
    {
        System.out.println(title);
        System.out.println(desc);
        System.out.println(genreId);
        System.out.println(author);
        System.out.println(countryId);
        System.out.println(pdfFile.getOriginalFilename());

        Genre targetGenre = this.genresService.findById(genreId);
        Country targetCountry = this.countriesService.findById(countryId);
        PDFFile targetFile = new PDFFile();
        targetFile.fromMultipartFile(pdfFile);

        this.pdfService.save(targetFile);

        Comic comic = new Comic();
        comic.setTitle(title);
        comic.setDescription(desc);
        comic.setGenre(targetGenre);
        comic.setAuthor(author);
        comic.setCountry(targetCountry);
        comic.setPdfFile(targetFile);
        this.comicsService.save(comic);
        return "redirect:/comics/index";
    }

    @GetMapping("/comics/{id}")
    public String infoComic(Model model, @PathVariable long id){
        model.addAttribute("comic", this.comicsService.findById(id));
        return "comics/comic-info";
    }

}
