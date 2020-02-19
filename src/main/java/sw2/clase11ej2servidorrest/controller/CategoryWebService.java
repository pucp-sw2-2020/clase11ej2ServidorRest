package sw2.clase11ej2servidorrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sw2.clase11ej2servidorrest.repository.CategoryRepository;

@RestController
@RequestMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryWebService {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("")
    public ResponseEntity listarProductos() {
        return new ResponseEntity(categoryRepository.findAll(), HttpStatus.OK);
    }
}
