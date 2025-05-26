package com.techlab.pedidos;

import java.util.*;

import com.techlab.productos.Producto;
import com.techlab.excepciones.SinStockException;

public class Pedido {
  private static int contador = 0;
  private final int id;
  private final List<DetallePedido> lineas = new ArrayList<>();

  public Pedido() {
    this.id = ++contador;
  }

  public int getId() {
    return id;
  }

  public void agregarLinea(Producto p, int cantidad) throws SinStockException {
    if (p.getStock() < cantidad) {
      throw new SinStockException(p.getNombre());
    }
    p.setStock(p.getStock() - cantidad);
    lineas.add(new DetallePedido(p, cantidad));
  }

  public double calcularTotal() {
    double total = 0;
    for (DetallePedido l : lineas) {
      total += l.subTotal();
    }
    return total;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Pedido #" + id + "\n");
    for (DetallePedido l : lineas) {
      sb.append("  ").append(l).append("\n");
    }
    sb.append(String.format("Total: %.2f", calcularTotal()));
    return sb.toString();
  }
}
