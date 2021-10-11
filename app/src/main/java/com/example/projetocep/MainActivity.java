package com.example.projetocep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetocep.model.CEP;
import com.example.projetocep.service.Conexao;

public class MainActivity extends AppCompatActivity {
    TextView tvResposta;
    CEP cep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etCep = findViewById(R.id.etMain_cep);

        tvResposta = findViewById(R.id.etMain_resposta);
        Button btn = findViewById(R.id.btnMain_buscarCep);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tarefa tarefa = new Tarefa();
                tarefa.execute("https://viacep.com.br/ws/"+etCep.getText().toString()+"/json/");
            }
        });

    }

    private class Tarefa extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            String retorno = Conexao.getDados(strings[0]);
            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {

            tvResposta.setText(s);
        }

    }
}

