package com.example.ejercicio1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ejercicio1_4.Clases.Transacciones;
import com.example.ejercicio1_4.Clases.Usuario;
import com.example.ejercicio1_4.Conexion.SQLiteConexion;

import java.util.ArrayList;

public class ActivityLista extends AppCompatActivity {

    Button btnmenu,btnverfoto;
    ListView lista;
    SQLiteConexion conexion;
    ArrayList<Usuario> listaUsuario;
    ArrayList <String> arrayUsuario;
    Intent intent;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        conexion = new SQLiteConexion(this, Transacciones.NameDatabase,null,1);
        casteo();

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        obtenerlistaUsuarios();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayUsuario);
        lista.setAdapter(adp);

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setUsuarioSeleccionado();
            }
        });*/

        btnverfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(),ActivityFoto.class);
                    //intent.putExtra("codigoParaFoto",getIntent().getStringExtra("codigo")+" ");
                    intent.putExtra("codigoParaFoto", usuario.getId()+"");
                    startActivity(intent);
                }catch (NullPointerException e){
                    Intent intent = new Intent(getApplicationContext(),ActivityFoto.class);
                    intent.putExtra("codigoParaFoto", "1");
                    startActivity(intent);
                }
            }
        });

    }

    public void casteo(){
        btnmenu = (Button) findViewById(R.id.btnmenu);
        btnverfoto = (Button) findViewById(R.id.btnverfoto);
        lista = (ListView) findViewById(R.id.lista);
    }


    //Metodo obtener de la lista de contactos
    private void obtenerlistaUsuarios() {
        SQLiteDatabase db = conexion.getReadableDatabase();

        Usuario list_contact = null;

        listaUsuario = new ArrayList<Usuario>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.TbUsuario, null);

        while (cursor.moveToNext())
        {
            list_contact = new Usuario();
            list_contact.setId(cursor.getInt(0));
            list_contact.setNombre(cursor.getString(1));
            list_contact.setDescripcion(cursor.getString(2));
            listaUsuario.add(list_contact);
        }
        cursor.close();

        llenarlista();

    }

    private void llenarlista()
    {
        arrayUsuario = new ArrayList<String>();

        for (int i=0; i < listaUsuario.size();i++)
        {
            arrayUsuario.add(listaUsuario.get(i).getNombre()+" | "+
                    listaUsuario.get(i).getDescripcion());

        }
    }


}