package aca.project.ftc.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
public class Project extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=10, max = 100)
    private String name;

    @NotNull
    @Size(min=100, max = 700)
    private String description;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<Transaction> transaction = new ArrayList<>();



    public void addTransaction(Transaction newTransaction) {
        transaction.add(newTransaction);
        newTransaction.setProject(this);
    }

    public List getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transaction = transactions;
    }

    public void removeTransaction(Transaction oldTransaction) {
        transaction.remove(oldTransaction);
        oldTransaction.setProject(null);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        Project project = (Project) o;
        return getId().equals(project.getId()) &&
                getName().equals(project.getName()) &&
                getDescription().equals(project.getDescription()) &&
                Objects.equals(getImgUrl(), project.getImgUrl()) &&
                getUser().equals(project.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getImgUrl(), getUser());
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", user=" + user +
                ", transaction=" + transaction +
                '}';
    }
}
