package com.auditoria.repository;

import com.auditoria.model.NaoConformidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NaoConformidadeRepository extends JpaRepository<NaoConformidade, Long> {
    List<NaoConformidade> findByAuditoriaId(Long auditoriaId);
    List<NaoConformidade> findByStatus(NaoConformidade.Status status);
}