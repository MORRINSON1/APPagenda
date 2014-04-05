/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface;

import Pojo.Tusuario;
import java.util.List;

/**
 *
 * @author KevinArnold
 */
public interface InterfaceTUsuario {
    public boolean register(Tusuario tUsuario)throws Exception;
    public List<Tusuario> get()throws Exception;
    public Tusuario getList(String codigoUsuario)throws Exception;
    public boolean update(Tusuario tUsuario)throws Exception;
}
