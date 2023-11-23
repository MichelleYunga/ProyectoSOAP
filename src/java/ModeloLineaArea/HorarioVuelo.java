/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloLineaArea;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author enriq
 */
public class HorarioVuelo {
    
     private String idHorario;
    private Date fecha;
    private String horaSalida;
    private String horaLlegada;
    
    public HorarioVuelo() {
    }

    public HorarioVuelo(String idHorario, Date fecha, String horaSalida, String horaLlegada) {
        this.idHorario = idHorario;
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

   

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

 
    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }
    
    public boolean cocidenciaFechaHora(String fechaS,String hora){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaVueloFormateada = dateFormat.format(this.fecha);
        
        return fechaVueloFormateada.equals(dateFormat.format(fecha))&& this.horaLlegada.equals(hora);
        
    }
    
}
