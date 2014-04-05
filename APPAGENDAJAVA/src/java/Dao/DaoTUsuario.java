/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Dao;

import HibernateUtil.HibernateUtil;
import Interface.InterfaceTUsuario;
import Pojo.Tusuario;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author KevinArnold
 */
public class DaoTUsuario implements InterfaceTUsuario{
    private Session session;

    @Override
    public boolean register(Tusuario tUsuario) throws Exception {
        session=HibernateUtil.getSessionFactory().openSession();
        Transaction transaction=session.beginTransaction();
        session.save(tUsuario);
        transaction.commit();
        session.close();
        
        return true;
    }

    @Override
    public List<Tusuario> get() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tusuario getList(String codigoUsuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Tusuario tUsuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
