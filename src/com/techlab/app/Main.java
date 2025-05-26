package com.techlab.app;

import java.util.*;
import com.techlab.productos.*;
import com.techlab.service.*;
import com.techlab.pedidos.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != 7);
    }

    private static void mostrarMenu() {
        System.out.println("=== SISTEMA DE GESTION ===");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.print("Elija una opcion: ");
    }

    private static int leerOpcion() {
        String line = sc.nextLine().trim();
        try {
            int opt = Integer.parseInt(line);
            if (opt >= 1 && opt <= 7)
                return opt;
        } catch (NumberFormatException e) {
        }
        System.out.println("Opcion invalida, intente nuevamente.");
        return -1;
    }

    private static void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> altaProducto();
            case 2 -> listaProductos();
            case 3 -> actualizarProducto();
            case 4 -> eliminarProducto();
            case 5 -> crearPedido();
            case 6 -> listarPedidos();
            case 7 -> System.out.println("sistema cerrado.");
            default -> {
            }
        }
    }

    private static void altaProducto() {
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(leerLineaNoVacia("Precio"));
            System.out.print("Stock: ");
            int stock = Integer.parseInt(leerLineaNoVacia("Stock"));
            productoService.agregarProducto(new Producto(nombre, precio, stock));
            System.out.println("Producto agregado exitosamente.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: debe ingresar un numero valido.");
        }
    }

    private static void listaProductos() {
        List<Producto> list = productoService.listarProductos();
        if (list.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private static void actualizarProducto() {
        try {
            System.out.print("ID producto a actualizar: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            Producto p = productoService.buscarPorId(id);
            if (p == null) {
                System.out.println("Producto no encontrado.");
                return;
            }
            System.out.println("Actualizando: " + p);
            System.out.print("Nuevo precio (ENTER para omitir): ");
            String precioStr = sc.nextLine().trim();
            Double nuevoPrecio = precioStr.isEmpty() ? null : Double.parseDouble(precioStr);
            System.out.print("Nuevo stock (ENTER para omitir): ");
            String stockStr = sc.nextLine().trim();
            Integer nuevoStock = stockStr.isEmpty() ? null : Integer.parseInt(stockStr);
            productoService.actualizarProducto(p, null, nuevoPrecio, nuevoStock);
            System.out.println("Producto actualizado: " + p);
        } catch (NumberFormatException ex) {
            System.out.println("Error: entrada invalida.");
        }
    }

    private static void eliminarProducto() {
        try {
            System.out.print("ID producto a eliminar: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            System.out.print("¿Confirma eliminacion? (S/N): ");
            String confirm = sc.nextLine().trim();
            if (confirm.equalsIgnoreCase("S") && productoService.eliminarProducto(id)) {
                System.out.println("Producto eliminado.");
            } else {
                System.out.println("Operacion cancelada o producto no encontrado.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error: ID invalido.");
        }
    }

    private static void crearPedido() {
        try {
            System.out.print("¿Cuantos productos desea agregar al pedido? ");
            int n = Integer.parseInt(sc.nextLine().trim());

            Map<Integer, Integer> mapaCantidades = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                System.out.print("ID producto " + i + ": ");
                int id = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Cantidad: ");
                int cant = Integer.parseInt(sc.nextLine().trim());

                mapaCantidades.put(id, mapaCantidades.getOrDefault(id, 0) + cant);
            }

            Pedido pedido = pedidoService.crearPedido(mapaCantidades, productoService.listarProductos());
            System.out.println("Pedido creado:");
            System.out.println(pedido);

        } catch (NumberFormatException ex) {
            System.out.println("Error: entrada numérica inválida.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error al crear pedido: " + ex.getMessage());
        }
    }

    private static void listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            pedidos.forEach(p -> {
                System.out.println(p);
                System.out.println("--------------------");
            });
        }
    }

    private static String leerLineaNoVacia(String campo) {
        String line;
        do {
            line = sc.nextLine().trim();
            if (line.isEmpty())
                System.out.print(campo + " no puede estar vacio. Intente de nuevo: ");
        } while (line.isEmpty());
        return line;
    }
}
