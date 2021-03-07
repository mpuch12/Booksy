package pl.umk.mat.booking.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Min(0)
    private int price;
    private int timeOfServiceInMinutes;
    @NotNull
    @ManyToMany
    @JoinTable(name = "employee_services",
            joinColumns = {@JoinColumn(name="service_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="employee_id", referencedColumnName="id")}
    )
    private List<Employee> selectedEmployees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTimeOfServiceInMinutes() {
        return timeOfServiceInMinutes;
    }

    public void setTimeOfServiceInMinutes(int timeOfServiceInMinutes) {
        this.timeOfServiceInMinutes = timeOfServiceInMinutes;
    }

    public List<Employee> getSelectedEmployees() {
        return selectedEmployees;
    }

    public void setSelectedEmployees(List<Employee> selectedEmployees) {
        this.selectedEmployees = selectedEmployees;
    }
}
