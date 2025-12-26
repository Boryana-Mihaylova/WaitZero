package dev.waitzero.waitzero.service;


import dev.waitzero.waitzero.model.entity.User;
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
        User user = modelMapper.map(userServiceModel, User.class);

        userRepository.save(user);
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return userRepository
                .findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(Long id, String username) {
        currentUser
                .setUsername(username)
                .setId(id);
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
