package org.comicshub.comichub.Controllers;


import org.comicshub.comichub.Models.Genre;
import org.comicshub.comichub.Services.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
            @RequestParam("name") String name,
            @RequestParam("description") String description
    ){
        Genre newGenre = new Genre();
        newGenre.setName(name);
        newGenre.setDescription(description);
        genresService.save(newGenre);
        return "redirect:/admin/index";
    }

    @DeleteMapping("/delete")
    public String deleteGenre(@RequestParam("genre_id") long genreId){
        this.genresService.deleteById(genreId);
        return "redirect:/admin/index";
    }


}
