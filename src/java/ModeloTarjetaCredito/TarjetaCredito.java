/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloTarjetaCredito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modeloInicioSesion.GenerarUsuarioId;
import modeloInicioSesion.Cliente;

/**
 *
 * @author enriq
 */
public class TarjetaCredito {

    private String numero;
    private String titular;
    private String fechaVencimiento;
    private String codigoSeguridad;
    //Este atributo sirve para el ingreso de dinero al momento de registrar la tarjeta
    private float saldoDisponible;
    private List<Transaccion> historialTransacciones;
    //ATRIBUTO PARA ASIGNAR CLIENTE A UNA TARJETA DE CREDITO Y VICEVERSA
    private Cliente cliente;

    public TarjetaCredito(String numero, String titular, String fechaVencimiento, String codigoSeguridad, float saldoDisponible, Cliente cliente) {
        this.numero = numero;
        this.titular = titular;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoSeguridad = codigoSeguridad;
        this.saldoDisponible = saldoDisponible;
        this.cliente = cliente;
    }
    
    
    

    public TarjetaCredito() {
    }
    
    
    

    public TarjetaCredito(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public float getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(float saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
    
       public void agregarTransaccion(String descripcion, double monto, Date fecha) {
        this.historialTransacciones.add(new Transaccion(descripcion, monto, fecha));
    }
       
    public List<Transaccion> getHistorialTransacciones() {
        return this.historialTransacciones;
    }

}