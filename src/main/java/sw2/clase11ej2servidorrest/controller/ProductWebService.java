package sw2.clase11ej2servidorrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import sw2.clase11ej2servidorrest.entity.Product;
import sw2.clase11ej2servidorrest.repository.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin
public class ProductWebService {

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listarProductos() {
        return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerProducto(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Product> opt = productRepository.findById(id);
            if (opt.isPresent()) {
                responseMap.put("estado", "ok");
                responseMap.put("producto", opt.get());
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el producto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity guardarProducto(
            @RequestBody Product product,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseMap = new HashMap<>();

        productRepository.save(product);
        if (fetchId) {
            responseMap.put("id", product.getId());
        }
        responseMap.put("estado", "creado");
        return new ResponseEntity(responseMap, HttpStatus.CREATED);

    }

    @PutMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarProducto(@RequestBody Product product) {

        HashMap<String, Object> responseMap = new HashMap<>();

        if (product.getId() > 0) {
            Optional<Product> opt = productRepository.findById(product.getId());
            if (opt.isPresent()) {
                productRepository.save(product);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El producto a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity borrarProducto(@PathVariable("id") String idStr) {

        HashMap<String, Object> responseMap = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
                responseMap.put("estado", "borrado exitoso");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el producto con id: " + id);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException ex) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "El ID debe ser un número");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/product/parcial", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarProductoParcial(@RequestBody Product product) {

        return updateProduct(product);
    }

    @PutMapping(value = "/product/x-www-form",
            consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity actualizarProductoXwwwForm(Product product) {

        return updateProduct(product);
    }

    public ResponseEntity updateProduct(Product product){

        HashMap<String, Object> responseMap = new HashMap<>();

        if (product.getId() > 0) {
            Optional<Product> opt = productRepository.findById(product.getId());
            if (opt.isPresent()) {
                Product productoDB = opt.get();
                //validar campo por campo
                if (product.getProductname() != null &&
                        !productoDB.getProductname().equals(product.getProductname())) {
                    productoDB.setProductname(product.getProductname());
                }
                if (product.getUnitprice() != null &&
                        productoDB.getUnitprice() != product.getUnitprice()) {
                    productoDB.setUnitprice(product.getUnitprice());
                }
                if (product.getUnitsinstock() != null &&
                        productoDB.getUnitsinstock() != product.getUnitsinstock()) {
                    productoDB.setUnitsinstock(product.getUnitsinstock());
                }
                if (product.getUnitsonorder() != null &&
                        productoDB.getUnitsonorder() != product.getUnitsonorder()) {
                    productoDB.setUnitsonorder(product.getUnitsonorder());
                }
                if (product.getSupplier() != null &&
                        productoDB.getSupplier().getId() != product.getSupplier().getId()) {
                    productoDB.setSupplier(product.getSupplier());
                }
                if (product.getCategory() != null &&
                        productoDB.getCategory().getId() != product.getCategory().getId()) {
                    productoDB.setCategory(product.getCategory());
                }
                if (product.getQuantityperunit() != null &&
                        !productoDB.getQuantityperunit().equals(product.getQuantityperunit())) {
                    productoDB.setQuantityperunit(product.getQuantityperunit());
                }
                if (product.getReorderlevel() != null &&
                        productoDB.getReorderlevel() != product.getReorderlevel()) {
                    productoDB.setReorderlevel(product.getReorderlevel());
                }

                if (product.getDiscontinued() != null &&
                        productoDB.getDiscontinued() != product.getDiscontinued()) {
                    productoDB.setDiscontinued(product.getDiscontinued());
                }

                productRepository.save(productoDB);
                responseMap.put("estado", "actualizado");
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "El producto a actualizar no existe");
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un ID");
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity gestionExcepcion(HttpServletRequest request) {

        HashMap<String, Object> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un producto");
        }
        return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
    }


}

