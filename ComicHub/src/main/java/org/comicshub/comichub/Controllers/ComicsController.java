package org.comicshub.comichub.Controllers;

import jakarta.validation.Valid;
import org.comicshub.comichub.Models.*;
import org.comicshub.comichub.Security.Role;
import org.comicshub.comichub.Services.*;
import org.comicshub.comichub.ValidationForms.ComicForm;
import org.comicshub.comichub.ValidationForms.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

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
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
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
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));

        return "comics/index";
    }

    @GetMapping("/comics/new")
    @PreAuthorize("hasRole('USER')")
    public String createComic(Model model, Principal principal){
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
        model.addAttribute("genres", this.genresService.findAll());
        model.addAttribute("countries", this.countriesService.findAll());
        model.addAttribute("username", principal.getName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));
        model.addAttribute("comic", new ComicForm());
        model.addAttribute("pdf_correct", true);
        model.addAttribute("country_correct", true);
        model.addAttribute("genre_correct", true);
        model.addAttribute("image_correct", true);


        return "comics/new";
    }

    @GetMapping("/comics/edit/{id}")
    public String editComic(@PathVariable("id") long comicId, Model model, Principal principal){
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
        model.addAttribute("comic", comicsService.findById(comicId));
        model.addAttribute("genres", this.genresService.findAll());
        model.addAttribute("countries", this.countriesService.findAll());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));

        return "comics/edit";
    }

    @PatchMapping("/comics/edit")
    public String editComic(@RequestParam("comic_id") long comicId,
                            @RequestParam("title") String title,
                            @RequestParam("desc") String description,
                            @RequestParam("country_id") long countryId,
                            @RequestParam("genre_id") long genreId,
                            @RequestParam("pdf-file") @DefaultValue(value = "null") MultipartFile pdfFile,
                            @RequestParam("title-image") @DefaultValue(value = "null") MultipartFile titleImage) throws IOException {

        Comic comicToEdit = comicsService.findById(comicId);

        if(!comicToEdit.getTitle().equals(title)){
            comicToEdit.setTitle(title);
        }
        if(!comicToEdit.getDescription().equals(description)){
            comicToEdit.setDescription(description);
        }
        if(comicToEdit.getCountry().getId() != countryId){
            Country country = this.countriesService.findById(countryId);
            comicToEdit.setCountry(country);
        }
        if(comicToEdit.getGenre().getId() != genreId){
            Genre genre = this.genresService.findById(genreId);
            comicToEdit.setGenre(genre);
        }


        if(!pdfFile.isEmpty()){
            PDFFile receivedPdfFile = new PDFFile();
            receivedPdfFile.fromMultipartFile(pdfFile);
            if(!Arrays.equals(receivedPdfFile.getFileContent(), comicToEdit.getPdfFile().getFileContent())){
                this.pdfService.deleteById(comicToEdit.getPdfFile().getId());
                comicToEdit.setPdfFile(receivedPdfFile);
            }
        }


        if(!titleImage.isEmpty()){
            ImageContent receivedImageContent = new ImageContent();
            receivedImageContent.fromMultipartFile(titleImage);
            if(!Arrays.equals(receivedImageContent.getFileContent(), comicToEdit.getTitleImage().getFileContent())){
                this.imageService.deleteById(comicToEdit.getTitleImage().getId());
                comicToEdit.setTitleImage(receivedImageContent);
            }
        }

        this.comicsService.update(comicToEdit);
        return "redirect:/comics/" + comicId;

    }

    @PostMapping("/comics/new")
    public String postComic(
            @ModelAttribute("comic") @Valid ComicForm comicForm,
            BindingResult bindingResult,
            Model model,
            @RequestParam("genre") long genreId,
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

        boolean isPdfFileCorrect = comicForm.isPdfFileCorrect(pdfFile);
        boolean isImageFileCorrect = comicForm.isImageCorrect(titleImage);
        boolean isGenreCorrect = genreId != 0;
        boolean isCountryCorrect = countryId != 0;

        model.addAttribute("pdf_correct", isPdfFileCorrect);
        model.addAttribute("country_correct", isCountryCorrect);
        model.addAttribute("genre_correct", isGenreCorrect);
        model.addAttribute("image_correct", isImageFileCorrect);

        if(bindingResult.hasErrors()){
            return "/comics/new";
        }

        this.pdfService.save(targetFile);
        this.imageService.save(imageContent);

        Comic comic = new Comic();
        comic.setTitle(comicForm.getTitle());
        comic.setDescription(comicForm.getDescription());
        comic.setGenre(targetGenre);
        comic.setAuthor(comicForm.getAuthor());
        comic.setCountry(targetCountry);
        comic.setPdfFile(targetFile);
        comic.setTitleImage(imageContent);
        this.comicsService.save(principal,comic);
        return "redirect:/comics/index";
    }

    @GetMapping("/comics/{id}")
    public String infoComic(Model model, @PathVariable long id, Principal principal){
        boolean isAdmin = false;
        if(this.userService.getUserByPrincipal(principal) != null)
        {
            isAdmin = this.userService.getUserByPrincipal(principal).getRoles().contains(Role.ROLE_ADMIN);
        }
        model.addAttribute("comic", this.comicsService.findById(id));
        model.addAttribute("comments", this.comicCommentsService.findByComicId(id));
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("user", this.userService.getUserByPrincipal(principal));
        model.addAttribute("comment", new CommentForm());

        return "comics/comic-info";
    }

    @DeleteMapping("/comics/delete/{id}")
    public String deleteComic(@PathVariable long id)
    {
        this.comicsService.deleteComic(id);
        return "redirect:/comics/index";
    }

}
