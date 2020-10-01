package com.example.serverclienteco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Button bt_ingresar;
    private EditText et_User, et_Password;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_ingresar = findViewById(R.id.bt_Ingresar);
        et_Password = findViewById(R.id.et_Password);
        et_User = findViewById(R.id.et_User);

        initClient();

        bt_ingresar.setOnClickListener(
                (v)->{
                    Gson gson = new Gson();
                    boolean isEmpty = et_User.getText().toString().trim().isEmpty() ||  et_Password.getText().toString().trim().isEmpty();
                    if(!isEmpty){
                        String username = et_User.getText().toString();
                        String password = et_Password.getText().toString();

                        User obj = new User(username,password);
                        String json = gson.toJson(obj);
                        sendMessage(json);
                    }else{
                        String msgNo = "Llena todos los campos para continuar";
                        Toast toast = Toast.makeText(this, msgNo, Toast.LENGTH_LONG);
                        toast.show();
                    }


                }
        );
    }

    public void initClient(){
        new Thread(
                () ->{
                    try {
                        socket = new Socket("192.168.0.6", 5000);

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        reader = new BufferedReader(isr);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        writer = new BufferedWriter(osw);
                        Gson gson = new Gson();

                        while(true) {

                            String line = reader.readLine();
                            Comprobacion obj = gson.fromJson(line,Comprobacion.class);
                            Log.e("Mensaje recibido ", line);

                            if(obj.isRegistrado()){
                                runOnUiThread(
                                        ()->{
                                            Intent i = new Intent(this, Welcome_Activity.class);
                                            startActivity(i);
                                        }
                                );

                            }else{
                                runOnUiThread(
                                        ()-> {
                                            String msgNo = "Usuario y contraseña no son correcto";
                                            Toast toast = Toast.makeText(this, msgNo, Toast.LENGTH_LONG);
                                            toast.show();
                                        });
                            }

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ).start();
    }

    public void sendMessage(String msg) {
        new Thread(
                ()->{
                    try {
                        writer.write(msg+"\n");
                        writer.flush();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        ).start();
    }

}