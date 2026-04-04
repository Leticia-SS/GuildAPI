package com.example.guildapi.dto;

import com.example.guildapi.model.enums.ClasseEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AventureiroCadastroRequestDto {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
    private String nome;
    @NotNull(message = "Classe é obrigatória")
    private ClasseEnum classe;
    @Min(value = 1, message = "Nível deve ser maior ou igual a 1")
    private Integer nivel = 1;
}
