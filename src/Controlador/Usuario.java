/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.usuario;

/**
 *
 * @author pablo
 */
public class Usuario {

    private final Connection cnn;

    public Usuario(Connection cnn) {
        this.cnn = cnn;
    }
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void insertar(usuario usu) {

        try {
            String sql = "Insert Into Usuarios (Rut, Nombre, Pass) VALUES (?,?,?)";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, usu.getRut());
            ps.setString(2, usu.getNombre());
            ps.setString(3, usu.getPass());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.cerrar(ps);
        }
    }

    public boolean iniciarSesion(usuario usu) {

        try {
            String sql = "SELECT Rut, Nombre, Pass FROM Usuarios WHERE Rut = ? ";
            ps = cnn.prepareStatement(sql);
            ps.setString(1, usu.getRut());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (usu.getPass().equals(rs.getString(3))) {
                    usu.setNombre(rs.getString(2));
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            Conexion.cerrar(rs, ps);
        }
    }
}
