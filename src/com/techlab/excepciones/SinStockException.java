package com.techlab.excepciones;

public class SinStockException extends Exception {
  public SinStockException(String producto) {
    super("Stock insuficiente para: " + producto);
  }
}
