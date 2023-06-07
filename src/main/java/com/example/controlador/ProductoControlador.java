package com.example.controlador;

import com.example.modelo.Producto;
import com.example.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoControlador {
    @Autowired
    private ProductoServicio servicio;

    @GetMapping("/productos")
    public List<Producto> listarProductos(){
        return servicio.listarProductos();
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Integer id) {
        try {
            Producto producto = servicio.obtenerProductoPorId(id);
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregarProducto")
    public void guardarProducto(@RequestBody Producto producto) {
        servicio.guardarProducto(producto);
    }

    @PutMapping("/actualizarProducto/{id}")
    public ResponseEntity<?> actualizarProducto(@RequestBody Producto producto, @PathVariable Integer id){
        try {
            Producto productoExistente = servicio.obtenerProductoPorId(id);
            productoExistente.setNombre(producto.getNombre());
            productoExistente.setPrecio(producto.getPrecio());
            servicio.guardarProducto(productoExistente);
            return new ResponseEntity<Producto>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminarProducto/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        servicio.eliminarProducto(id);
    }
}
