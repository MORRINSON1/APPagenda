/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Interface.InterfaceTUsuario;
import Pojo.Tusuario;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author KevinArnold
 */
public class DaoTUsuario implements InterfaceTUsuario{

    @Override
    public boolean register(Session session, Tusuario tUsuario) throws Exception {
        session.save(tUsuario);
        
        return true;
    }

    @Override
    public List<Tusuario> getAll(Session session) throws Exception {
        String hql="from Tusuario";
        Query query=session.createQuery(hql);
        
        List<Tusuario> listaTUsuario=(List<Tusuario>) query.list();
        
        return listaTUsuario;
    }

    @Override
    public Tusuario getByCodigoUsuario(Session session, String codigoUsuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Tusuario getByCorreoElectronico(Session session, String correoElectronico) throws Exception {
        String hql="from Tusuario where correoElectronico=:correoElectronico";
        Query query=session.createQuery(hql);
        query.setParameter("correoElectronico", correoElectronico);
        
        Tusuario tUsuario=(Tusuario) query.uniqueResult();
        
        return tUsuario;
    }

    @Override
    public boolean update(Session session, Tusuario tUsuario) throws Exception {
        session.update(tUsuario);
        
        return true;
    }
    
}
