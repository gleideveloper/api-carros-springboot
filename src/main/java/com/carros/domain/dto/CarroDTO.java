package com.carros.domain.dto;

import com.carros.domain.Carro;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class CarroDTO {
    /**
     * Carro DTO pode ser um objeto com atributos resumidos.
     */
    private Long id;
    private String nome;
    private String tipo;
    //private String placa ="QHZ-0414";

    /**
     * ModelMapper -> Transferi os atributos de objeto para outro,
     * resumindo propriedades do objeto que será usado.
     */
    public static CarroDTO create(Carro c){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(c, CarroDTO.class);
    }

}
