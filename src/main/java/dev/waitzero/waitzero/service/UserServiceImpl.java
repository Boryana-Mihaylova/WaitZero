package dev.waitzero.waitzero.service;


import dev.waitzero.waitzero.model.entity.User;
import dev.waitzero.waitzero.model.entity.UserRole;
import dev.waitzero.waitzero.model.service.UserServiceModel;
import dev.waitzero.waitzero.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final dev.waitzero.waitzero.util.CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           dev.waitzero.waitzero.util.CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {

        if (userServiceModel.getUsername() != null) {
            userServiceModel.setUsername(userServiceModel.getUsername().trim());
        }

        User user = modelMapper.map(userServiceModel, User.class);

        user.setRole(UserRole.USER);

        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {

        if (username != null) username = username.trim();

        return userRepository
                .findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(Long id, String username) {

        User user = userRepository.findById(id).orElseThrow();

        currentUser
                .setUsername(username)
                .setId(id)
                .setRole(user.getRole());
    }

    @Override
    public void logout() {
        currentUser
                .setId(null)
                .setUsername(null);
    }

    @Override
    public UserServiceModel findById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public boolean isNameExists(String username) {

        if (username != null) {
            username = username.trim();
        }
        
        return userRepository
                .findByUsername(username)
                .isPresent();
    }

    @Override
    public User findCurrentLoginUserEntity() {
        return userRepository
                .findById(currentUser.getId())
                .orElse(null);
    }
}
