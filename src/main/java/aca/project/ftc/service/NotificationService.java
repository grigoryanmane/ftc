package aca.project.ftc.service;

import aca.project.ftc.exception.UserNotFound;
import aca.project.ftc.model.constants.NotificationStatus;
import aca.project.ftc.model.dto.request.notification.NotificationEditRequestDto;
import aca.project.ftc.model.dto.request.notification.NotificationRequestDto;
import aca.project.ftc.model.dto.response.notification.NotificationResponseDto;
import aca.project.ftc.model.dto.response.product.ProductResponseDto;
import aca.project.ftc.model.dto.response.user.UserResponseDto;
import aca.project.ftc.model.entity.NotificationModel;
import aca.project.ftc.repository.NotificationRepository;
import aca.project.ftc.repository.ProductRepository;
import aca.project.ftc.repository.UserProductRepository;
import aca.project.ftc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    public NotificationResponseDto addNotification(NotificationRequestDto notificationRequestDto) {
        try {
            if (userRepository.existsById(notificationRequestDto.getReceiverId())
                    && userRepository.existsById(notificationRequestDto.getSenderId())
                    && userProductRepository.existsById(notificationRequestDto.getUserProductId())) {
                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setReceiver(userRepository.findById(notificationRequestDto.getReceiverId()).get());
                notificationModel.setSender(userRepository.findById(notificationRequestDto.getSenderId()).get());
                notificationModel.setUserProduct(userProductRepository.findById(notificationRequestDto.getUserProductId()).get());
                notificationModel.setMessage(notificationRequestDto.getMessage());
                notificationModel.setStatus(NotificationStatus.PENDING);
                notificationRepository.save(notificationModel);
                return getNotificationResponseDto(notificationModel);
            }
            throw new UserNotFound("INVALID_REQUEST");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UserNotFound("INVALID_REQUEST");

        }
    }

    public NotificationResponseDto getNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            return getNotificationResponseDto(notificationRepository.findById(id).get());
        }
        //TODO::GIVE NORMAL EXCEPTION
        throw new UserNotFound("NOTIFICATION NOT FOUND");
    }

    public List<NotificationResponseDto> farmerNotification(Long id) {
        List<NotificationModel> notificationResponseDto = notificationRepository.findAllBySenderIdAndStatusIsOrStatusIsOrderByUpdatedAtDesc(id, NotificationStatus.ACCEPTED, NotificationStatus.REJECTED);
        return getResponseList(notificationResponseDto);
    }

    public List<NotificationResponseDto> companyNotification(Long id) {
        List<NotificationModel> notificationResponseDto = notificationRepository.findAllByReceiverIdAndStatusIsOrderByUpdatedAtDesc(id, NotificationStatus.PENDING);
        return getResponseList(notificationResponseDto);
    }

    public List<NotificationResponseDto> getResponseList(List<NotificationModel> notificationModel) {
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        int i = 0;
        while (i < notificationModel.size()) {
            NotificationResponseDto notificationResponseDto = getNotificationResponseDto(notificationModel.get(i));
            notificationResponseDtoList.add(notificationResponseDto);
            i++;
        }
        return notificationResponseDtoList;
    }

    public NotificationResponseDto editNotification(NotificationEditRequestDto notificationEditRequestDto, Long id) {
        try {
            if (notificationRepository.existsById(id)) {
                System.out.println(id);
                NotificationModel notificationModel = notificationRepository.findById(id).get();
                notificationModel.setStatus(NotificationStatus.valueOf(notificationEditRequestDto.getStatus().toUpperCase()));
                notificationRepository.save(notificationModel);
                return getNotificationResponseDto(notificationModel);
            } //TODO:: CHANGE THE EXCEPTION TYPE
            throw new UserNotFound("INVALID NOTIFICATION");
        } catch (Exception e) {
            throw new UserNotFound("SOME EXCEPTION");
        }
    }

    public NotificationResponseDto deleteNotification(Long id) {
        if (notificationRepository.existsById(id)) {
            NotificationModel notificationModel = notificationRepository.findById(id).get();
            notificationRepository.deleteById(id);
            return getNotificationResponseDto(notificationModel);
        }
        //TODO:: REMOVE AND CHANGE THE EXCEPTION
        throw new UserNotFound("Invalid Notification");
    }

    public NotificationResponseDto getNotificationResponseDto(NotificationModel notificationModel) {
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        UserResponseDto receiver = userService.getUserResponseData(userRepository.findById(notificationModel.getReceiver().getId()).get());
        UserResponseDto sender = userService.getUserResponseData(userRepository.findById(notificationModel.getSender().getId()).get());
        ProductResponseDto product =
                productService.setUserProductDto(userProductRepository.findById(notificationModel.getUserProduct().getId()).get());
        product.setProductName(productRepository.findById(product.getProductId()).get().getName());
        notificationResponseDto.setId(notificationModel.getId());
        notificationResponseDto.setMessage(notificationModel.getMessage());
        notificationResponseDto.setStatus(notificationModel.getStatus().getKey());
        notificationResponseDto.setSender(sender);
        notificationResponseDto.setReceiver(receiver);
        notificationResponseDto.setProduct(product);
        return notificationResponseDto;
    }

}
