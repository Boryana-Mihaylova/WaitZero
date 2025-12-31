package dev.waitzero.waitzero.service;


import dev.waitzero.waitzero.model.entity.User;
import dev.waitzero.waitzero.model.service.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);



    UserServiceModel findUserByUsernameAndPassword(String username, String password);



    void loginUser(Long id, String username);

    void logout();

    UserServiceModel findById(Long id);

    boolean isNameExists(String username);

    User findCurrentLoginUserEntity();
}
