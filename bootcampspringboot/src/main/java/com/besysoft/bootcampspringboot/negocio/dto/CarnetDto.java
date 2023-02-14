package com.besysoft.bootcampspringboot.negocio.dto;

import lombok.*;

/*@Getter
@Setter
@AllArgsConstructor //Constructor con parametros
@NoArgsConstructor //Constructor por defecto
@ToString
@EqualsAndHashCode*/
@Data
@AllArgsConstructor
public class CarnetDto {
    private Long codigo;
    private String numero;

}
