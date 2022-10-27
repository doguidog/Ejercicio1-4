package com.example.ejercicio1_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ejercicio1_4.Clases.Transacciones;
import com.example.ejercicio1_4.Conexion.SQLiteConexion;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText txtnombre,txtdescripcion;
    ImageView foto1;
    Button btntomarfoto,btnsql,btnverlista;

    static final int PETICION_CAMARA = 100;
    static final int TAKE_PIC_REQUEST = 101;
    Bitmap foto;

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        casteo();

        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarPermisos();
            }
        });

        btnsql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validarDatos();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Tomese una foto porfavor.",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnverlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intencion = new Intent(getApplicationContext(),ActivityLista.class);
                startActivity(intencion);
            }
        });


    }

    public void casteo(){
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtdescripcion = (EditText) findViewById(R.id.txtdescripcion);
        foto1 = (ImageView) findViewById(R.id.foto1);
        btntomarfoto = (Button) findViewById(R.id.btntomarfoto);
        btnsql = (Button) findViewById(R.id.btnsql);
        btnverlista = (Button) findViewById(R.id.btnverlista);
    }



    //Metodo que pide permisos para tomar fotos
    private void tomarPermisos() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},PETICION_CAMARA);
        }else{
            tomarFoto();
        }
    }

    //Metodo que toma fotos
    private void tomarFoto() {
        Intent takepic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takepic.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takepic,TAKE_PIC_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requescode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requescode, resultCode, data);

        if(requescode == TAKE_PIC_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            foto = (Bitmap) extras.get("data");
            foto1.setImageBitmap(foto);
        }else if (resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            foto1.setImageURI(imageUri);
        }
    }

    //Metodo que guarda los contactos

    private void guardarContacto(Bitmap bitmap) {
        db = conexion.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] ArrayFoto  = stream.toByteArray();

        ContentValues valores = new ContentValues();

        valores.put(String.valueOf(Transacciones.foto),ArrayFoto);
        valores.put(Transacciones.nombre, txtnombre.getText().toString());
        valores.put(Transacciones.descripcion, txtdescripcion.getText().toString());


        Long resultado = db.insert(Transacciones.TbUsuario, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro #" + resultado.toString() + " Ingresado"
                ,Toast.LENGTH_LONG).show();

        db.close();


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    //Metodo que valida el ingreso de datos
    private void validarDatos() {
        if (txtnombre.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Debe de escribir un nombre" ,Toast.LENGTH_LONG).show();
        }else if (txtdescripcion.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Debe de escribir una descripcion" ,Toast.LENGTH_LONG).show();
        }else{
            guardarContacto(foto);
        }
    }

}