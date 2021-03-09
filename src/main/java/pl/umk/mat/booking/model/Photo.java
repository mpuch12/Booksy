package pl.umk.mat.booking.model;

import javax.persistence.*;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alternativeText;
    private String path;
    private String type;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyDetails companyDetails;

    public Photo(){}

    public Photo(String path, String type, CompanyDetails companyDetails) {
        this.path = path;
        this.type = type;
        this.companyDetails = companyDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

    public void setCompanyDetails(CompanyDetails companyDetails) {
        this.companyDetails = companyDetails;
    }
}
