package com.example.mypasswords;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListaActivity extends AppCompatActivity {
    private ListView listView;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Singleton.passwordList));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyPassword myPassword = (MyPassword) adapterView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaActivity.this);
                builder.setTitle("Confirmar Exclusão");
                builder.setMessage("Você deseja confirmar a exclusão da senha de "+myPassword.getLocal());

                // botão para confirmar exclusão
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        Singleton.passwordList.remove(i);
                        listView.setAdapter(new ArrayAdapter<>(ListaActivity.this, android.R.layout.simple_list_item_1, Singleton.passwordList));
                        Toast.makeText(ListaActivity.this, "Senha de " + myPassword.getLocal() + " excluída!", Toast.LENGTH_SHORT).show();
                    }
                });

                // botão para cancelar exclusão
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ListaActivity.this, "Exclusão cancelada!", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });
    }
}