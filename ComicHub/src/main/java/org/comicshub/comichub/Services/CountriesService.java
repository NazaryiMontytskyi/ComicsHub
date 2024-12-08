package org.comicshub.comichub.Services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.comicshub.comichub.Models.Country;
import org.comicshub.comichub.Repositories.CountriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CountriesService {


    @Autowired
    CountriesRepository countriesRepository;

    public List<Country> findAll(){

        return this.countriesRepository.findAll();
    }

    public Country save(Country country) {

        return this.countriesRepository.save(country);
    }

    public Country findById(long id){
        return this.countriesRepository.findById(id);
    }

    public Country deleteById(long id){
        return this.countriesRepository.deleteById(id);
    }

}
