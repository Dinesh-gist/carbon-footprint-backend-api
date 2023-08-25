package com.gistimpact.carbonfootprint.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
//@Data
@Table(name = "entity_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid", unique = true, updatable = false, nullable = false, columnDefinition = "CHAR(36)") // @Column annotation is used to define the column in database that maps annotated field.
    private String guid;
    //private int id;
    @Column(name = "company_name")
    private String companyName;
    @ManyToOne
    @JoinColumn(name="sector_id", nullable=false)
    private SectorInfo sectorInfo;
    @OneToMany(mappedBy="company",cascade = CascadeType.ALL)
    private List<ImpactData> impactDataList;

 }
