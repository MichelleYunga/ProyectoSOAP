/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioInicioSesion;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modeloInicioSesion.Cliente;
import modeloInicioSesion.GenerarUsuarioId;

/**
 *
 * @author enriq
 */
@WebService(serviceName = "InicioSesion")
public class InicioSesion {

    /**
     * This is a sample web service operation
     */
    private GenerarUsuarioId generar = new GenerarUsuarioId();

    @WebMethod(operationName = "login")
    public String login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {

        try {
            Cliente cliente = generar.buscarCliente(username, password);
            if (cliente != null) {
                System.out.println("Credenciales correctas");
                return "Credenciales correctas";
            }
            System.out.println("Sus credenciales sin incorrectas");
            return "sus credenciales son incorrectas";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante el inicio sesion", e);
        }
    }

    
    
    //METRODO PARA VALIDAR LAS CONTRASEÑAS
    private boolean compararContraseñas(String contraseña1, String contraseña2) {
        return contraseña1.equals(contraseña2);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Registro")
    public String Registro(@WebParam(name = "nombre") String nombre, @WebParam(name = "apellido") String apellido, @WebParam(name = "cedula") String cedula, @WebParam(name = "usuaio") String usuaio, @WebParam(name = "contrasena") String contrasena, @WebParam(name = "contrasena1") String contrasena1) {

        try {
            if (generar.existeUsuario(usuaio)) {
                System.out.println("No se puede registrar, usuario ya existente");
                return "No se puede registrar, usuario ya existente";
            }
            if (!compararContraseñas(contrasena, contrasena1)) {
                System.out.println("Las contraseñas no coinciden");
                return "las contraseñas no coinciden";
            }

            Cliente nuevoCliente = generar.generarClienteConID(nombre, apellido, cedula, usuaio, contrasena, contrasena1);
            System.out.println("Usuario creado exitosamente. : " + nuevoCliente.getUsuario());
            return "Usuario creado exitosamente";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante el registro de usuario", e);

        }
    }
}
