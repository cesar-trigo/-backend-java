package com.techlab.productos;

public class Producto {
  private static int contador = 0;
  private final int id;
  private String nombre;
  private double precio;
  private int stock;

  public Producto(String nombre, double precio, int stock) {
    if (precio < 0 || stock < 0) {
      throw new IllegalArgumentException("Precio y stock deben ser >= 0");
    }
    this.id = ++contador;
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    if (precio < 0) {
      throw new IllegalArgumentException("Precio no puede ser negativo");
    }
    this.precio = precio;
  }

  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    if (stock < 0) {
      throw new IllegalArgumentException("Stock no puede ser negativo");
    }
    this.stock = stock;
  }

  @Override
  public String toString() {
    return String.format("[%d] %s - Precio: %.2f - Stock: %d", id, nombre, precio, stock);
  }
}
