package com.example.exercicios_aula;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class buscarCEPActivity extends AppCompatActivity {

    private EditText editTextCEP, editTextNumero, editTextComplemento;
    private Button buttonBuscarCEP;
    private TextView textViewLogradouro, textViewBairro, textViewCidade, textViewEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cep);

        editTextCEP = findViewById(R.id.editTextCEP);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        buttonBuscarCEP = findViewById(R.id.buttonBuscarCEP);
        textViewLogradouro = findViewById(R.id.textViewLogradouro);
        textViewBairro = findViewById(R.id.textViewBairro);
        textViewCidade = findViewById(R.id.textViewCidade);
        textViewEstado = findViewById(R.id.textViewEstado);

        // botao
        buttonBuscarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = editTextCEP.getText().toString();
                if (!cep.isEmpty()) {
                    if (isCEPValido(cep)) {
                        buscarEndereco(cep);
                    } else {
                        Toast.makeText(buscarCEPActivity.this, "CEP inválido. Digite um CEP com 8 dígitos.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(buscarCEPActivity.this, "Por favor, insira um CEP", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isCEPValido(String cep) {
        return cep.matches("\\d{8}"); // verifica se tem 8digit
    }

    private void buscarEndereco(final String cep) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String urlString = "https://viacep.com.br/ws/" + cep + "/json/";

                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();


                    JSONObject jsonResponse = new JSONObject(response.toString());

                    if (jsonResponse.has("erro") && jsonResponse.getBoolean("erro")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(buscarCEPActivity.this, "CEP não encontrado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                     String logradouro = jsonResponse.getString("logradouro");
                     String bairro = jsonResponse.getString("bairro");
                     String cidade = jsonResponse.getString("localidade");
                     String estado = jsonResponse.getString("uf");
                     String numero = editTextNumero.getText().toString();

                     String complemento = editTextComplemento.getText().toString();

                     String enderecoFormatado = formatarEndereco(logradouro, numero, complemento, bairro, cidade, estado);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewLogradouro.setText("Endereço: " + enderecoFormatado);
                            textViewBairro.setText("Bairro: " + bairro);
                            textViewCidade.setText("Cidade: " + cidade);
                            textViewEstado.setText("Estado: " + estado);


                            textViewLogradouro.setVisibility(View.VISIBLE);
                            textViewBairro.setVisibility(View.VISIBLE);
                            textViewCidade.setVisibility(View.VISIBLE);
                            textViewEstado.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(buscarCEPActivity.this, "Erro ao buscar o endereço", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private String formatarEndereco(String logradouro, String numero, String complemento, String bairro, String cidade, String estado) {
        StringBuilder endereco = new StringBuilder(logradouro + ", " + numero);

        if (!complemento.isEmpty()) {
            endereco.append(", " + complemento);
        }

        endereco.append(" - " + bairro + ", " + cidade + " – " + estado);
        return endereco.toString();
    }
}