package aca.project.ftc.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "projects")
@Getter
@Setter
public class ProjectModel extends BaseModel {
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
    private UserModel user;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private List<TransactionModel> transaction = new ArrayList<>();

    public void addTransaction(TransactionModel newTransaction) {
        transaction.add(newTransaction);
        newTransaction.setProject(this);
    }

    public void removeTransaction(TransactionModel oldTransaction) {
        transaction.remove(oldTransaction);
        oldTransaction.setProject(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectModel)) return false;
        ProjectModel project = (ProjectModel) o;
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

}
