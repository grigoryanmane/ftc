package aca.project.ftc.service;


import aca.project.ftc.model.UserModel;
import aca.project.ftc.model.UserProductModel;
import aca.project.ftc.model.request.DeleteUserRequest;
import aca.project.ftc.model.request.UserEditRequest;
import aca.project.ftc.model.request.UserProductRequest;
import aca.project.ftc.model.response.User;
import aca.project.ftc.model.response.UserEditResponseDto;
import aca.project.ftc.model.response.UserProductResponseDto;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
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

//    public UserProductResponseDto editUserProduct(UserProductRequest userProductRequest) {
//
//    }

}
