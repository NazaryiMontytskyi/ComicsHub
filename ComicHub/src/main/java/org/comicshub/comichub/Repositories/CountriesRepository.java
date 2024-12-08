package org.comicshub.comichub.Repositories;


import org.comicshub.comichub.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Long> {

    List<Country> findAll();

    Country save(Country country);

    Country findById(long id);

    Country deleteById(long id);

}
