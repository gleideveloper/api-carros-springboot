package com.carros.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> getCarrosFake(){
        List<Carro> carros = new ArrayList<>();

        carros.add(new Carro(1L, "Fusca"));
        carros.add(new Carro(2L, "Brasilia"));
        carros.add(new Carro(3L, "Chevette"));

        return carros;
    }
    public Iterable<Carro> getCarros(){
        return carroRepository.findAll();
    }

    public Optional<Carro> getCarroById(Long id) {
        return carroRepository.findById(id);
    }

    public List<Carro> getCarroByTipo(String tipo) {
        return carroRepository.findByTipo(tipo);
    }

    public Carro insert(Carro carro) {
        return carroRepository.save(carro);
    }

    public Carro update(Carro carro, long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        //Busca o carro no bd
        Optional<Carro> optionalCarro = getCarroById(id);

        if(optionalCarro.isPresent()){
            Carro db = optionalCarro.get();

            //Copia as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            System.out.println("Carro id: " + db.getId());

            //Atualiza o carro
            carroRepository.save(db);
            return db;
        }else{
            throw new RuntimeException("Não foi possível atualizar o registro");
        }
    }

    public void delete(long id) {
        Optional<Carro> carro = getCarroById(id);
        if(carro.isPresent()){
            carroRepository.deleteById(id);
        }
    }
}
