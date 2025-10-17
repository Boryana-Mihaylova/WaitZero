package dev.waitzero.waitzero.service;




import dev.waitzero.waitzero.model.service.RouteServiceModel;
import dev.waitzero.waitzero.model.view.RouteDetailsViewModel;
import dev.waitzero.waitzero.model.view.RouteViewModel;

import java.util.List;



public interface RouteService {

    List<RouteViewModel> findAllRoutesView();

    void addNewRoute(RouteServiceModel routeServiceModel);

    RouteDetailsViewModel findRouteById(Long id);
}
