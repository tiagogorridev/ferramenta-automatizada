package com.auditoria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pergunta {
    private Long id;
    private String categoria;
    private String texto;
}