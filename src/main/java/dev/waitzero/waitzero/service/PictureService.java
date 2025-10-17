package dev.waitzero.waitzero.service;

import dev.waitzero.waitzero.repository.PictureRepository;


import java.util.List;


public interface PictureService {

    List<String> findAllUrls();
}
