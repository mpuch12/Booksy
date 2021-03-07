package pl.umk.mat.booking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE} )
    @JoinColumn(name = "client_id")
    private Client client;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service selectedService;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Term termOfService;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Term getTermOfService() {
        return termOfService;
    }

    public void setTermOfService(Term termOfService) {
        this.termOfService = termOfService;
    }
}
