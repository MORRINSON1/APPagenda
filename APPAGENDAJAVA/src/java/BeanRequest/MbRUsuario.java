/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BeanRequest;

import Clases.Encrypt;
import Dao.DaoTUsuario;
import HibernateUtil.HibernateUtil;
import Pojo.Tusuario;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author KevinArnold
 */
@ManagedBean
@RequestScoped
public class MbRUsuario {

    /**
     * Creates a new instance of MbRUsuario
     */
    private Session session;
    private Transaction transaccion;
    
    private Tusuario tUsuario;
    private List<Tusuario> listaTUsuario;
    
    private String txtContraseniaRepita;
    
    public MbRUsuario() 
    {
        this.tUsuario=new Tusuario();
        this.tUsuario.setCodigoUsuario("");
        this.tUsuario.setSexo(true);
    }
    
    public void register()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {            
            if(!this.tUsuario.getContrasenia().equals(this.txtContraseniaRepita))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coencide"));

                return;
            }

            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuario.getByCorreoElectronico(this.session, this.tUsuario.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El usuario ya se encuentra registrado en el sistema"));

                return;
            }

            this.tUsuario.setContrasenia(Encrypt.sha512(this.tUsuario.getContrasenia()));
            daoTUsuario.register(this.session, this.tUsuario);
            
            this.transaccion.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente"));

            this.tUsuario=new Tusuario();
            this.tUsuario.setCodigoUsuario("");
            this.tUsuario.setSexo(true);
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public Tusuario gettUsuario() {
        return tUsuario;
    }

    public void settUsuario(Tusuario tUsuario) {
        this.tUsuario = tUsuario;
    }

    public List<Tusuario> getListaTUsuario() {
        return listaTUsuario;
    }

    public void setListaTUsuario(List<Tusuario> listaTUsuario) {
        this.listaTUsuario = listaTUsuario;
    }

    public String getTxtContraseniaRepita() {
        return txtContraseniaRepita;
    }

    public void setTxtContraseniaRepita(String txtContraseniaRepita) {
        this.txtContraseniaRepita = txtContraseniaRepita;
    }
    
}
