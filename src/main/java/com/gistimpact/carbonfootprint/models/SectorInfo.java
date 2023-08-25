package com.gistimpact.carbonfootprint.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.*;
import java.util.UUID;

//@Data
@Entity
@Table(name="ref_sector_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SectorInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "guid", unique = true, updatable = false, nullable = false, columnDefinition = "CHAR(36)") // @Column annotation is used to define the column in database that maps annotated field.
    //@Type(type="org.hibernate.type.UUIDCharType")
    private String guid;
    //private int id;
    @OneToMany(mappedBy="sectorInfo",cascade = CascadeType.ALL)
    List<Company> companies;
    private String sectorCode;
    private String sectorName;
}
