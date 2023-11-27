/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloInicioSesion;

import ModeloTarjetaCredito.TarjetaCredito;

/**
 *
 * @author enriq
 */
public class Cliente {

    private int idCliente;
    private String nombre;
    private String apellido;
    private String cedula;
    private String usuario;
    private String contrasena;
    private String  valContrasena;
    
    //ATRIBUTO PARA ASIGNAR UNA TARJETA A UN CLIENTE
    private TarjetaCredito tarjeta;

    public Cliente(int idCliente, String nombre, String apellido, String cedula, String usuario, String contrasena, String valContrasena) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.valContrasena = valContrasena;
    }

    public Cliente(int idCliente, String nombre, String apellido, String cedula, String usuario, String contrasena, String valContrasena, TarjetaCredito tarjeta) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.valContrasena = valContrasena;
        this.tarjeta = tarjeta;
    }

    public Cliente(String cedula) {
        this.cedula = cedula;
    }

   
    
    
    public Cliente() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getValContrasena() {
        return valContrasena;
    }

    public void setValContrasena(String valContrasena) {
        this.valContrasena = valContrasena;
    }
    
    

}
