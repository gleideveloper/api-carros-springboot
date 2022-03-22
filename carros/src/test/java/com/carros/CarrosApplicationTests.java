package com.carros;

import com.carros.api.exception.ObjectNotFoundException;
import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import com.carros.domain.dto.CarroDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosApplicationTests {

    @Autowired
    private CarroService service;

    @Test
    public void saveCarro() {
        Carro carro = new Carro();
        carro.setNome("Ferrari");
        carro.setTipo("esportivos");

        CarroDTO carroDTO = service.insert(carro);
        assertNotNull(carroDTO);

        Long id = carroDTO.getId();
        assertNotNull(carroDTO);

        //Buscar objeto e verifica se está presente
        carroDTO = service.getCarroById(id);
        assertNotNull(carroDTO);

        //Compara se as propriedades são iguais ao experado.
        assertEquals("Ferrari", carroDTO.getNome());
        assertEquals("esportivos", carroDTO.getTipo());

        //Deleta o objeto
        service.delete(id);

        //Verificar se foi deletado
        try{
            assertNull(service.getCarroById(id));
            fail("O carro não foi excluído");
        }catch (ObjectNotFoundException objEx){
            objEx.printStackTrace();
        }

    }

    @Test
    public void listaCarro() {
        List<CarroDTO> carroList = service.getCarros();
        assertEquals(30, carroList.size());
    }

}
