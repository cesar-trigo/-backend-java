package com.techlab.pedidos;

import com.techlab.productos.Producto;

public class DetallePedido {
  private final Producto producto;
  private final int cantidad;

  public DetallePedido(Producto producto, int cantidad) {
    this.producto = producto;
    this.cantidad = cantidad;
  }

  public double subTotal() {
    return producto.getPrecio() * cantidad;
  }

  @Override
  public String toString() {
    return String.format("%s x %d = %.2f", producto.getNombre(), cantidad, subTotal());
  }
}
