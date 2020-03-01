package aca.project.ftc.auth;

import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFound {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new User(user.get().getUsername(),user.get().getPassword(),
                    new ArrayList<>());
        } else {
            throw new UserNotFound("USER_NOT_FOUND_WITH_USERNAME: " + username);
        }
    }
}
