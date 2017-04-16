package com.example.giovanazzi.tdroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.util.Log.d;

/**
 * Created by Diego on 15/04/2017.
 */

public class Activity_Configuracion extends AppCompatActivity {
    ClientAsyncTask client;
    Button btn_Guardar,btn_Actualizar;
    //EditText editText_IP,editText_port;
    TextView textView,text_Conf1,text_Conf2,text_Conf3,text_Conf4,
            text_Conf5,text_Conf6,text_Conf7,text_Conf8,text_Conf9;
    EditText edit_Conf1,edit_Conf2,edit_Conf3,edit_Conf4,
            edit_Conf5,edit_Conf6,edit_Conf7,edit_Conf8,edit_Conf9;
    String IP_Conf,Port_Conf;

    String Conf1,Conf2,Conf3,Conf4,Conf5,Conf6,Conf7,Conf8,Conf9;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        LevantarXML();
        Botones();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle b = this.getIntent().getExtras();
        textView.setText(b.getString("IP"));

        IP_Conf=b.getString("IP");
        Port_Conf=b.getString("PORT");


        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,"888");

    }

    void LevantarXML(){

        textView=(TextView)findViewById(R.id.text_AcConf);
        text_Conf1=(TextView)findViewById(R.id.text_conf1);
        text_Conf2=(TextView)findViewById(R.id.text_conf2);
        text_Conf3=(TextView)findViewById(R.id.text_conf3);
        text_Conf4=(TextView)findViewById(R.id.text_conf4);
        text_Conf5=(TextView)findViewById(R.id.text_conf5);
        text_Conf6=(TextView)findViewById(R.id.text_conf6);
        text_Conf7=(TextView)findViewById(R.id.text_conf7);
        text_Conf8=(TextView)findViewById(R.id.text_conf8);
        text_Conf9=(TextView)findViewById(R.id.text_conf9);

        edit_Conf1=(EditText)findViewById(R.id.edit_conf1);
        edit_Conf2=(EditText)findViewById(R.id.edit_conf2);
        edit_Conf3=(EditText)findViewById(R.id.edit_conf3);
        edit_Conf4=(EditText)findViewById(R.id.edit_conf4);
        edit_Conf5=(EditText)findViewById(R.id.edit_conf5);
        edit_Conf6=(EditText)findViewById(R.id.edit_conf6);
        edit_Conf7=(EditText)findViewById(R.id.edit_conf7);
        edit_Conf8=(EditText)findViewById(R.id.edit_conf8);
        edit_Conf9=(EditText)findViewById(R.id.edit_conf9);

        btn_Guardar=(Button)findViewById(R.id.btn_guardar);
        btn_Actualizar=(Button)findViewById(R.id.btn_Actualizar);

    }

    void Botones(){

        btn_Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client=new ClientAsyncTask();
                client.execute(IP_Conf,Port_Conf,"888");
            }
        });

        btn_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LevantarDatos();
            }
        });

    }

    void LevantarDatos(){

        Conf1=edit_Conf1.getText().toString();
        Conf2=edit_Conf2.getText().toString();
        Conf3=edit_Conf3.getText().toString();
        Conf4=edit_Conf4.getText().toString();
        Conf5=edit_Conf5.getText().toString();
        Conf6=edit_Conf6.getText().toString();
        Conf7=edit_Conf7.getText().toString();
        Conf8=edit_Conf8.getText().toString();
        Conf9=edit_Conf9.getText().toString();

        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,"999");
        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,Conf1+" "+Conf2+" "+Conf3+" "+Conf4+" "+Conf5+" "+Conf6+" "+Conf7+" "+Conf8+" "+Conf9);


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

            if((s == null) || (s.equals(""))){

            } else {
              //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                String delimitadores = " ";
                String[] dato = s.split(delimitadores);
                int longitud = dato.length;
                d("CONFIG", "Longitud: "+longitud);
                String Conf1="0",Conf2="0", Conf3="0", Conf4="0",Conf5="0",Conf6="0" ,Conf7="0", Conf8="0",Conf9="0";

                if(longitud==1) {

                    Toast.makeText(getApplicationContext(),"Configuración Exitosa !!",Toast.LENGTH_SHORT).show();

                }
              if(longitud==9) {

                  Toast.makeText(getApplicationContext(),"Lectura de configuración Exitosa !!!",Toast.LENGTH_SHORT).show();

                  edit_Conf1.setText(dato[0]);
                  edit_Conf2.setText(dato[1]);
                  edit_Conf3.setText(dato[2]);
                  edit_Conf4.setText(dato[3]);
                  edit_Conf5.setText(dato[4]);
                  edit_Conf6.setText(dato[5]);
                  edit_Conf7.setText(dato[6]);
                  edit_Conf8.setText(dato[7]);
                  edit_Conf9.setText(dato[8]);
                  Toast.makeText(getApplicationContext(),"Lectura de configuración Exitosa !!!",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }




}
