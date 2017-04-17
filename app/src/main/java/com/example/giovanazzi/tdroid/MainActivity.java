package com.example.giovanazzi.tdroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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


public class MainActivity extends AppCompatActivity {

    Switch switch_1,switch_2,switch_3,switch_4,switch_5,switch_6,switch_7,switch_8,switch_9,switch_10,switch_11;
    Switch switch_IN_1,switch_IN_2,switch_IN_3,switch_IN_4,switch_IN_5,switch_IN_6,switch_IN_7,switch_IN_8;
    Button btn_Enviar,btn_Config;
    EditText editText_IP,editText_port;
    TextView text_H3,text_H2,text_H1,text_P3,text_P2,text_P1,text_T3,text_K2,text_K3,text_K1,text_T2,text_T1,text_Inputs,text_Outputs;
    String IP,port;
    CheckBox checkBox_Conf;
    String TAG="TrackDroid";
    public SharedPreferences preferencias;
    ClientAsyncTask client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias=getSharedPreferences("MisPref", Context.MODE_PRIVATE);
        Levantar_XML();
        HabilitarSw(false);
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
        switch_10=(Switch) findViewById(R.id.switch_10);
        switch_11=(Switch) findViewById(R.id.switch_11);
        switch_IN_1=(Switch)findViewById(R.id.switch_IN_1);
        switch_IN_2=(Switch)findViewById(R.id.switch_IN_2);
        switch_IN_3=(Switch)findViewById(R.id.switch_IN_3);
        switch_IN_4=(Switch)findViewById(R.id.switch_IN_4);
        switch_IN_5=(Switch)findViewById(R.id.switch_IN_5);
        switch_IN_6=(Switch)findViewById(R.id.switch_IN_6);
        switch_IN_7=(Switch)findViewById(R.id.switch_IN_7);
        switch_IN_8=(Switch)findViewById(R.id.switch_IN_8);

        btn_Enviar=(Button)findViewById(R.id.btn_Enviar);
        btn_Config=(Button)findViewById(R.id.btn_config);

        editText_IP=(EditText)findViewById(R.id.editText_IP);
        editText_port=(EditText)findViewById(R.id.editText_Port);

