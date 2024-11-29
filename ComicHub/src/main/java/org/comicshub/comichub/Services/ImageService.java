package org.comicshub.comichub.Services;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.comicshub.comichub.Models.ImageContent;
import org.comicshub.comichub.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ImageService {

    ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public List<ImageContent> findAll(){
        return imageRepository.findAll();
    }

    @Transactional
    public ImageContent findById(long id){
        return imageRepository.findById(id);
    }

    public ImageContent save(ImageContent image){
        return imageRepository.save(image);
    }

    public ImageContent deleteById(long id){
        return imageRepository.deleteById(id);
    }

}
