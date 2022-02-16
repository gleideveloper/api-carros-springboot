package com.carros.api;

import com.carros.domain.Carro;
import com.carros.domain.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<Iterable<Carro>> get(){
        return ResponseEntity.ok(carroService.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Carro> carro =  carroService.getCarroById(id);

        //Exemplo 3 -> usando lambda
        return carro
                .map(ResponseEntity::ok) //=> (c -> ResponseEntity.ok(c))
                .orElse(ResponseEntity.notFound().build());

        /*/Exemplo 2 -> usando Ternário
        return carro.isPresent()?
                ResponseEntity.ok(carro.get()):
                ResponseEntity.notFound().build();*/

        /*/Exemplo 1 -> usando if/else
        if(carro.isPresent()){
            return ResponseEntity.ok(carro.get());
        }else{
        return ResponseEntity.notFound().build();
        }*/
    }

    @GetMapping("/tipo/{tipo}")
    public Iterable<Carro> get(@PathVariable("tipo") String tipo){
        return carroService.getCarroByTipo(tipo);
    }

    @PostMapping
    public String post(@RequestBody Carro carro){
        Carro c = carroService.insert(carro);
        return "Carro salvo com sucesso " + c.getId();
    }

    @PutMapping("/{id}")
    public String put(@PathVariable("id") long id, @RequestBody Carro carro){
        Carro c = carroService.update(carro, id);
        return "Carro atualizado com sucesso " + c.getId();
    }

    @DeleteMapping("/{id}")
    public String put(@PathVariable("id") long id){
        carroService.delete(id);
        return "Carro deletado com sucesso ";
    }
}

