package com.carros.api.carros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(carroService.getCarros());
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        CarroDTO carro = carroService.getCarroById(id);
        return ResponseEntity.ok(carro);

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
    public ResponseEntity getCarByType(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = carroService.getCarroByTipo(tipo);

        //Exemplo 2 -> usando Ternário
        return carros.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carros);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity post(@RequestBody Carro carro) {
        CarroDTO c = carroService.insert(carro);
        URI location = getUri(c.getId());
        return ResponseEntity.created(location).build();
    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable("id") long id, @RequestBody Carro carro) {
        CarroDTO c = carroService.update(carro, id);
        return c != null ?
                ResponseEntity.ok(c) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        carroService.delete(id);
        return ResponseEntity.ok().build();
    }
}

