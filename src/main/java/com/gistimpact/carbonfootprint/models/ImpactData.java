package com.gistimpact.carbonfootprint.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

//@Data
@Entity
@Table(name = "ref_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImpactData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid", unique = true, updatable = false, nullable = false, columnDefinition = "CHAR(36)") // @Column annotation is used to define the column in database that maps annotated field.
    private String guid;
    //private int id;
    private double ghgImpactUsd;
    private double marketCapitalization;
    private int reportingYear;
    @ManyToOne
    @JoinColumn(name="company_Id", nullable=false)
    private Company company;
}
