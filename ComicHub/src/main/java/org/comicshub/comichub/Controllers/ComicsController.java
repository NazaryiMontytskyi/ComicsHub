package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.*;
import org.comicshub.comichub.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ComicsController {

    ComicsService comicsService;
    GenresService genresService;
    CountriesService countriesService;
    PdfService pdfService;
    ImageService imageService;
    ComicCommentsService comicCommentsService;
    UserService userService;

    @Autowired
    public ComicsController(ComicsService comicsService, GenresService genresService, CountriesService countriesService,
                            PdfService pdfService, ImageService imageService,
                            ComicCommentsService comicCommentsService,
                            UserService userService) {
        this.comicsService = comicsService;
        this.genresService = genresService;
        this.countriesService = countriesService;
        this.pdfService = pdfService;
        this.imageService = imageService;
        this.comicCommentsService = comicCommentsService;
        this.userService = userService;
    }

    @GetMapping("/comics/index")
    public String index(@RequestParam(value = "genreId", required = false, defaultValue = "0") long genreId,
                        @RequestParam(value = "countryId", required = false, defaultValue = "0") long countryId,
                        @RequestParam(value = "searchString", required = false, defaultValue = "") String searchQuery,
                        @PageableDefault(size = 3) Pageable pageable, Principal principal,
                        Model model){
        Genre genre = null;
        Country country = null;
        if(genreId != 0){
            genre = this.genresService.findById(genreId);
        }
        if(countryId != 0){
            country = this.countriesService.findById(countryId);
        }

        Page<Comic> content = this.comicsService.getFilteredComics(genre, country, pageable);
        if(!searchQuery.isEmpty()){
            content = this.comicsService.getApproximateSearchComic(searchQuery, pageable);
        }
        model.addAttribute("all_genres", this.genresService.findAll());
        model.addAttribute("all_countries", this.countriesService.findAll());
        model.addAttribute("genre", genre);
        model.addAttribute("country", country);
        model.addAttribute("comics", content);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));
        return "comics/index";
    }

    @GetMapping("/comics/new")
    public String createComic(Model model, Principal principal){
        model.addAttribute("genres", this.genresService.findAll());
        model.addAttribute("countries", this.countriesService.findAll());
        model.addAttribute("username", principal.getName());
        return "comics/new";
    }


    @PostMapping("/comics/new")
    public String postComic(
            @RequestParam("title") String title,
            @RequestParam("desc") String desc,
            @RequestParam("genre") long genreId,
            @RequestParam("author") String author,
            @RequestParam("country") long countryId,
            @RequestParam("pdf-file") MultipartFile pdfFile,
            @RequestParam("title-image") MultipartFile titleImage,
            Principal principal
    ) throws IOException
    {

        Genre targetGenre = this.genresService.findById(genreId);
        Country targetCountry = this.countriesService.findById(countryId);
        PDFFile targetFile = new PDFFile();
        targetFile.fromMultipartFile(pdfFile);
        ImageContent imageContent = new ImageContent();
        imageContent.fromMultipartFile(titleImage);

        this.pdfService.save(targetFile);
        this.imageService.save(imageContent);

        Comic comic = new Comic();
        comic.setTitle(title);
        comic.setDescription(desc);
        comic.setGenre(targetGenre);
        comic.setAuthor(author);
        comic.setCountry(targetCountry);
        comic.setPdfFile(targetFile);
        comic.setTitleImage(imageContent);
        this.comicsService.save(principal,comic);
        return "redirect:/comics/index";
    }

    @GetMapping("/comics/{id}")
    public String infoComic(Model model, @PathVariable long id){
        model.addAttribute("comic", this.comicsService.findById(id));
        model.addAttribute("comments", this.comicCommentsService.findByComicId(id));
        return "comics/comic-info";
    }

}