        text_K1=(TextView)findViewById(R.id.text_K1);
        text_K2=(TextView)findViewById(R.id.text_K2);
        text_K3=(TextView)findViewById(R.id.text_K3);
        text_H3=(TextView)findViewById(R.id.text_H3);
        text_H2=(TextView)findViewById(R.id.text_H2);
        text_H1=(TextView)findViewById(R.id.text_H1);
        text_P1=(TextView)findViewById(R.id.text_P1);
        text_P2=(TextView)findViewById(R.id.text_P2);
        text_P3=(TextView)findViewById(R.id.text_P3);
        text_T1=(TextView)findViewById(R.id.text_T1);
        text_T2=(TextView)findViewById(R.id.text_T2);
        text_T3=(TextView)findViewById(R.id.text_T3);
        text_Inputs=(TextView)findViewById(R.id.text_Inputs);
        text_Outputs=(TextView)findViewById(R.id.text_Outputs);
        checkBox_Conf=(CheckBox)findViewById(R.id.checkBox_conf);
    }

    @Override
    protected void onStart() {
        super.onStart();

        client =new ClientAsyncTask();
        client.execute(new String[]{IP,port,"000"});

    }

    void Botones(){

        checkBox_Conf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                HabilitarSw(b);
                if(!b){
                    AlmacenarPreferencias();
                    Toast.makeText(getApplicationContext(),"Control Manual BLOQUEADO.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Control Manual HABILITADO.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client=new ClientAsyncTask();
                client.execute(new String[]{IP,port,"000"});
            }
        });

        btn_Config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento=new Intent(MainActivity.this,Activity_Configuracion.class);
                Bundle b = new Bundle();
                b.putString("IP", editText_IP.getText().toString());
                b.putString("PORT", editText_port.getText().toString());

                //Añadimos la información al intent
                intento.putExtras(b);
                //Iniciamos la nueva actividad
                startActivity(intento);

            }
        });

        switch_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
             //   ClientAsyncTask client;
                 client=new ClientAsyncTask();
                if(isChecked){client.execute(new String[]{IP,port,"401"});
                }else{
                    client.execute(new String[]{IP,port,"400"});
                }
            }
        });

        switch_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               // ClientAsyncTask client;
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
                //ClientAsyncTask client;
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
                //ClientAsyncTask client;
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
     //               ClientAsyncTask client;
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
       //         ClientAsyncTask client;
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

           // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            if((s == null) || (s.equals(""))){
                System.out.print("DATO NULL");
                d(TAG, "null");

            } else {
                Toast.makeText(getApplicationContext(),"Lectura Actualizada",Toast.LENGTH_SHORT).show();
                String delimitadores = " ";
                String[] dato = s.split(delimitadores);
                int longitud = dato.length;
                d(TAG, "Longitud: "+longitud);
                int posP1=-1,posP2=-1,posP3=-1,posT1=-1,posT2=-1,posT3=-1,posH1=-1,posH2=-1,posH3=-1,posK1=-1,posK2=-1,posK3=-1,posDI=-1,posDO=-1;
                String P1="0",P2="0", P3="0", H1="0",H2="0",H3="0" ,T1="0", T2="0",T3="0", K1="0",K2="0",K3="0", DI="00000000000", DO="00000000";
                String In1,In2, In3, In4,In5,In6 ,In7, In8;
                String unit;
                String scale;
                int cantidad;
              if(longitud==1){
                  // Toast.makeText(getApplicationContext(),dato[0],Toast.LENGTH_SHORT).show();
               }
                if(longitud==9){
                    Toast.makeText(getApplicationContext(),"Configuración:"+dato[0],Toast.LENGTH_SHORT).show();
                }
                if(longitud>9) {


                    for(int i=0;i<longitud;i++){

                        switch (dato[i]){

                            case "P1": posP1=i;  Log.d(TAG,"P1: "+posP1);break;
                            case "P2": posP2=i;  Log.d(TAG,"P2: "+posP2);break;
                            case "P3": posP3=i;  Log.d(TAG,"P3: "+posP3);break;

                            case "T1": posT1=i;  Log.d(TAG,"T1: "+posT1);break;
                            case "T2": posT2=i;  Log.d(TAG,"T2: "+posT2);break;
                            case "T3": posT3=i;  Log.d(TAG,"T3: "+posT3);break;

                            case "H1": posH1=i; Log.d(TAG,"H1: "+posH1);break;
                            case "H2": posH2=i; Log.d(TAG,"H2: "+posH2);break;
                            case "H3": posH3=i; Log.d(TAG,"H3: "+posH3);break;

                            case "K1": posK1=i; Log.d(TAG,"K1: "+posK1);break;
                            case "K2": posK2=i; Log.d(TAG,"K2: "+posK2);break;
                            case "K3": posK3=i; Log.d(TAG,"K3: "+posK3);break;

                            case "DI": posDI=i; Log.d(TAG,"DI: "+posDI);break;
                            case "DO": posDO=i; Log.d(TAG,"DO: "+posDO);break;
                            default:break;

                        }



                    }



                    if(posP1!=-1){P1 = dato[posP1+1];
                    }else{P1="0";}

                    if(posP2!=-1){P2 = dato[posP2+1];
                    }else{P2="0";}

                    if(posP3!=-1){P3 = dato[posP3+1];
                    }else{P3="0";}

                    if(posT1!=-1){T1 = dato[posT1+1];
                    }else{T1="0";}

                    if(posT2!=-1){T2 = dato[posT2+1];
                    }else{T2="0";}

                    if(posT3!=-1){T3 = dato[posT3+1];
                    }else{T3="0";}

                    if(posH1!=-1){H1 = dato[posH1+1];
                    }else{H1="0";}

                    if(posH2!=-1){H2 = dato[posH2+1];
                    }else{H2="0";}

                    if(posH3!=-1){H3 = dato[posH3+1];
                    }else{H3="0";}

                    if(posK1!=-1){K1 = dato[posK1+1];
                    }else{K1="0";}

                    if(posK2!=-1){K2 = dato[posK2+1];
                    }else{K2="0";}

                    if(posK3!=-1){P1 = dato[posK3+1];
                    }else{K3="0";}

                    if(posDI!=-1){DO = dato[posDI+1];
                    }else{DI="0";}
                    if(posDO!=-1){DO = dato[posDO+1];


                    }else{   DI = dato[12];
                             DO = dato[13];}



                    text_P1.setText("P1: "+P1+" Bar");
                    text_P2.setText("P2: "+P2+" Bar");
                    text_P3.setText("P3: "+P3+" Bar");

                    text_H1.setText("H1: "+H1+" %");
                    text_H2.setText("H2: "+H2+" %");
                    text_H3.setText("H3: "+H3+" %");

                    text_T1.setText("T1: "+T1+" °C");
                    text_T2.setText("T2: "+T2+" °C");
                    text_T3.setText("T3: "+T3+" °C");

                    text_K1.setText("K1: "+K1+" Kg");
                    text_K2.setText("K2: "+K2+" Kg");
                    text_K3.setText("K3: "+K3+" Kg");

                    text_Inputs.setText(DI);
                    text_Outputs.setText(DO);

                    ///////////////////////// entradas

                    if(DI.substring(0,1).equals("1")){
                        switch_IN_1.setChecked(true);
                    }else{switch_IN_1.setChecked(false);}

                    if(DI.substring(1,2).equals("1")){
                        switch_IN_2.setChecked(true);
                    }else{switch_IN_2.setChecked(false);}

                    if(DI.substring(2,3).equals("1")){
                        switch_IN_3.setChecked(true);
                    }else{switch_IN_3.setChecked(false);}

                    if(DI.substring(3,4).equals("1")){
                        switch_IN_4.setChecked(true);
                    }else{switch_IN_4.setChecked(false);}

                    if(DI.substring(4,5).equals("1")){
                        switch_IN_5.setChecked(true);
                    }else{switch_IN_5.setChecked(false);}

                    if(DI.substring(5,6).equals("1")){
                        switch_IN_6.setChecked(true);
                    }else{switch_IN_6.setChecked(false);}

                    if(DI.substring(6,7).equals("1")){
                        switch_IN_7.setChecked(true);
                    }else{switch_IN_7.setChecked(false);}

                    if(DI.substring(7,8).equals("1")){
                        switch_IN_8.setChecked(true);
                    }else{switch_IN_8.setChecked(false);}


                    ///////////////////// fin entradas


                    ///////////////// salidas

                    if(DO.substring(0,1).equals("1")){
                        switch_1.setChecked(true);
                    }else{switch_1.setChecked(false);}

                    if(DO.substring(1,2).equals("1")){
                        switch_2.setChecked(true);
                    }else{switch_2.setChecked(false);}

                    if(DO.substring(2,3).equals("1")){
                        switch_3.setChecked(true);
                    }else{switch_3.setChecked(false);}

                    if(DO.substring(3,4).equals("1")){
                        switch_4.setChecked(true);
                    }else{switch_4.setChecked(false);}

                    if(DO.substring(4,5).equals("1")){
                        switch_5.setChecked(true);
                    }else{switch_5.setChecked(false);}

                    if(DO.substring(5,6).equals("1")){
                        switch_6.setChecked(true);
                    }else{switch_6.setChecked(false);}

                    if(DO.substring(6,7).equals("1")){
                        switch_7.setChecked(true);
                    }else{switch_7.setChecked(false);}

                    if(DO.substring(7,8).equals("1")){
                        switch_8.setChecked(true);
                    }else{switch_8.setChecked(false);}

                    if(DO.substring(8,9).equals("1")){
                        switch_9.setChecked(true);
                    }else{switch_9.setChecked(false);}

                    if(DO.substring(9,10).equals("1")){
                        switch_10.setChecked(true);
                    }else{switch_10.setChecked(false);}

                    if(DO.substring(10,11).equals("1")){
                        switch_11.setChecked(true);
                    }else{switch_11.setChecked(false);}

                    ///////// fin salidas
                }

            }
        }
    }

    private void AlmacenarPreferencias(){

        d("Preferencias","Guardar");

        IP=editText_IP.getText().toString();
        port=editText_port.getText().toString();
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("IP", IP);
        editor.putString("port", port);
        editor.commit();

    }

    private void LevantarPreferencias(){

        editText_IP.setText(preferencias.getString("IP", "localhost"));
        editText_port.setText(preferencias.getString("port", "9000"));
        IP=preferencias.getString("IP", "localhost");
        port=preferencias.getString("port", "9000");
       }

    void HabilitarSw(boolean valor){

        switch_1.setClickable(valor);
        switch_2.setClickable(valor);
        switch_3.setClickable(valor);
        switch_4.setClickable(valor);
        switch_5.setClickable(valor);
        switch_6.setClickable(valor);
        switch_7.setClickable(valor);
        switch_8.setClickable(valor);
        switch_9.setClickable(valor);
        switch_10.setClickable(valor);
        switch_11.setClickable(valor);

        editText_IP.setEnabled(valor);
        editText_port.setEnabled(valor);
        if(!valor) {
            client = new ClientAsyncTask();
            client.execute(new String[]{IP, port, "000"});
        }

    }


}

