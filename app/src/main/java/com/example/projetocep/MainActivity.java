package com.example.projetocep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetocep.model.CEP;
import com.example.projetocep.service.Conexao;

import org.json.JSONException;
import org.json.JSONObject;

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
                if(etCep.getText().toString().length() > 0 && !etCep.getText().toString().equals("") && etCep.getText().toString().length() == 8) {
                    cep = new CEP();
                    Tarefa tarefa = new Tarefa();
                    tarefa.execute("https://viacep.com.br/ws/" + etCep.getText().toString() + "/json/");
                }else  tvResposta.setText("CEP INVÁLIDO!");
            }
        });

    }

    private class Tarefa extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            String retorno = Conexao.getDados(strings[0]);
            try {
                JSONObject retornoCEP = new JSONObject(retorno);

                cep.setCep(retornoCEP.getString("cep"));
                Log.i("CEP",cep.getCep());

                cep.setLogradouro(retornoCEP.getString("logradouro"));
                cep.setComplemento(retornoCEP.getString("complemento"));
                cep.setBairro(retornoCEP.getString("bairro"));
                cep.setLocalidade(retornoCEP.getString("localidade"));
                cep.setUf(retornoCEP.getString("uf"));
                cep.setIbge(retornoCEP.getString("ibge"));
                cep.setGia(retornoCEP.getString("gia"));
                cep.setDdd(retornoCEP.getString("ddd"));
                cep.setSiafi(retornoCEP.getString("siafi"));

                Log.i("CEP ENCONTRADO: \n",cep.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return cep.toString();
        }

        @Override
        protected void onPostExecute(String s) {

            tvResposta.setText(s);
        }

    }
}

