package bg.softuni.pathfinder.service;

import bg.softuni.pathfinder.repository.PictureRepository;


import java.util.List;


public interface PictureService {

    List<String> findAllUrls();
}
