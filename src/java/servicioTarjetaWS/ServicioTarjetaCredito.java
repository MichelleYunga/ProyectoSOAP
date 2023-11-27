/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioTarjetaWS;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import ModeloTarjetaCredito.TarjetaCredito;
import ModeloTarjetaCredito.Transaccion;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import modeloInicioSesion.Cliente;
import modeloInicioSesion.GenerarUsuarioId;

/**
 *
 * @author enriq
 */
@WebService(serviceName = "ServicioTarjetaCredito")
public class ServicioTarjetaCredito {

    /**
     * This is a sample web service operation
     */
    private final ArrayList<TarjetaCredito> tarjetasCredito;
    TarjetaCredito tar;
    private List<Transaccion> historialTransacciones;

    public ServicioTarjetaCredito() {
        this.tarjetasCredito = new ArrayList<>();
    }

    //METODO PARA EL REGISTRO DE LA TARJETA
    
    @WebMethod(operationName = "RegistroTarjeta")
    public String RegistroTarjeta(@WebParam(name = "numero") String numero,// el numero de la tarjeta tiene una longitud de 10
            @WebParam(name = "titular") String titular,
            @WebParam(name = "fechaVencimiento") String fechaVencimiento,
            @WebParam(name = "codigoSeguridad") String codigoSeguridad,
            @WebParam(name = "saldoDisponible") float saldoDisponible,
            @WebParam(name = "cliente") String cliente) {

        try {
            for (TarjetaCredito tarjeta : tarjetasCredito) {
                if (numero.equals(tarjeta.getNumero())) {
                    System.out.println("Ya existe esta tarjeta de crédito");
                    return "TARJETA DE CRÉDITO YA REGISTRADA"; // Tarjeta ya registrada
                }
            }

            System.out.println("Creando objeto Cliente con nombre: " + cliente);
            Cliente propietario = new Cliente(cliente);

            System.out.println("Creando objeto TarjetaCredito con número: " + numero);
            TarjetaCredito tarjeta = new TarjetaCredito(numero, titular, fechaVencimiento, codigoSeguridad, saldoDisponible, propietario);

            tarjetasCredito.add(tarjeta);

            System.out.println("Tarjeta de crédito creada exitosamente");
            return "REGISTRO DE TARJETA EXITOSO";
        } catch (Exception e) {
            throw new RuntimeException("Error durante el registro de tarjeta: " + e.getMessage(), e);
        }
    }

    //METODO PARA ACTUALIZAR LA TARJETA DE CREDITO
    //SE INGRESA el numero de la tarjeta para realizar la modificacion
    @WebMethod(operationName = "ActualizarTarjeta")
    public String ActualizarTarjeta(@WebParam(name = "numero") String numero,
            @WebParam(name = "titular") String titular,
            @WebParam(name = "fechaVencimiento") String fechaVencimiento,
            @WebParam(name = "codigoSeguridad") String codigoSeguridad,
            @WebParam(name = "saldoDisponible") float saldoDisponible) {

        try {
            // Comprobar nulidad de los parámetros
            if (numero == null || titular == null || fechaVencimiento == null || codigoSeguridad == null) {
                throw new IllegalArgumentException("Todos los parámetros deben tener valores no nulos");
            }

            boolean tarjetaEncontrada = false;

            for (TarjetaCredito tarjeta : tarjetasCredito) {
                if (numero.equals(tarjeta.getNumero())) {
                    // Validar datos de entrada antes de actualizar
                    validarDatosDeEntrada(titular, fechaVencimiento, codigoSeguridad, saldoDisponible);

                    tarjeta.setTitular(titular);
                    tarjeta.setFechaVencimiento(fechaVencimiento);
                    tarjeta.setCodigoSeguridad(codigoSeguridad);
                    tarjeta.setSaldoDisponible(saldoDisponible);
                    System.out.println("Tarjeta actualizada");
                    tarjetaEncontrada = true;
                    break;
                }
            }

            if (!tarjetaEncontrada) {
                throw new NoSuchElementException("La tarjeta con número " + numero + " no se encontró para actualizar");
            }

            return "TARJETA ACTUALIZADA EXITOSAMENTE"; // Actualización exitosa
        } catch (IllegalArgumentException | NoSuchElementException e) {
            // Manejar excepciones específicas
            System.out.println("Error al actualizar la tarjeta: " + e.getMessage());
            return "ERROR AL ACTUALIZAR TARJETA"; // Actualización fallida
        }
    }

