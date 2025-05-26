package com.techlab.service;

import java.util.*;
import com.techlab.productos.Producto;
import com.techlab.excepciones.SinStockException;
import com.techlab.pedidos.Pedido;

public class PedidoService {
  private final List<Pedido> pedidos = new ArrayList<>();

  public Pedido crearPedido(Map<Integer, Integer> mapaCantidades, List<Producto> productos)
      throws IllegalArgumentException {

    Pedido pedido = new Pedido();
    List<String> errores = new ArrayList<>();

    for (Map.Entry<Integer, Integer> e : mapaCantidades.entrySet()) {
      int idProducto = e.getKey();
      int cantidad = e.getValue();

      Producto prod = productos.stream()
          .filter(p -> p.getId() == idProducto)
          .findFirst()
          .orElse(null);

      if (prod == null) {
        errores.add("Producto no encontrado: ID " + idProducto);
        continue;
      }

      if (prod.getStock() < cantidad) {
        errores.add("Stock insuficiente para: " + prod.getNombre());
        continue;
      }

      try {
        pedido.agregarLinea(prod, cantidad);
      } catch (SinStockException ex) {
        errores.add(ex.getMessage());
      }

    }
    if (!errores.isEmpty()) {
      throw new IllegalArgumentException(String.join("\n", errores));
    }
    pedidos.add(pedido);
    return pedido;
  }

  public List<Pedido> listarPedidos() {
    return Collections.unmodifiableList(pedidos);
  }
}
