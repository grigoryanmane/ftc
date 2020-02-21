package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> editUser(UserEditRequest userEditRequest, Long id) {
        //TODO::CHANGE THE LOGIC IN HERE
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (!userEditRequest.getFirstName().isEmpty()) {
                user.get().setFirstName(userEditRequest.getFirstName());
            }
            if (!userEditRequest.getLastName().isEmpty()) {
                user.get().setLastName(userEditRequest.getLastName());
            }
            if (!userEditRequest.getPhoneNumber().isEmpty()) {
                user.get().setPhoneNumber(userEditRequest.getPhoneNumber());
            }
            if (!(userEditRequest.getRegion() == null)) {
                user.get().setRegion(userEditRequest.getRegion());
            }
            return user;
        } else {
            throw new UserNotFound();
        }


    }
}
