package com.auditoria.repository;

import com.auditoria.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
    List<Auditoria> findByProjetoContaining(String projeto);
    List<Auditoria> findByAuditor(String auditor);
}