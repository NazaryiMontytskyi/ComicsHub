package org.comicshub.comichub.Services;

import org.comicshub.comichub.Models.Comic;
import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Repositories.UserReadRepository;
import org.comicshub.comichub.Security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserReadService {

    UserReadRepository userReadRepository;

    @Autowired
    public UserReadService(UserReadRepository userReadRepository) {
        this.userReadRepository = userReadRepository;
    }

    public List<UserRead> findAllByUser(User user) {
        return this.userReadRepository.findByUser(user);
    }

    public UserRead save(UserRead userRead) {
        return this.userReadRepository.save(userRead);
    }

    @Transactional
    public void removeFromList(User user, Comic comic) {
        this.userReadRepository.deleteByUserAndComic(user, comic);
    }

}
