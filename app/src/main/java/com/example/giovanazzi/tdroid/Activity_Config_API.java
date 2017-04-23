package com.example.giovanazzi.tdroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
 * Created by Laboratorio on 19/4/2017.
 */

public class Activity_Config_API extends AppCompatActivity {
    String TAG="TrackDroid";
    public SharedPreferences preferencias;

    ClientAsyncTask client;
    EditText edit_pass_actual,edit_pass_nuevo,edit_repass_nuevo,edit_IP_Menu,edit_Puerto_Menu;
    Button btn_grabarPass;
    String IP,port,password,pass_Nuevo;
    CheckBox checkBox_ip_port;
    AlertDialog.Builder dialogo1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_api);
        preferencias=getSharedPreferences("MisPref", Context.MODE_PRIVATE);
        LevantarXML();
        Botones();
        LevantarPreferencias();
        Dialogo();
        d(TAG, "ON CREATE");

    }

    @Override
    protected void onStart() {
       super.onStart();
       LevantarPreferencias();
       edit_pass_nuevo.setText("");
       edit_repass_nuevo.setText("");
        d(TAG, "ON START");

    }

    @Override
    protected void onStop(){
        super.onStop();
       AlmacenarPreferencias();
        d(TAG, "ON STOP");
    }

    void LevantarXML(){

        btn_grabarPass=(Button)findViewById(R.id.btn_grabarPass);

        edit_pass_actual=(EditText)findViewById(R.id.edit_pass_actual);

        edit_pass_nuevo=(EditText)findViewById(R.id.edit_pass_nuevo);
        edit_repass_nuevo=(EditText)findViewById(R.id.edit_repass_nuevo);

        edit_IP_Menu=(EditText)findViewById(R.id.edit_IP_Menu);
        edit_Puerto_Menu=(EditText)findViewById(R.id.edit_Puerto_Menu);

        checkBox_ip_port=(CheckBox)findViewById(R.id.check_ip_port);
    }

    void Botones(){
        edit_pass_actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogo1.show();
            }
        });

        btn_grabarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_pass_actual.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Ingrese password Actual", Toast.LENGTH_SHORT).show();
                }else{

                if (edit_pass_nuevo.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Ingrese nuevo password", Toast.LENGTH_SHORT).show();
                } else {

                    if (edit_repass_nuevo.getText().toString().matches("")) {
                        Toast.makeText(getApplicationContext(), "ReIngrese nuevo password", Toast.LENGTH_SHORT).show();

                    } else {
                        if (edit_pass_nuevo.getText().toString().matches(edit_repass_nuevo.getText().toString())) {
                            CargarPassword();
                            Toast.makeText(getApplicationContext(), "Solicitud enviada...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "No coinciden las contraseñas", Toast.LENGTH_SHORT).show();
                        }
                        edit_pass_nuevo.setText("");
                        edit_repass_nuevo.setText("");

                    }

                }
            }
            }
        });

        checkBox_ip_port.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                   edit_IP_Menu.setEnabled(true);
                   edit_Puerto_Menu.setEnabled(true);
                }else{
                   AlmacenarPreferencias();
                   edit_IP_Menu.setEnabled(false);
                   edit_Puerto_Menu.setEnabled(false);
                   Toast.makeText(getApplicationContext(),"Dato Almacenado",Toast.LENGTH_SHORT).show();
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
            d(TAG, "DATO RECIBIDO: "+ s);

            if((s == null) || (s.equals(""))){

                d(TAG, "DATO VACIO");
            } else{
                    String delimitadores = " ";
                    String[] dato = s.split(delimitadores);
                    int longitud = dato.length;
                    d(TAG, "dato[0]: "+dato[0]+" dato[1]: "+dato[1]);

                      if(dato[0].equals("111")){

                        if(dato[1].equals(pass_Nuevo)){

                            password=pass_Nuevo;
                            edit_pass_actual.setText(password);
                            Toast.makeText(getApplicationContext(),"Password:'"+password+"' fue almacenado !!",Toast.LENGTH_SHORT).show();
                            d(TAG, "Password almacenado");
                            d(TAG, "pass_Nuevo: " +pass_Nuevo);
                            d(TAG, "password: " +password);
                            AlmacenarPreferencias();
                        }

                      }


                 }
            }
    }

    private void LevantarPreferencias(){

        IP=preferencias.getString("IP", "localhost");
        port=preferencias.getString("port", "9000");
        password=preferencias.getString("password", "1234");
        edit_IP_Menu.setText(IP);
        edit_Puerto_Menu.setText(port);
        edit_pass_actual.setText(password);
    }

    private void AlmacenarPreferencias(){

        IP=edit_IP_Menu.getText().toString();
        port=edit_Puerto_Menu.getText().toString();
        password=edit_pass_actual.getText().toString();
        d(TAG, "password: "+password);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("IP", IP);
        editor.putString("port", port);
        editor.putString("password", password);
        editor.commit();

    }

    private void CargarPassword(){

        pass_Nuevo = edit_pass_nuevo.getText().toString();
        password = edit_pass_actual.getText().toString();
        d(TAG, "pass_Nuevo: " + pass_Nuevo);
        d(TAG, "password: " + password);
        client = new ClientAsyncTask();
        client.execute(IP, port, "111"); // envia passactual y nuevo
        client = new ClientAsyncTask();
        client.execute(IP, port, password + " " + pass_Nuevo); // envia passactual y nuevo
        edit_pass_nuevo.setText("");

    }

    private void Dialogo(){

        dialogo1= new AlertDialog.Builder(this);
        dialogo1.setTitle("IMPORTANTE");

        dialogo1.setMessage("CUIDADO !!!\nCambiara la contraseña local.\n" +
                "Almacenela en un lugar seguro antes de cambiarla.");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });

/*        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }

        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.menu_conf) {

            Intent Config = new Intent(getApplicationContext(),Activity_Config_API.class);
            startActivity(Config);
            d(TAG, "Menu configuracion");
            return true;
        }


        if (id == R.id.menu_solicitar_pass) {

            Toast.makeText(getApplicationContext(),"El equipo remoto mostrara la contraseña",Toast.LENGTH_LONG).show();
            client=new ClientAsyncTask();
            client.execute(IP,port,"777");
            return true;
        }


        if (id == R.id.menu_send) {


            d(TAG, "Acerca de...");
            Toast.makeText(getApplicationContext(),"Desarrollado por DiegoGiovanazzi@gmail.com",Toast.LENGTH_LONG).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}



