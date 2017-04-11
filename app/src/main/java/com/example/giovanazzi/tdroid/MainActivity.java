package com.example.giovanazzi.tdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Switch switch_1,switch_2,switch_3,switch_4,switch_5,switch_6,switch_7,switch_8,switch_9;
    Button btn_Enviar,btn_Conf_IP;
    EditText editText_IP,editText_port;
    String IP,port;
    int puerto;
    public SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias=getSharedPreferences("MisPref", Context.MODE_PRIVATE);
        Levantar_XML();
        LevantarPreferencias();
        Botones();

    }

    void Levantar_XML(){

        switch_1=(Switch) findViewById(R.id.switch_1);
        switch_2=(Switch) findViewById(R.id.switch_2);
        switch_3=(Switch) findViewById(R.id.switch_3);
        switch_4=(Switch) findViewById(R.id.switch_4);
        switch_5=(Switch) findViewById(R.id.switch_5);
        switch_6=(Switch) findViewById(R.id.switch_6);
        switch_7=(Switch) findViewById(R.id.switch_7);
        switch_8=(Switch) findViewById(R.id.switch_8);
        switch_9=(Switch) findViewById(R.id.switch_9);

        btn_Enviar=(Button)findViewById(R.id.btn_Enviar);
        btn_Conf_IP=(Button)findViewById(R.id.btn_ConfIP);

        editText_IP=(EditText)findViewById(R.id.editText_IP);
        editText_port=(EditText)findViewById(R.id.editText_Port);

     //   puerto=Integer.parseInt(editText_port.getText().toString());

    }

    void Botones(){

        btn_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                client.execute(new String[]{IP,port,"000"});
            }
        });

        btn_Conf_IP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IP=editText_IP.getText().toString();
                port=editText_port.getText().toString();
                AlmacenarPreferencias();
            }
        });


        switch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){
                    client.execute(new String[]{IP,port,"101"});
                }else{

                    client.execute(new String[]{IP,port,"100"});
                }
            }
        });

        switch_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"201"});
                }else{
                    client.execute(new String[]{IP,port,"200"});
                }
            }
        });

        switch_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){
                   client.execute(new String[]{IP,port,"301"});
                }else{
                    client.execute(new String[]{IP,port,"300"});
                }
            }
        });

        switch_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                 client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"401"});
                }else{
                    client.execute(new String[]{IP,port,"400"});
                }
            }
        });

        switch_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"501"});
                }else{
                    client.execute(new String[]{IP,port,"500"});
                }
            }
        });

        switch_6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){
                    client.execute(new String[]{IP,port,"601"});
                }else{
                    client.execute(new String[]{IP,port,"600"});
                }
            }
        });

        switch_7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"701"});
                }else{
                   client.execute(new String[]{IP,port,"700"});
                }
            }
        });

        switch_8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ClientAsyncTask client;
                     client=new ClientAsyncTask();
                    if(isChecked){
                       client.execute(new String[]{IP,port,"801"});
                    }else{
                        client.execute(new String[]{IP,port,"800"});
                    }
                }
        });

        switch_9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientAsyncTask client;
                client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"901"});
                }else{
                    client.execute(new String[]{IP,port,"900"});
                }
            }
        });


    }

    class ClientAsyncTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String result = null;
            try {
                //Create a client socket and define internet address and the port of the server
                Socket socket = new Socket(params[0],Integer.parseInt(params[1]));
                //Get the input stream of the client socket
                InputStream is = socket.getInputStream();
                //Get the output stream of the client socket
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                //Write data to the output stream of the client socket
                out.println(params[2]);
                //Buffer the data coming from the input stream
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                //Read data in the input buffer
                result = br.readLine();
                //Close the client socket
                socket.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        }
    }

    private void AlmacenarPreferencias(){

        Log.d("Preferencias","Guardar");

        IP=editText_IP.getText().toString();
        port=editText_port.getText().toString();
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("IP", IP);
        editor.putString("port", port);
        editor.commit();
        Toast.makeText(getApplicationContext(),"IP: "+IP+" : "+port+" Almacenado",Toast.LENGTH_SHORT).show();

    }

    private void LevantarPreferencias(){

        editText_IP.setText(preferencias.getString("IP", "localhost"));
        editText_port.setText(preferencias.getString("port", "9000"));
        IP=preferencias.getString("IP", "localhost");
        port=preferencias.getString("port", "9000");
    }
}

