package com.ejemplo.conversorunidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etValor;
    RadioGroup rgTipo;
    RadioButton rbMetrosKm, rbKmMetros, rbGramosKg, rbKgGramos;
    RadioButton rbCelsiusFahr, rbFahrCelsius;
    Button btnCalcular, btnBorrar, btnGuardar, btnMostrar;
    TextView tvResultado;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValor = findViewById(R.id.etValor);
        rgTipo = findViewById(R.id.rgTipo);
        rbMetrosKm = findViewById(R.id.rbMetrosKm);
        rbKmMetros = findViewById(R.id.rbKmMetros);
        rbGramosKg = findViewById(R.id.rbGramosKg);
        rbKgGramos = findViewById(R.id.rbKgGramos);
        rbCelsiusFahr = findViewById(R.id.rbCelsiusFahr);
        rbFahrCelsius = findViewById(R.id.rbFahrCelsius);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);
        tvResultado = findViewById(R.id.tvResultado);

        preferencias = getSharedPreferences("MisConversiones", Context.MODE_PRIVATE);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrar();
            }
        });
    }

    public void calcular() {
        String texto = etValor.getText().toString();

        if (texto.isEmpty()) {
            Toast.makeText(this, "Debes introducir un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double valor = Double.parseDouble(texto);
        double resultado = 0;
        String tipo = "";

        if (rbMetrosKm.isChecked()) {
            resultado = valor / 1000;
            tipo = " km";
        } else if (rbKmMetros.isChecked()) {
            resultado = valor * 1000;
            tipo = " m";
        } else if (rbGramosKg.isChecked()) {
            resultado = valor / 1000;
            tipo = " kg";
        } else if (rbKgGramos.isChecked()) {
            resultado = valor * 1000;
            tipo = " g";
        } else if (rbCelsiusFahr.isChecked()) {
            resultado = (valor * 9/5) + 32;
            tipo = " °F";
        } else if (rbFahrCelsius.isChecked()) {
            resultado = (valor - 32) * 5/9;
            tipo = " °C";
        }

        tvResultado.setText("Resultado: " + resultado + tipo);
    }

    public void borrar() {
        etValor.setText("");
        tvResultado.setText("El resultado aparecerá aquí");
        rbMetrosKm.setChecked(true);
    }

    public void guardar() {
        String resultado = tvResultado.getText().toString();
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("ultima", resultado);
        editor.apply();
        Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
    }

    public void mostrar() {
        String guardada = preferencias.getString("ultima", "No hay nada guardado");
        tvResultado.setText(guardada);
    }
}
