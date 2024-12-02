package org.comicshub.comichub.Services;

import org.comicshub.comichub.Models.UserRead;
import org.comicshub.comichub.Repositories.UserReadRepository;
import org.comicshub.comichub.Security.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReadService {

    UserReadRepository userReadRepository;

    public UserReadService(UserReadRepository userReadRepository) {
        this.userReadRepository = userReadRepository;
    }

    public List<UserRead> findAllByUser(User user) {
        return this.userReadRepository.findByUser(user);
    }

    public UserRead save(UserRead userRead) {
        return this.userReadRepository.save(userRead);
    }

}
