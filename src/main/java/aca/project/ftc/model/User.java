package aca.project.ftc.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table(name = "users")
public class User extends Audit {
    public enum Type {
        COMPANY, FARMER
    }

    public enum Region {
        YEREVAN, KOTAYQ, VAYOTSDZOR, TAVUSH, SYUNIQ, SHIRAK, LORI, GEGHARQUNIK, ARMAVIR, ARARAT, ARAGATSOTN
    }

    public enum Gender {
        FEMALE, MALE, OTHER
    }

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

    @NotNull
    @Size(max = 250)
    private String password;

    @NotNull
    @Size(max = 13, min = 13) // We need to pass values in this way 00374xx xx xx xx
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Type type;

    @NotNull
    private Date birthDate;

    @Column(unique = true)
    private String username;

    public String getUsername() {
        return this.email;
    }

    public void setUsername(String username) {
        this.email = username;
        this.username = username;
    }

    private String industry;

    private Double rating = 5.0;


    private Integer ratingCount = 1;

    private String companyName;
    private String address;
    private Region region;
    private String city;

    private Boolean active = true;
    private Gender gender;


    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<UserProduct> userProduct = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<Project> project = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<Transaction> transaction = new ArrayList<>();


    public void addTransaction(Transaction newTransaction) {
        transaction.add(newTransaction);
        newTransaction.setUser(this);
    }

    public List getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transaction = transactions;
    }

    public void addProject(Project newProject) {
        project.add(newProject);
        newProject.setUser(this);
    }

    public List<UserProduct> getUserProduct() {
        return userProduct;
    }


    public void removeTransaction(Transaction oldTransaction) {
        transaction.remove(oldTransaction);
        oldTransaction.setUser(null);
    }

    public List getProject() {
        return project;
    }

    public void setProject(List<Project> projects) {
        this.project = projects;
    }

    public void removeProject(Project oldProject) {
        project.remove(oldProject);
        oldProject.setUser(null);
    }

    public void addUserProduct(UserProduct newUserProduct) {
        userProduct.add(newUserProduct);
        newUserProduct.setUser(this);
    }

    public void setUserProduct(List<UserProduct> userProduct) {
        this.userProduct = userProduct;
    }

    public void removeUserProduct(UserProduct oldUserProduct) {
        userProduct.remove(oldUserProduct);
        oldUserProduct.setUser(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

//    public Set<Notification> getNotification() {
//        return notification;
//    }
//
//    public void setNotification(Set<Notification> notification) {
//        this.notification = notification;
//    }
//
//    public void addNotification(Notification newNotification) {
//        notification.add(newNotification);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId().equals(user.getId()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getPhoneNumber().equals(user.getPhoneNumber()) &&
                getType() == user.getType() &&
                getBirthDate().equals(user.getBirthDate()) &&
                getUsername().equals(user.getUsername()) &&
                Objects.equals(getIndustry(), user.getIndustry()) &&
                getRating().equals(user.getRating()) &&
                getRatingCount().equals(user.getRatingCount()) &&
                Objects.equals(getCompanyName(), user.getCompanyName()) &&
                Objects.equals(getAddress(), user.getAddress()) &&
                getRegion() == user.getRegion() &&
                Objects.equals(getCity(), user.getCity()) &&
                getActive().equals(user.getActive()) &&
                getGender() == user.getGender() &&
                Objects.equals(getUserProduct(), user.getUserProduct()) &&
                Objects.equals(getProject(), user.getProject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail(), getPassword(), getPhoneNumber(), getType(), getBirthDate(), getUsername(), getIndustry(), getRating(), getRatingCount(), getCompanyName(), getAddress(), getRegion(), getCity(), getActive(), getGender(), getUserProduct(), getProject());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", type=" + type +
                ", birthDate=" + birthDate +
                ", username='" + username + '\'' +
                ", industry='" + industry + '\'' +
                ", rating=" + rating +
                ", ratingCount=" + ratingCount +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", region=" + region +
                ", city='" + city + '\'' +
                ", active=" + active +
                ", gender=" + gender +
                ", userProduct=" + userProduct +
                ", project=" + project +
                ", transaction=" + transaction +
                '}';
    }
}
