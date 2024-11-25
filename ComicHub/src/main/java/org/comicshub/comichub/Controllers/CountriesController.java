package org.comicshub.comichub.Controllers;

import org.comicshub.comichub.Models.Country;
import org.comicshub.comichub.Services.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/countries")
public class CountriesController {


    private CountriesService countriesService;

    @Autowired
    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("countries", countriesService.findAll());
        return "countries/index";
    }

    @GetMapping("/new")
    public String createNewCountry(){
        return "countries/new";
    }


    @PostMapping("/new")
    public String postCountry(
            @RequestParam("short_name") String shortName,
            @RequestParam("full_name") String fullName
    ){
        Country newCountry = new Country();
        newCountry.setShortName(shortName);
        newCountry.setFullName(fullName);
        this.countriesService.save(newCountry);
        return "redirect:/countries/index";
    }

}
