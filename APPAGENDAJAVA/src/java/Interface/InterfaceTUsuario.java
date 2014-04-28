/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Pojo.Tusuario;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author KevinArnold
 */
public interface InterfaceTUsuario {
    public boolean register(Session session, Tusuario tUsuario)throws Exception;
    public List<Tusuario> getAll(Session session)throws Exception;
    public Tusuario getByCodigoUsuario(Session session, String codigoUsuario)throws Exception;
    public Tusuario getByCorreoElectronico(Session session, String correoElectronico)throws Exception;
    public boolean update(Session session, Tusuario tUsuario)throws Exception;
}
