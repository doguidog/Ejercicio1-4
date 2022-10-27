package com.example.ejercicio1_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ejercicio1_4.Clases.Transacciones;
import com.example.ejercicio1_4.Conexion.SQLiteConexion;

import java.io.ByteArrayInputStream;

public class ActivityFoto extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
    ImageView foto2;
    Button btnlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        btnlista = (Button) findViewById(R.id.btnlista);
        foto2 = (ImageView) findViewById(R.id.foto2);

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityLista.class);
                startActivity(intent);
            }
        });

        Bitmap recuperarFoto = buscarImagen(getIntent().getStringExtra("codigoParaFoto"));
        foto2.setImageBitmap(recuperarFoto);

    }

    public Bitmap buscarImagen(String id) {
        SQLiteDatabase db = conexion.getWritableDatabase();

        String sql = "SELECT foto FROM usuarios WHERE id =" + id;
        Cursor cursor = db.rawQuery(sql, new String[] {});
        Bitmap bitmap = null;
        if(cursor.moveToFirst()){
            byte[] blob = cursor.getBlob(0);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        return bitmap;
    }
}