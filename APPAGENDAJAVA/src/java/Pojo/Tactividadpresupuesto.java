package Pojo;
// Generated 11-jul-2014 16:04:47 by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * Tactividadpresupuesto generated by hbm2java
 */
public class Tactividadpresupuesto  implements java.io.Serializable {


     private String codigoActividadPresupuesto;
     private Tactividad tactividad;
     private Tunidadmedida tunidadmedida;
     private String descripcion;
     private BigDecimal precioUnitario;
     private float cantidad;
     private Date fechaRegistro;
     private Date fechaModificacion;

    public Tactividadpresupuesto() {
    }

    public Tactividadpresupuesto(String codigoActividadPresupuesto, Tactividad tactividad, Tunidadmedida tunidadmedida, String descripcion, BigDecimal precioUnitario, float cantidad, Date fechaRegistro, Date fechaModificacion) {
       this.codigoActividadPresupuesto = codigoActividadPresupuesto;
       this.tactividad = tactividad;
       this.tunidadmedida = tunidadmedida;
       this.descripcion = descripcion;
       this.precioUnitario = precioUnitario;
       this.cantidad = cantidad;
       this.fechaRegistro = fechaRegistro;
       this.fechaModificacion = fechaModificacion;
    }
   
    public String getCodigoActividadPresupuesto() {
        return this.codigoActividadPresupuesto;
    }
    
    public void setCodigoActividadPresupuesto(String codigoActividadPresupuesto) {
        this.codigoActividadPresupuesto = codigoActividadPresupuesto;
    }
    public Tactividad getTactividad() {
        return this.tactividad;
    }
    
    public void setTactividad(Tactividad tactividad) {
        this.tactividad = tactividad;
    }
    public Tunidadmedida getTunidadmedida() {
        return this.tunidadmedida;
    }
    
    public void setTunidadmedida(Tunidadmedida tunidadmedida) {
        this.tunidadmedida = tunidadmedida;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecioUnitario() {
        return this.precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public float getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }




}


