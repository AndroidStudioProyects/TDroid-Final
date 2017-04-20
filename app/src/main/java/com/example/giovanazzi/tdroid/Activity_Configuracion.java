package com.example.giovanazzi.tdroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    String TAG="TrackDroid";

    ClientAsyncTask client;
    Button btn_Guardar,btn_Actualizar;
    //EditText editText_IP,editText_port;
    TextView text_Conf1,text_Conf2,text_Conf3,text_Conf4,
            text_Conf5,text_Conf6,text_Conf7,text_Conf8,text_Conf9;
    EditText edit_Conf1,edit_Conf2,edit_Conf3,edit_Conf4,
            edit_Conf5,edit_Conf6,edit_Conf7,edit_Conf8,edit_Conf9;
    String IP_Conf,Port_Conf;
    String Unidades[]={"Bar","Kg","°C","%"};
    Spinner spin_Conf1,spin_Conf2,spin_Conf3,spin_Conf4,spin_Conf5,spin_Conf6,spin_Conf7,spin_Conf8,spin_Conf9;
    ArrayAdapter<String> adaptador ;
    Bundle b;
    String Conf1,Conf2,Conf3,Conf4,Conf5,Conf6,Conf7,Conf8,Conf9,password;
    public SharedPreferences preferencias;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        preferencias=getSharedPreferences("MisPref", Context.MODE_PRIVATE);
        LevantarXML();
        SetSpiners();
        Botones();
        Spiners();

    }

    @Override
    protected void onStart() {
        super.onStart();
        preferencias=getSharedPreferences("MisPref", MODE_PRIVATE);
        IP_Conf=preferencias.getString("IP", "localhost");
        Port_Conf=preferencias.getString("Port", "9000");

        Conf1=preferencias.getString("Conf1", "nada");
        Conf2=preferencias.getString("Conf2", "Bar");
        Conf3=preferencias.getString("Conf3", "Bar");
        Conf4=preferencias.getString("Conf4", "Bar");
        Conf5=preferencias.getString("Conf5", "Bar");
        Conf6=preferencias.getString("Conf6", "Bar");
        Conf7=preferencias.getString("Conf7", "Bar");
        Conf8=preferencias.getString("Conf8", "Bar");
        Conf9=preferencias.getString("Conf9", "Bar");

        spin_Conf1.setSelection(Unidad(Conf1));
        spin_Conf2.setSelection(Unidad(Conf2));
        spin_Conf3.setSelection(Unidad(Conf3));
        spin_Conf4.setSelection(Unidad(Conf4));
        spin_Conf5.setSelection(Unidad(Conf5));
        spin_Conf6.setSelection(Unidad(Conf6));
        spin_Conf7.setSelection(Unidad(Conf7));
        spin_Conf8.setSelection(Unidad(Conf8));
        spin_Conf9.setSelection(Unidad(Conf9));

        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,"888");

    }

    void LevantarXML(){

        spin_Conf1=(Spinner)findViewById(R.id.spin_Conf1) ;
        spin_Conf2=(Spinner)findViewById(R.id.spin_Conf2) ;
        spin_Conf3=(Spinner)findViewById(R.id.spin_Conf3) ;
        spin_Conf4=(Spinner)findViewById(R.id.spin_Conf4) ;
        spin_Conf5=(Spinner)findViewById(R.id.spin_Conf5) ;
        spin_Conf6=(Spinner)findViewById(R.id.spin_Conf6) ;
        spin_Conf7=(Spinner)findViewById(R.id.spin_Conf7) ;
        spin_Conf8=(Spinner)findViewById(R.id.spin_Conf8) ;
        spin_Conf9=(Spinner)findViewById(R.id.spin_Conf9) ;

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

    void SetSpiners(){

        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.unidades,android.R.layout.simple_spinner_item);
        spin_Conf1.setAdapter(adapter);
        spin_Conf2.setAdapter(adapter);
        spin_Conf3.setAdapter(adapter);
        spin_Conf4.setAdapter(adapter);
        spin_Conf5.setAdapter(adapter);
        spin_Conf6.setAdapter(adapter);
        spin_Conf7.setAdapter(adapter);
        spin_Conf8.setAdapter(adapter);
        spin_Conf9.setAdapter(adapter);




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

    void Spiners(){

        spin_Conf1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SharedPreferences.Editor editor=preferencias.edit();
                editor.putString("Conf1",Posicion(position));
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor=preferencias.edit();
                editor.putString("Conf2",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               editor=preferencias.edit();
                editor.putString("Conf3",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               editor=preferencias.edit();
                editor.putString("Conf4",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               editor=preferencias.edit();
                editor.putString("Conf5",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editor=preferencias.edit();
                editor.putString("Conf6",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor=preferencias.edit();
                editor.putString("Conf7",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor=preferencias.edit();
                editor.putString("Conf8",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_Conf9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor=preferencias.edit();
                editor.putString("Conf9",Posicion(position));
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        password=preferencias.getString("password", "1234");

        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,"999");
        client=new ClientAsyncTask();
        client.execute(IP_Conf,Port_Conf,Conf1+" "+Conf2+" "+Conf3+" "+Conf4+" "+Conf5+" "+Conf6+" "+Conf7+" "+Conf8+" "+Conf9+" "+password);
        d(TAG,"Configuracion Enviada: "+ Conf1+" "+Conf2+" "+Conf3+" "+Conf4+" "+Conf5+" "+Conf6+" "+Conf7+" "+Conf8+" "+Conf9+" "+password);


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

    String Posicion(int pos){
        String value="Bar";

        switch (pos){

            case 0: value="Bar"; break;
            case 1: value="°C";break;
            case 2: value="Seg";break;
            case 3: value="Kg";break;
            case 4: value="%";
        }

        return value;
    }

    int Unidad(String value){
        int posicion=0;

        switch (value){

            case "Bar": posicion=0; break;
            case "°C": posicion=1;;break;
            case "Seg": posicion=2;;break;
            case "Kg": posicion=3;;break;
            case "%": posicion=4;;
        }

        return posicion;
    }

}
