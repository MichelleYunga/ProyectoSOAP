/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioLineaAreaWS;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import ModeloLineaArea.LineaArea;
import ModeloLineaArea.HorarioVuelo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author enriq
 */
@WebService(serviceName = "ServicioLineaArea")
public class ServicioLineaArea {

    /**
     * This is a sample web service operation
     */
    //ARRAYS
    private List<LineaArea> lineasAereas = new ArrayList<>();
    private List<HorarioVuelo> horarios = new ArrayList<>();

    @WebMethod(operationName = "RegistroHorario")
    public String RegistroHorario(
            @WebParam(name = "id") String idhorario,
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "horaSalida") String horaSalida,
            @WebParam(name = "horaLLegada") String horaLlegada) {
        try {
            if (existeHorarioConID(idhorario)) {
                return "Error: Ya existe un horario con el mismo ID.";
            }
            if (!esFechaValida(fecha) || !esHoraValida(horaSalida) || !esHoraValida(horaLlegada)) {
                return "Error: La fecha o las horas son inválidas.";
            }
            HorarioVuelo horav = new HorarioVuelo(idhorario, fecha, horaSalida, horaLlegada);
            horarios.add(horav);

            return "Horario registrado con éxito";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante el registro de horario", e);
        }
    }

    private boolean existeHorarioConID(String idHorario) {
        for (HorarioVuelo horario : horarios) {
            if (horario.getIdHorario().equals(idHorario)) {
                return true;
            }
        }
        return false;
    }

    @WebMethod(operationName = "ListarHorarios")
    public String ListarHorarios() {
        if (horarios.isEmpty()) {
            return "No hay horarios registrados.";
        }

        StringBuilder listaHorarios = new StringBuilder("Listado de Horarios:\n");

        for (HorarioVuelo horario : horarios) {
            listaHorarios.append("ID de Horario: ").append(horario.getIdHorario())
                    .append(", Fecha: ").append(horario.getFecha())
                    .append(", Hora de Salida: ").append(horario.getHoraSalida())
                    .append(", Hora de Llegada: ").append(horario.getHoraLlegada())
                    .append("\n");
        }

        return listaHorarios.toString();
    }
