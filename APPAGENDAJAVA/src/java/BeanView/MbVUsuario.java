/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BeanView;

import BeanSession.MbSLogin;
import Clases.Encrypt;
import Dao.DaoTUsuario;
import HibernateUtil.HibernateUtil;
import Pojo.Tusuario;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author KevinArnold
 */
@ManagedBean
@ViewScoped
public class MbVUsuario {

    /**
     * Creates a new instance of MbVUsuario
     */
    private Session session;
    private Transaction transaccion;
    
    private Tusuario usuario;
    private List<Tusuario> listaUsuario;
    private List<Tusuario> listaUsuarioFiltrado;
    
    private String txtContraseniaRepita;
    private UploadedFile avatar;
    
    @ManagedProperty("#{mbSLogin}")
    private MbSLogin mbSLogin;
    
    public MbVUsuario() 
    {
        this.usuario=new Tusuario();
        this.usuario.setCodigoUsuario("");
        this.usuario.setSexo(true);
    }
    
    public void register()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {            
            if(!this.usuario.getContrasenia().equals(this.txtContraseniaRepita))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Las contraseñas no coencide"));

                return;
            }

            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuario.getByCorreoElectronico(this.session, this.usuario.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El usuario ya se encuentra registrado en el sistema"));

                return;
            }

            this.usuario.setContrasenia(Encrypt.sha512(this.usuario.getContrasenia()));
            daoTUsuario.register(this.session, this.usuario);
            
            this.transaccion.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "El registro fue realizado correctamente"));

            this.usuario=new Tusuario();
            this.usuario.setCodigoUsuario("");
            this.usuario.setSexo(true);
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
    
    public List<Tusuario> getAll()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=this.session.beginTransaction();
            
            this.listaUsuario=daoTUsuario.getAll(this.session);
            
            this.transaccion.commit();
            
            return this.listaUsuario;
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
            
            return null;
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public Tusuario getByCorreoElectronico()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=this.session.beginTransaction();
            
            HttpSession sessionUsuario=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            this.usuario=daoTUsuario.getByCorreoElectronico(this.session, sessionUsuario.getAttribute("correoElectronico").toString());
            
            this.transaccion.commit();
            
            return this.usuario;
        }
        catch(Exception ex)
        {
            if(this.transaccion!=null)
            {
                this.transaccion.rollback();
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
            
            return null;
        }
        finally
        {
            if(this.session!=null)
            {
                this.session.close();
            }
        }
    }
    
    public void update()
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();

            if(daoTUsuario.getByCorreoElectronicoDiferent(this.session, this.usuario.getCodigoUsuario(), this.usuario.getCorreoElectronico())!=null)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Correo electrónico ocupado"));

                return;
            }

            daoTUsuario.update(this.session, this.usuario);
            
            this.transaccion.commit();
            
            this.mbSLogin.setCorreoElectronico(this.usuario.getCorreoElectronico());
            
            HttpSession httpSession=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            httpSession.setAttribute("correoElectronico", this.usuario.getCorreoElectronico());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Los cambios fueron guardados correctamente"));
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
    
    public void actualizarAvatar()throws IOException
    {
        InputStream inputStream=null;
        OutputStream outputStream=null;
        
        try
        {
            if(this.avatar.getSize()<=0)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ud. debe seleccionar un archivo de imagen \".png\""));
                return;
            }
            
            if(!this.avatar.getFileName().endsWith(".png"))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo debe ser con extensión \".png\""));
                return;
            }
            
            if(this.avatar.getSize()>2097152)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El archivo no puede ser más de 2mb"));
                return;
            }
            
            ServletContext servletContext=(ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String carpetaAvatar=(String)servletContext.getRealPath("/avatar");
            
            outputStream=new FileOutputStream(new File(carpetaAvatar+"/"+this.usuario.getCodigoUsuario()+".png"));
            inputStream=this.avatar.getInputstream();
            
            int read=0;
            byte[] bytes=new byte[1024];
            
            while((read=inputStream.read(bytes))!=-1)
            {
                outputStream.write(bytes, 0, read);
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto:", "Avatar actualizado correctamente"));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error fatal:", "Por favor contacte con su administrador "+ex.getMessage()));
        }
        finally
        {
            if(inputStream!=null)
            {
                inputStream.close();
            }
            
            if(outputStream!=null)
            {
                outputStream.close();
            }
        }
    }
    
    public void cargarUsuarioEditar(String codigoUsuario)
    {
        this.session=null;
        this.transaccion=null;
        
        try
        {
            DaoTUsuario daoTUsuario=new DaoTUsuario();
            
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaccion=session.beginTransaction();
            
            this.usuario=daoTUsuario.getByCodigoUsuario(this.session, codigoUsuario);
            
            RequestContext.getCurrentInstance().update("frmEditarUsuario:panelEditarUsuario");
            RequestContext.getCurrentInstance().execute("PF('dialogoEditarUsuario').show()");
            
            this.transaccion.commit();
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
    
    public Tusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Tusuario usuario) {
        this.usuario = usuario;
    }

    public List<Tusuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Tusuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    
    public List<Tusuario> getListaUsuarioFiltrado() {
        return listaUsuarioFiltrado;
    }

    public void setListaUsuarioFiltrado(List<Tusuario> listaUsuarioFiltrado) {
        this.listaUsuarioFiltrado = listaUsuarioFiltrado;
    }

    public String getTxtContraseniaRepita() {
        return txtContraseniaRepita;
    }

    public void setTxtContraseniaRepita(String txtContraseniaRepita) {
        this.txtContraseniaRepita = txtContraseniaRepita;
    }
    
    public UploadedFile getAvatar() {
        return avatar;
    }

    public void setAvatar(UploadedFile avatar) {
        this.avatar = avatar;
    }
    
    public MbSLogin getMbSLogin() {
        return mbSLogin;
    }

    public void setMbSLogin(MbSLogin mbSLogin) {
        this.mbSLogin = mbSLogin;
    }
    
}
