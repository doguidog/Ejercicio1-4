package com.example.ejercicio1_4.Clases;

public class Transacciones {

    public static final String NameDatabase = "ejercicio4";

    public static final String id = "id";
    public static  final String foto = "foto";
    public static final String nombre = "nombre";
    public static final String descripcion = "descripcion";

    public static final String TbUsuario = "usuarios";

    public static final String CreateTableUsuarios = "CREATE TABLE " + TbUsuario +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT, descripcion TEXT, foto BLOB )";

    public static final String DropTableUsuarios = "DROP TABLE IF EXIST" + TbUsuario;
}
