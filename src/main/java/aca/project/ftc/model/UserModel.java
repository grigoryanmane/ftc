package aca.project.ftc.model;


import aca.project.ftc.model.constants.Gender;
import aca.project.ftc.model.constants.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table(name = "users")
@Setter
@Getter
public class UserModel extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100, min = 3)
    private String firstName;

    @NotNull
    @Size(max = 100, min = 2)
    private String lastName;

    @NotNull
    @Size(max = 150)
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @NotNull
    @Size(max = 250)
    private String password;

    @NotNull
    private String phoneNumber;

    private Boolean isCompany = false;

    @NotNull
    private Date birthDate;

    private Double rating = 5.0;

    private Integer ratingCount = 1;

    private String companyName;

    private Region region;

    private Gender gender;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<UserProductModel> userProduct = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<ProjectModel> project = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<TransactionModel> transaction = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "receiver", orphanRemoval = true)
    private List<NotificationModel> notificationReceived = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "sender", orphanRemoval = true)
    private List<NotificationModel> notificationSent = new ArrayList<>();

    public void addNotificationReceived(NotificationModel newNotification) {
        notificationReceived.add(newNotification);
        newNotification.setReceiver(this);
    }

    public void removeNotificationReceived(NotificationModel oldNotification) {
        notificationReceived.remove(oldNotification);
        oldNotification.setReceiver(null);
    }

    public void addNotificationSent(NotificationModel newNotification) {
        notificationSent.add(newNotification);
        newNotification.setSender(this);
    }

    public void removeNotificationSent(NotificationModel oldNotification) {
        notificationSent.remove(oldNotification);
        oldNotification.setSender(null);
    }

    public void addTransaction(TransactionModel newTransaction) {
        transaction.add(newTransaction);
        newTransaction.setUser(this);
    }

    public void removeTransaction(TransactionModel oldTransaction) {
        transaction.remove(oldTransaction);
        oldTransaction.setUser(null);
    }

    public void addUserProject(ProjectModel newProject) {
        project.add(newProject);
        newProject.setUser(this);
    }

    public void removeProject(ProjectModel oldProject) {
        project.remove(oldProject);
        oldProject.setUser(null);
    }

    public void addUserProduct(UserProductModel newUserProduct) {
        userProduct.add(newUserProduct);
        newUserProduct.setUser(this);
    }

    public void removeUserProduct(UserProductModel oldUserProduct) {
        userProduct.remove(oldUserProduct);
        oldUserProduct.setUser(null);
    }

}
