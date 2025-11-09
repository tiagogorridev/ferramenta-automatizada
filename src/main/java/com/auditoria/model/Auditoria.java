package com.auditoria.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Auditoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String projeto;
    private String auditor;
    private LocalDate dataAuditoria;
    
    @OneToMany(mappedBy = "auditoria", cascade = CascadeType.ALL)
    private List<Resposta> respostas = new ArrayList<>();
    
    @OneToMany(mappedBy = "auditoria", cascade = CascadeType.ALL)
    private List<NaoConformidade> naoConformidades = new ArrayList<>();
    
    private Double percentualAderencia;
    private Integer totalPerguntas;
    private Integer totalSim;
    private Integer totalNao;
    private Integer totalNA;
}