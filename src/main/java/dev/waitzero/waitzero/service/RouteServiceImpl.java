package dev.waitzero.waitzero.service;


import dev.waitzero.waitzero.model.entity.Route;
import dev.waitzero.waitzero.model.service.RouteServiceModel;
import dev.waitzero.waitzero.model.view.RouteDetailsViewModel;
import dev.waitzero.waitzero.model.view.RouteViewModel;
import dev.waitzero.waitzero.repository.RouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {


    private final RouteRepository routeRepository;
    private final dev.waitzero.waitzero.service.UserService userService;
    private final CategoryService categoryService;


    private final ModelMapper modelMapper;

    public RouteServiceImpl(RouteRepository routeRepository, dev.waitzero.waitzero.service.UserService userService, CategoryService categoryService, CategoryService categoryService1, UserService userService1, CategoryService categoryService2,
                            ModelMapper modelMapper) {
        this.routeRepository = routeRepository;
        this.userService = userService1;
        this.categoryService = categoryService2;


        this.modelMapper = modelMapper;
    }

    @Override
    public List<RouteViewModel> findAllRoutesView() {
        return routeRepository
                .findAll()
                .stream()
                .map(route ->
                {
                    RouteViewModel routeViewModel = modelMapper.map(route, RouteViewModel.class);
                    if (route.getPictures().isEmpty()) {
                        routeViewModel.setPictureUrl("/images/pic4.jpg");
                    } else {
                        routeViewModel
                                .setPictureUrl(route
                                        .getPictures()
                                        .stream()
                                        .findFirst()
                                        .get()
                                        .getUrl());
                    }

                    return routeViewModel;
                })
                .collect(Collectors.toList());

    }

    @Override
    public void addNewRoute(RouteServiceModel routeServiceModel) {
        Route route = modelMapper.map(routeServiceModel, Route.class);
        route.setAuthor(userService.findCurrentLoginUserEntity());

        route.setCategories(routeServiceModel
                .getCategories()
                .stream()
                .map(categoryService::findCategoryByName)
                .collect(Collectors.toSet()));

        routeRepository.save(route);
    }

    @Override
    public RouteDetailsViewModel findRouteById(Long id) {
        return routeRepository.findById(id).map(route -> modelMapper.map(route, RouteDetailsViewModel.class))
                .orElse(null);
    }
}
