package com.example.serverclienteco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                    String username = et_User.getText().toString();
                    String password = et_Password.getText().toString();
                    sendMessage(username+"----"+password);
                    //Intent i = new Intent(this, Welcome_Activity.class);
                    //startActivity(i);

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

                        while(true) {
                            String line = reader.readLine();

                            runOnUiThread(
                                    ()->{
                                       Toast toast = Toast.makeText(this,line,Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                            );

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