//**********************************************************************************************************************************
    @WebMethod(operationName = "RegistroLineaAerea")
    public String registroLineaAerea(
            @WebParam(name = "id") int idLinea,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "idHorarios") List<String> idHorarios) {
        try {
            if (lineasAereas == null) {
                lineasAereas = new ArrayList<>();
            }
            for (LineaArea li : lineasAereas) {
                if (nombre.equals(li.getNombre())) {
                    return "Línea aérea ya existente";
                }
            }
            List<HorarioVuelo> horarios = new ArrayList<>();
            for (String idHorario : idHorarios) {
                HorarioVuelo horario = buscarHorarioPorID(idHorario);
                if (horario == null) {
                    return "Horario con ID " + idHorario + " no encontrado";
                }
                horarios.add(horario);
            }
            lineasAereas.add(new LineaArea(idLinea, nombre, horarios));
            return "Registro exitoso de línea aérea";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante el registro de línea aérea", e);
        }
    }

    private HorarioVuelo buscarHorarioPorID(String idHorario) {
        // Asegúrate de que horarios esté inicializada
        if (horarios == null) {
            horarios = new ArrayList<>();
        }
        // Buscar el horario en la lista de horarios por el ID
        for (HorarioVuelo horario : horarios) {
            if (idHorario.equals(horario.getIdHorario())) {
                return horario;
            }
        }
        return null;
    }

    @WebMethod(operationName = "ListarLieneasAereas")
    public String ListarLieneasAereas() {
        if (lineasAereas.isEmpty()) {
            return "No hay líneas aéreas registradas.";
        }
        StringBuilder listaLinAereas = new StringBuilder("Listado de Líneas Aéreas:\n");
        for (LineaArea linA : lineasAereas) {
            listaLinAereas.append("ID de Línea: ").append(linA.getIdLineaAerea())
                    .append(", Nombre: ").append(linA.getNombre())
                    .append(", Horarios:\n");
            for (HorarioVuelo horario : horarios) {
                listaLinAereas.append("  - ID de Horario: ").append(horario.getIdHorario())
                        .append(", Fecha: ").append(horario.getFecha())
                        .append(", Hora Salida: ").append(horario.getHoraSalida())
                        .append(", Hora Llegada: ").append(horario.getHoraLlegada())
                        .append("\n");
            }
            listaLinAereas.append("\n");
        }
        return listaLinAereas.toString();
    }

    @WebMethod(operationName = "BuscarLineaArea")
    public String buscarLineaArea(@WebParam(name = "nombre") String nombre) {
        try {
            if (lineasAereas == null) {
                return "No hay líneas aéreas registradas.";
            }
            StringBuilder infoLineasAereas = new StringBuilder("Información de la(s) Línea(s) Aérea(s):\n");

            for (LineaArea lineaA : lineasAereas) {
                if (nombre.equals(lineaA.getNombre())) {
                    infoLineasAereas.append("ID de Línea: ").append(lineaA.getIdLineaAerea())
                            .append(", Nombre: ").append(lineaA.getNombre())
                            .append(", Horarios:\n");
                    for (HorarioVuelo horario : horarios) {
                        infoLineasAereas.append("  - ID de Horario: ").append(horario.getIdHorario())
                                .append(", Fecha: ").append(horario.getFecha())
                                .append(", Hora Salida: ").append(horario.getHoraSalida())
                                .append(", Hora Llegada: ").append(horario.getHoraLlegada())
                                .append("\n");
                    }
                }
            }
            // Verificar si se encontró la línea aérea
            if (infoLineasAereas.toString().equals("Información de la(s) Línea(s) Aérea(s):\n")) {
                return "No se encontró una línea aérea con el nombre proporcionado.";
            } else {
                return infoLineasAereas.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante la búsqueda de línea aérea", e);
        }
    }
//**************************************************************************************************************

    @WebMethod(operationName = "BuscarVuelos")
    public List<HorarioVuelo> buscarVuelosDisponibles(
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "hora") String hora) {

        try {
            if (!esFechaValida(fecha) || !esHoraValida(hora)) {
                return new ArrayList<>();
            }

            List<HorarioVuelo> vuelosDisponibles = new ArrayList<>();

            for (HorarioVuelo vuelo : horarios) {
                if (vuelo.coincidenciaFechaHora(fecha, hora)) {
                    vuelosDisponibles.add(vuelo);
                }
            }

            return vuelosDisponibles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error durante la búsqueda de vuelos disponibles", e);
        }
    }

    @WebMethod(operationName = "CambiarVuelo")
    public boolean cambiarVuelo(
            @WebParam(name = "numeroVuelo") String idVuelo, // Cambiado a String
            @WebParam(name = "nuevaFecha") String nuevaFecha,
            @WebParam(name = "nuevaHora") String nuevaHora) {

        if (!esFechaValida(nuevaFecha) || !esHoraValida(nuevaHora)) {
            return false;
        }

        for (HorarioVuelo vuelo : horarios) {
            if (vuelo.getIdHorario().equals(idVuelo)) {
                vuelo.setFecha(nuevaFecha);
                vuelo.setHoraSalida(nuevaFecha);
                return true;
            }
        }

        return false;
    }

    @WebMethod(operationName = "AnularVuelo")
    public Boolean anularVuelo(@WebParam(name = "numeroVuelo") String numeroVuelo) {

        for (HorarioVuelo vuelo : horarios) {
            if (vuelo.getIdHorario().equals(numeroVuelo)) {
                horarios.remove(vuelo);
                return true;
            }
        }
        return false;
    }

    //VALIDACIONES
    //BUSCAR VUELOS DISPONIBLES EN BASE A FECHA Y HORA
    private boolean esFechaValida(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean esHoraValida(String hora) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        // Compilar la expresión regular
        Pattern pattern = Pattern.compile(regex);
        // Crear un matcher con la hora proporcionada
        Matcher matcher = pattern.matcher(hora);
        // Verificar si la hora coincide con el formato esperado
        return matcher.matches();
    }
    private Date convertirfecha(String fecha) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("ERROR");
            return null;
        }
    }

}
