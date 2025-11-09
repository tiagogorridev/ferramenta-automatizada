package com.auditoria.service;

import com.auditoria.model.Auditoria;
import com.auditoria.model.NaoConformidade;
import com.auditoria.model.Pergunta;
import com.auditoria.repository.AuditoriaRepository;
import com.auditoria.repository.NaoConformidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaService {
    
    private final AuditoriaRepository auditoriaRepository;
    private final NaoConformidadeRepository naoConformidadeRepository;
    
    public List<Pergunta> obterPerguntas() {
        List<Pergunta> perguntas = new ArrayList<>();
        
        // IDENTIFICAÇÃO
        perguntas.add(new Pergunta(1L, "Identificação", 
            "O plano possui título 'Plano de Capacitação'?"));
        perguntas.add(new Pergunta(2L, "Identificação", 
            "Existe identificação da empresa/projeto?"));
        perguntas.add(new Pergunta(3L, "Identificação", 
            "Há controle de versão do documento?"));
        perguntas.add(new Pergunta(4L, "Identificação", 
            "Possui data de elaboração?"));
        perguntas.add(new Pergunta(5L, "Identificação", 
            "Consta o nome do responsável pela elaboração?"));
        
        // CAP 1 - NECESSIDADES
        perguntas.add(new Pergunta(6L, "CAP 1 - Necessidades", 
            "As necessidades de treinamento foram identificadas?"));
        perguntas.add(new Pergunta(7L, "CAP 1 - Necessidades", 
            "Existe análise de competências atuais vs necessárias?"));
        perguntas.add(new Pergunta(8L, "CAP 1 - Necessidades", 
            "As necessidades estão alinhadas aos objetivos do negócio?"));
        perguntas.add(new Pergunta(9L, "CAP 1 - Necessidades", 
            "Há priorização dos treinamentos identificados?"));
        
        // PLANEJAMENTO
        perguntas.add(new Pergunta(10L, "Planejamento", 
            "Existe lista de treinamentos planejados?"));
        perguntas.add(new Pergunta(11L, "Planejamento", 
            "Para cada treinamento, consta: nome do curso?"));
        perguntas.add(new Pergunta(12L, "Planejamento", 
            "Para cada treinamento, consta: carga horária?"));
        perguntas.add(new Pergunta(13L, "Planejamento", 
            "Para cada treinamento, consta: público-alvo?"));
        perguntas.add(new Pergunta(14L, "Planejamento", 
            "Para cada treinamento, consta: instrutor/fornecedor?"));
        perguntas.add(new Pergunta(15L, "Planejamento", 
            "Para cada treinamento, consta: data prevista?"));
        perguntas.add(new Pergunta(16L, "Planejamento", 
            "Para cada treinamento, consta: custo estimado?"));
        
        // CAP 2 - EXECUÇÃO
        perguntas.add(new Pergunta(17L, "CAP 2 - Execução e Registro", 
            "Existe procedimento para registro de participação?"));
        perguntas.add(new Pergunta(18L, "CAP 2 - Execução e Registro", 
            "Há modelo de lista de presença?"));
        perguntas.add(new Pergunta(19L, "CAP 2 - Execução e Registro", 
            "Consta forma de registro de certificados?"));
        
        // CAP 3 - AVALIAÇÃO
        perguntas.add(new Pergunta(20L, "CAP 3 - Avaliação", 
            "Existe metodologia para avaliar efetividade dos treinamentos?"));
        perguntas.add(new Pergunta(21L, "CAP 3 - Avaliação", 
            "Há indicadores/métricas definidos?"));
        perguntas.add(new Pergunta(22L, "CAP 3 - Avaliação", 
            "Consta periodicidade de avaliação?"));
        
        // CAP 4 - RECURSOS
        perguntas.add(new Pergunta(23L, "CAP 4 - Recursos", 
            "Os recursos necessários estão identificados?"));
        perguntas.add(new Pergunta(24L, "CAP 4 - Recursos", 
            "Há plano para desenvolvimento de instrutores internos?"));
        perguntas.add(new Pergunta(25L, "CAP 4 - Recursos", 
            "Existe orçamento definido para capacitação?"));
        
        return perguntas;
    }
    
    public Auditoria salvarAuditoria(Auditoria auditoria) {
        // Calcular aderência
        long sim = auditoria.getRespostas().stream()
            .filter(r -> "SIM".equals(r.getValor())).count();
        long nao = auditoria.getRespostas().stream()
            .filter(r -> "NAO".equals(r.getValor())).count();
        long na = auditoria.getRespostas().stream()
            .filter(r -> "NA".equals(r.getValor())).count();
        
        long total = auditoria.getRespostas().size();
        long aplicaveis = total - na;
        
        double aderencia = aplicaveis > 0 ? (sim * 100.0 / aplicaveis) : 0;
        
        auditoria.setPercentualAderencia(aderencia);
        auditoria.setTotalPerguntas((int) total);
        auditoria.setTotalSim((int) sim);
        auditoria.setTotalNao((int) nao);
        auditoria.setTotalNA((int) na);
        
        // Associar respostas à auditoria
        auditoria.getRespostas().forEach(r -> r.setAuditoria(auditoria));
        auditoria.getNaoConformidades().forEach(nc -> nc.setAuditoria(auditoria));
        
        return auditoriaRepository.save(auditoria);
    }
    
    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }
    
    public Auditoria buscarPorId(Long id) {
        return auditoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Auditoria não encontrada"));
    }
    
    public NaoConformidade resolverNC(Long ncId) {
        NaoConformidade nc = naoConformidadeRepository.findById(ncId)
            .orElseThrow(() -> new RuntimeException("NC não encontrada"));
        nc.setStatus(NaoConformidade.Status.RESOLVIDA);
        nc.setDataResolucao(java.time.LocalDate.now());
        return naoConformidadeRepository.save(nc);
    }
    
    public List<NaoConformidade> listarNCsAbertas() {
        return naoConformidadeRepository.findByStatus(NaoConformidade.Status.ABERTA);
    }
}