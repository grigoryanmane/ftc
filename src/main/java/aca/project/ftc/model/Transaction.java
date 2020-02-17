package aca.project.ftc.model;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction extends Audit {

    public enum Status {
        PENDING, CANCELLED, ACCEPTED, REJECTED, FAILED, SUCCESS
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double amountSent;
    private String currencySent;
    private Double amountReceived;
    private String currencyReceived;
    private String dataSent;
    private String dataReceived;
    private Status status = Status.PENDING;
    private String callbackReceived;
    private Long paymentId;
    private String note;
    private String response;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(Double amountSent) {
        this.amountSent = amountSent;
    }

    public String getCurrencySent() {
        return currencySent;
    }

    public void setCurrencySent(String currencySent) {
        this.currencySent = currencySent;
    }

    public Double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(Double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getCurrencyReceived() {
        return currencyReceived;
    }

    public void setCurrencyReceived(String currencyReceived) {
        this.currencyReceived = currencyReceived;
    }

    public String getDataSent() {
        return dataSent;
    }

    public void setDataSent(String dataSent) {
        this.dataSent = dataSent;
    }

    public String getDataReceived() {
        return dataReceived;
    }

    public void setDataReceived(String dataReceived) {
        this.dataReceived = dataReceived;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCallbackReceived() {
        return callbackReceived;
    }

    public void setCallbackReceived(String callbackReceived) {
        this.callbackReceived = callbackReceived;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
