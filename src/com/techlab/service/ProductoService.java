package com.techlab.service;

import java.util.*;

import com.techlab.productos.Producto;

public class ProductoService {
  private final List<Producto> productos = new ArrayList<>();

  public void agregarProducto(Producto p) {
    productos.add(p);
  }

  public List<Producto> listarProductos() {
    return Collections.unmodifiableList(productos);
  }

  public Producto buscarPorId(int id) {
    for (Producto p : productos) {
      if (p.getId() == id) {
        return p;
      }
    }
    return null;
  }

  public boolean eliminarProducto(int id) {
    return productos.removeIf(p -> p.getId() == id);
  }

  public void actualizarProducto(Producto p, String nombre, Double precio, Integer stock) {
    if (nombre != null && !nombre.isBlank()) {
      p.setNombre(nombre);
    }
    if (precio != null && precio >= 0) {
      p.setPrecio(precio);
    }
    if (stock != null && stock >= 0) {
      p.setStock(stock);
    }
  }
}
