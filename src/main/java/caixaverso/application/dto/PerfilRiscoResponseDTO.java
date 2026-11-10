package caixaverso.application.dto;

public record PerfilRiscoResponseDTO(
        Long clienteId,
        String perfil,
        Integer pontuacao,
        String descricao) {}