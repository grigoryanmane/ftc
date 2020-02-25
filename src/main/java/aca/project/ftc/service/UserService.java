package aca.project.ftc.service;


import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.entity.UserModel;
import aca.project.ftc.model.entity.UserProductModel;
import aca.project.ftc.model.dto.request.product.ProductRequestDto;
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

    public UserProductResponseDto addUserProduct(ProductRequestDto productRequestDto) {
        UserProductModel userProductModel = new UserProductModel();
        //TODO:: SHOULD I DO CHECKS FOR isPresent()??
        userProductModel.setProduct(productRepository.findById(productRequestDto.getProductId()).get());
        userProductModel.setUser(userRepository.findById(productRequestDto.getUserId()).get());
        userProductModel.setAmount(productRequestDto.getAmount());
        userProductModel.setQuantity(productRequestDto.getQuantity());
        userProductModel.setDescription(productRequestDto.getDescription());
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

    public UserProductResponseDto editUserProduct(ProductRequestDto productRequestDto) {
        UserProductModel userProductModel = new UserProductModel();
        try {
            if (userProductRepository.findById(productRequestDto.getId()).isPresent()) {
                userProductModel.setId(productRequestDto.getId());
            }//TODO can be replaced with exists
            if (userRepository.findById(productRequestDto.getUserId()).isPresent()) {
                userProductModel.setUser(userRepository.findById(productRequestDto.getUserId()).get());
            }
            if (productRepository.findById(productRequestDto.getProductId()).isPresent()) {
                userProductModel.setProduct((productRepository.findById(productRequestDto.getProductId())).get());
            }
            if (productRequestDto.getAmount() != null) {
                userProductModel.setAmount(productRequestDto.getAmount());
            }
            if (productRequestDto.getQuantity() != null) {
                userProductModel.setQuantity(productRequestDto.getQuantity());
            }
            if (productRequestDto.getDescription() != null) {
                userProductModel.setDescription(productRequestDto.getDescription());
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
