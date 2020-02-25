package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.entity.UserProductModel;
import aca.project.ftc.model.dto.request.UserProductRequest;
import aca.project.ftc.model.dto.response.User;
import aca.project.ftc.model.dto.response.UserProductResponseDto;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationService authenticationService;

    //TODO:: Add three calls here: checkUsername,addNotification,checkNotification

    public List<UserProductResponseDto> userProducts(Long id) {
        List<UserProductModel> userProductModel = userProductRepository.findByUserId(id);
        if (!userProductModel.isEmpty()) {
            List<UserProductResponseDto> userProductResponseDtoList = new ArrayList<>();
            int i = 0;
            while (i < userProductModel.size()) {
                UserProductResponseDto userProductResponseDto = setUserProductDto(userProductModel.get(i));
                userProductResponseDtoList.add(userProductResponseDto);
                i++;
            }
            return userProductResponseDtoList;
        } else {
            return Collections.emptyList();
        }
    }

    public UserProductResponseDto addUserProduct(UserProductRequest userProductRequest) {
        UserProductModel userProductModel = new UserProductModel();
        //TODO:: SHOULD I DO CHECKS FOR isPresent()??
        userProductModel.setProduct(productRepository.findById(userProductRequest.getProductId()).get());
        userProductModel.setUser(userRepository.findById(userProductRequest.getUserId()).get());
        userProductModel.setAmount(userProductRequest.getAmount());
        userProductModel.setQuantity(userProductRequest.getQuantity());
        userProductModel.setDescription(userProductRequest.getDescription());
        userProductRepository.save(userProductModel);
        return setUserProductDto(userProductModel);

    }

    public User getUserData(Long id) {
        Optional<UserModel> userModel = userRepository.findById(id);
        if (userModel.isPresent()) {
            return authenticationService.getUserResponseData(userModel.get());
        }
        throw new UserNotFound("USER_NOT_FOUND");
    }

    public UserProductResponseDto editUserProduct(UserProductRequest userProductRequest) {
        UserProductModel userProductModel = new UserProductModel();
        try {
            if (userProductRepository.findById(userProductRequest.getId()).isPresent()) {
                userProductModel.setId(userProductRequest.getId());
            }//TODO can be replaced with exists
            if (userRepository.findById(userProductRequest.getUserId()).isPresent()) {
                userProductModel.setUser(userRepository.findById(userProductRequest.getUserId()).get());
            }
            if (productRepository.findById(userProductRequest.getProductId()).isPresent()) {
                userProductModel.setProduct((productRepository.findById(userProductRequest.getProductId())).get());
            }
            if (userProductRequest.getAmount() != null) {
                userProductModel.setAmount(userProductRequest.getAmount());
            }
            if (userProductRequest.getQuantity() != null) {
                userProductModel.setQuantity(userProductRequest.getQuantity());
            }
            if (userProductRequest.getDescription() != null) {
                userProductModel.setDescription(userProductRequest.getDescription());
            }
            userProductRepository.save(userProductModel);
            return setUserProductDto(userProductModel);
        } catch (Exception e) {
            throw new UserNotFound("INVALID_REQUEST_BODY");
        }
    }

    public UserProductResponseDto setUserProductDto(UserProductModel userProductModel) {
        UserProductResponseDto userProductResponseDto = new UserProductResponseDto();
        userProductResponseDto.setId(userProductModel.getId());
        userProductResponseDto.setUserId(userProductModel.getUser().getId());
        userProductResponseDto.setProductId(userProductModel.getProduct().getId());
        userProductResponseDto.setAmount(userProductModel.getAmount());
        userProductResponseDto.setQuantity(userProductModel.getQuantity());
        userProductResponseDto.setDescription(userProductModel.getDescription());
        userProductResponseDto.setIsActive(userProductModel.getIsActive());
        return userProductResponseDto;
    }


}
