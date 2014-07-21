/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import Interface.InterfaceTUsuarioAmigo;
import Pojo.Tusuarioamigo;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author KevinArnold
 */
public class DaoTUsuarioAmigo implements InterfaceTUsuarioAmigo{

    @Override
    public boolean register(Session session, Tusuarioamigo tUsuarioamigo) throws Exception {
        session.save(tUsuarioamigo);
        
        return true;
    }

    @Override
    public Tusuarioamigo getByCorreoElectronico(Session session, String correoElectronico) throws Exception {
        String hql="from Tusuarioamigo where correoElectronico=:correoElectronico";
        Query query=session.createQuery(hql);
        query.setParameter("correoElectronico", correoElectronico);
        
        return (Tusuarioamigo) query.uniqueResult();
    }
    
    @Override
    public List<Tusuarioamigo> getByCodigoUsuario(Session session, String codigoUsuario)throws Exception
    {
        String hql="from Tusuarioamigo where codigoUsuario=:codigoUsuario";
        Query query=session.createQuery(hql);
        query.setParameter("codigoUsuario", codigoUsuario);
        
        return (List<Tusuarioamigo>) query.list();
    }
    
}