    private void validarDatosDeEntrada(String titular, String fechaVencimiento, String codigoSeguridad, float saldoDisponible) {
        if (titular.isEmpty() || fechaVencimiento.isEmpty() || codigoSeguridad.isEmpty()) {
            throw new IllegalArgumentException("Los datos de entrada no son válidos");
        }
    }

    //TARJETA DE CREDITO
    @WebMethod(operationName = "validarFechaVencimiento")
    public boolean validarFechaVencimiento(String numeroTarjeta, String fechaVencimiento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate fechaVencimientoTarjeta = LocalDate.parse(fechaVencimiento, formatter);
            LocalDate fechaActual = LocalDate.now();

            if (fechaVencimientoTarjeta.isBefore(fechaActual)) {
                // Tarjeta vencida
                return false;
            }

            for (TarjetaCredito tarjeta : tarjetasCredito) {
                if (tarjeta.getNumero().equals(numeroTarjeta) && tarjeta.getFechaVencimiento().equals(fechaVencimiento)) {
                    // Fecha de vencimiento válida
                    System.out.println("Fecha de vencimiento valida");
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            // Se produjo un error al parsear la fecha de vencimiento
            System.out.println("Error al parsear la fecha de vencimiento: " + e.getMessage());
            return false;
        }
    }

    //METODO PARA CONSULTAR SALDO DISPONIBLE
    @WebMethod(operationName = "consultarSaldoDisponible")
    public Float consultarSaldoDisponible(@WebParam(name = "numero") String numeroTarjeta) {
        try {
            for (TarjetaCredito tarjeta : tarjetasCredito) {
                if (tarjeta.getNumero().equals(numeroTarjeta)) {
                    return tarjeta.getSaldoDisponible();
                }
            }

            return null;
        } catch (Exception e) {
            System.out.println("Error al consultar saldo disponible: " + e.getMessage());
            return null;
        }
    }

    //METODO PARA REALIZAR LA TRANSACCION
    @WebMethod(operationName = "realizartTransaccion")
    public boolean realizarTransaccion(
            @WebParam(name = "numeroTarjeta") String numeroTarjeta,
            @WebParam(name = "monto") float monto,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "fecha") String fecha) {

        try {
            validarEntrada(numeroTarjeta, monto, descripcion, fecha);

            TarjetaCredito tarjeta = buscarTarjetaCredito(numeroTarjeta);

            if (tarjeta != null && BigDecimal.valueOf(tarjeta.getSaldoDisponible()).compareTo(BigDecimal.valueOf(monto)) >= 0) {
                realizarTransaccionExitosa(tarjeta, descripcion, monto, fecha);
                System.out.println("Su transferencia fue exitosa");
                return true;
            } else {
                System.out.println("Saldo insuficiente para realizar la transferencia");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la entrada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error durante la transacción: " + e.getMessage());
        }

        System.out.println("La transferencia ha fallado");
        return false;
    }

    //METODOS PARA RELIZAR LA TRANSACCION//
    private void validarEntrada(String numeroTarjeta, float monto, String descripcion, String fecha) {
        if (numeroTarjeta == null || descripcion == null || fecha == null || monto <= 0) {
            throw new IllegalArgumentException("Parámetros de entrada no válidos");
        }
    }

    private void realizarTransaccionExitosa(TarjetaCredito tarjeta, String descripcion, float monto, String fecha) {
        Transaccion transaccion = new Transaccion(descripcion, monto, fecha);
        tarjeta.getHistorialTransacciones().add(transaccion);

        BigDecimal saldoActual = new BigDecimal(Float.toString(tarjeta.getSaldoDisponible()));
        tarjeta.setSaldoDisponible(saldoActual.subtract(BigDecimal.valueOf(monto)).floatValue());
    }

    private TarjetaCredito buscarTarjetaCredito(String numeroTarjeta) {
        for (TarjetaCredito tarjeta : tarjetasCredito) {
            if (tarjeta.getNumero().equals(numeroTarjeta)) {
                return tarjeta;
            }
        }
        return null; // Tarjeta no encontrada
    }///

    
    
    //METODO PARA VER EL HISTORIAL DE LA TARJETA DE CREDITO
    @WebMethod(operationName = "obtenerHistorialTarjeta")
    public List<Transaccion> obtenerHistorialTarjeta(@WebParam(name = "numeroTarjeta") String numeroTarjeta) {
        // Buscar la tarjeta de crédito en la lista de tarjetas
        for (TarjetaCredito tarjeta : tarjetasCredito) {
            if (tarjeta.getNumero().equals(numeroTarjeta)) {
                return tarjeta.getHistorialTransacciones();
            }
        }
        System.out.println("No se pudo encontrar el hitorial de la tarjeta");
        // Si no se encuentra la tarjeta, devolver una lista vacía
        return new ArrayList<>();
    }

    //METODO PARA RETIRAR DINERO
    @WebMethod(operationName = "retirarDinero")
    public boolean retirarDinero(@WebParam(name = "numeroTarjeta") String numeroTarjeta,
            @WebParam(name = "cedulaCliente") String cedulaCliente,
            @WebParam(name = "monto") float monto) {
        try {
            // Validar que los parámetros no sean nulos o vacíos
            validarRetiroDinero(numeroTarjeta, cedulaCliente, monto);

            // Buscar la tarjeta de crédito por número
            TarjetaCredito tarjeta = buscarTarjetaCredito(numeroTarjeta);

            // Verificar que la tarjeta existe y la cédula del cliente coincide
            if (tarjeta != null && tarjeta.getCliente().getCedula().equals(cedulaCliente)) {
                // Verificar que hay saldo suficiente para el retiro
                if (tarjeta.getSaldoDisponible() >= monto) {
                    // Realizar el retiro
                    tarjeta.setSaldoDisponible(tarjeta.getSaldoDisponible() - monto);
                    System.out.println("Retiro exitoso");
                    return true;
                } else {
                    System.out.println("Saldo insuficiente para el retiro");
                }
            } else {
                System.out.println("La tarjeta no existe o la cédula del cliente no coincide");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los parámetros: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error durante el retiro: " + e.getMessage());
        }

        System.out.println("El retiro ha fallado");
        return false;
    }

    private void validarRetiroDinero(String numeroTarjeta, String cedulaCliente, float monto) {
        if (numeroTarjeta == null || numeroTarjeta.isEmpty() || cedulaCliente == null || cedulaCliente.isEmpty() || monto <= 0) {
            throw new IllegalArgumentException("Parámetros de retiro no válidos");
        }
    }

    //VALIDACIONES
    //VALIDAR TARJETA
    public boolean validarTarjetaCredito(String numeroTarjeta) {
    // Eliminar espacios en blanco y guiones del número de tarjeta
    String numeroTarjetaSinEspacios = numeroTarjeta.replace(" ", "").replace("-", "");

    // Verificar que el número de tarjeta contenga solo dígitos
    if (!numeroTarjetaSinEspacios.matches("\\d{10}")) {
        return false;
    }

    // Aplicar el algoritmo de Luhn
    int suma = 0;
    boolean doble = false;
    for (int i = numeroTarjetaSinEspacios.length() - 1; i >= 0; i--) {
        int digito = Character.getNumericValue(numeroTarjetaSinEspacios.charAt(i));
        if (doble) {
            digito *= 2;
            if (digito > 9) {
                digito -= 9;
            }
        }
        suma += digito;
        doble = !doble;
    }

    // La tarjeta es válida si la suma es divisible por 10
    return suma % 10 == 0;
}
}
