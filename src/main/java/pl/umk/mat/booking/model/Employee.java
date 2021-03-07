package pl.umk.mat.booking.model;




import pl.umk.mat.booking.validation.constraints.Password;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Set<UserRole> roles = new HashSet<>();
    private String name;
    @Email
    private String email;
    @Password
    private String password;
    private String photoUrl;
    @OneToMany(mappedBy = "employee")
    private List<WorkHours> workHoursList = new ArrayList<>();
    @OneToMany(mappedBy = "employee")
    private List<Holiday> leaveList = new ArrayList<>();

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<WorkHours> getWorkHoursList() {
        return workHoursList;
    }

    public void setWorkHoursList(List<WorkHours> workHoursList) {
        this.workHoursList = workHoursList;
    }

    public List<Holiday> getLeaveList() {
        return leaveList;
    }

    public void setLeaveList(List<Holiday> leaveList) {
        this.leaveList = leaveList;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }



}
