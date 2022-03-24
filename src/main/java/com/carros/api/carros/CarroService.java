package com.carros.api.carros;

import com.carros.api.infra.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    private CarroRepository carroRep;

    public List<CarroDTO> getCarros() {
        //Exemplo 1-> Lambda
        return carroRep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());

        /*/Exemplo 2-> ForEach
        List<Carro> carros = carroRep.findAll();
        List<CarroDTO> carrosDTO = new ArrayList<>();
        for (Carro c : carros) {
            carrosDTO.add(new CarroDTO(c));
        }
        return carrosDTO;*/
    }

    public CarroDTO getCarroById(Long id) {
        //Exemplo 1-> Lambda
        Optional<Carro> carro = carroRep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(()-> new ObjectNotFoundException("Carro não encontrado!!!"));

        /*/Exemplo 2->Functional style expression
        Optional<Carro> carroOp = carroRep.findById(id);
        return carroOp.map(carro -> Optional.of(new CarroDTO(carro))).orElse(null);*/
    }

    public List<CarroDTO> getCarroByTipo(String tipo) {
        /** Exemplo da forma resumida do Lambda
         * map(c -> new CarroDTO(c)) => map(CarroDTO::new)
         */
        return carroRep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível atualizar o registro");

        return CarroDTO.create(carroRep.save(carro));
    }

    public CarroDTO update(Carro carro, long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        //Busca o carro no bd
        Optional<Carro> optionalCarro = carroRep.findById(id);

        if (optionalCarro.isPresent()) {
            Carro db = optionalCarro.get();

            //Copia as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id: " + db.getId());

            //Atualiza o carro
            carroRep.save(db);
            return CarroDTO.create(db);
        }
        /*throw new RuntimeException("Não foi possível atualizar o registro");*/
        return null;
    }

    public void delete(long id) {
        carroRep.deleteById(id);
    }
}
