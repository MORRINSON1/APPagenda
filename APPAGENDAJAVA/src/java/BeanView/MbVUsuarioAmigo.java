/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BeanView;

import Clases.Encrypt;
import Dao.DaoTUsuario;
import Dao.DaoTUsuarioAmigo;
import HibernateUtil.HibernateUtil;
import Pojo.Tusuario;
import Pojo.Tusuarioamigo;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author KevinArnold
 */
@Named(value = "mbVUsuarioAmigo")
@ViewScoped
public class MbVUsuarioAmigo {

    /**
     * Creates a new instance of MbVUsuarioAmigo
     */
    private Tusuarioamigo usuarioamigo;
    private Tusuario usuario;
    
    private String txtContraseniaRepita;
    
    private Session session;
    private Transaction transaccion;
    
    public MbVUsuarioAmigo() 
    {
        this.usuarioamigo=new Tusuarioamigo();
        this.usuarioamigo.setCodigoUsuarioAmigo("");
        this.usuarioamigo.setSexo(true);
    }
    
    public void register()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {            
            if(!this.usuarioamigo.getContrasenia().equals(this.txtContraseniaRepita))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coencide"));

                return;
            }

            DaoTUsuarioAmigo daoTUsuarioAmigo=new DaoTUsuarioAmigo();
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuarioAmigo.getByCorreoElectronico(this.session, this.usuarioamigo.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El correo electrónico ya se encuentra registrado en el sistema"));

                return;
            }

            this.usuarioamigo.setContrasenia(Encrypt.sha512(this.usuarioamigo.getContrasenia()));
            
            HttpSession sessionUsuario=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            this.usuarioamigo.setTusuario(daoTUsuario.getByCorreoElectronico(this.session, sessionUsuario.getAttribute("correoElectronico").toString()));
            
            daoTUsuarioAmigo.register(this.session, this.usuarioamigo);
            
            this.transaccion.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente"));

            this.usuarioamigo=new Tusuarioamigo();
            this.usuarioamigo.setCodigoUsuarioAmigo("");
            this.usuarioamigo.setSexo(true);
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
    
    public Tusuarioamigo getUsuarioamigo() {
        return usuarioamigo;
    }

    public void setUsuarioamigo(Tusuarioamigo usuarioamigo) {
        this.usuarioamigo = usuarioamigo;
    }
    
    public Tusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Tusuario usuario) {
        this.usuario = usuario;
    }

    public String getTxtContraseniaRepita() {
        return txtContraseniaRepita;
    }

    public void setTxtContraseniaRepita(String txtContraseniaRepita) {
        this.txtContraseniaRepita = txtContraseniaRepita;
    }
    
}
