package org.comicshub.comichub.Controllers;


import jakarta.validation.Valid;
import org.comicshub.comichub.Models.Genre;
import org.comicshub.comichub.Services.GenresService;
import org.comicshub.comichub.ValidationForms.GenreForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/genres")
public class GenresController {

    private GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }


    @GetMapping("/index")
    public String index(Model model){
       model.addAttribute("genres", genresService.findAll());
       return "genres/index";
    }

    @GetMapping("/new")
    public String newGenre(Model model){
        return "genres/new";
    }

    @PostMapping("/new")
    public String postGenre(
            @ModelAttribute("genre") @Valid GenreForm genreForm,
            BindingResult bindingResult,
            Model model
            ){

        if(bindingResult.hasErrors()){
            return "redirect:/admin/index";
        }

        Genre newGenre = new Genre();
        newGenre.setName(genreForm.getName());
        newGenre.setDescription(genreForm.getDescription());
        genresService.save(newGenre);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/delete")
    public String deleteGenre(@RequestParam("genre_id") long genreId){
        this.genresService.deleteById(genreId);
        return "redirect:/admin/index";
    }


}
