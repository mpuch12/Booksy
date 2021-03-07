package pl.umk.mat.booking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class CompanyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "works_photo_id", referencedColumnName="id")
    private List<Photo> worksPhotos;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "workshop_photo_id", referencedColumnName="id")
    private List<Photo> workshopPhotos;
    @OneToMany(mappedBy = "id")
    private List<Employee> employees;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Photo> getWorksPhotos() {
        return worksPhotos;
    }

    public void setWorksPhotos(List<Photo> worksPhotos) {
        this.worksPhotos = worksPhotos;
    }

    public List<Photo> getWorkshopPhotos() {
        return workshopPhotos;
    }

    public void setWorkshopPhotos(List<Photo> workshopPhotos) {
        this.workshopPhotos = workshopPhotos;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}