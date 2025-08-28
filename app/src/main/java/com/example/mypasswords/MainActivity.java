package com.example.mypasswords;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText tiLocal, tiUsuario, tiSenha;
    private Button btArmazenar;
    private TextView tvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tiLocal = findViewById(R.id.tiLocal);
        tiUsuario = findViewById(R.id.tiUsuario);
        tiSenha = findViewById(R.id.tiSenha);
        btArmazenar = findViewById(R.id.btArmazenar);
        tvLista = findViewById(R.id.tvLista);

        btArmazenar.setOnClickListener(e -> { armazenarSenha(); });
        tvLista.setOnClickListener(e -> verSenhas());
        carregarSenhas();

    }

    private void carregarSenhas() {
        FileInputStream fout;
        ObjectInputStream out;

        try {
            fout = openFileInput("dados.dat");
            out = new ObjectInputStream(fout);
            Singleton.passwordList = (List<MyPassword>) out.readObject();
        }
        catch (Exception e) {
            Log.e("erroxxx ao carregar dados: ", e.getMessage());
        }
    }

    private void verSenhas() {
        Intent intent = new Intent(this, ListaActivity.class);
        startActivity(intent);
    }

    private void armazenarSenha() {
        String local, usuario, senha;
        local = tiLocal.getText().toString();
        usuario = tiUsuario.getText().toString();
        senha = tiSenha.getText().toString();

        // só salva se, pelo menos, local e senha tiver conteudo
        if (local.length() > 0 && senha.length() > 0) {
            Singleton.passwordList.add(new MyPassword(local, usuario, senha));

            Toast.makeText(MainActivity.this, "Senha " + local + " salva com sucesso!", Toast.LENGTH_LONG).show();

            tiLocal.setText("");
            tiUsuario.setText("");
            tiSenha.setText("");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        FileOutputStream fout = null;
        ObjectOutputStream out;

        try {
            fout = openFileOutput("dados.dat", MODE_PRIVATE);
            out = new ObjectOutputStream(fout);
            out.writeObject(Singleton.passwordList);
            out.close();
        }
        catch (Exception e) {
            Log.e("erroxxx ao salvar: ", e.getMessage());
        }
    }
}