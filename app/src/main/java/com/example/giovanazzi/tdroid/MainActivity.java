package com.example.giovanazzi.tdroid;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.util.Log.d;
import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {

    public static final String CmdSolicitudPass="777";

    Switch switch_1,switch_2,switch_3,switch_4,switch_5,switch_6,switch_7,switch_8,switch_9,switch_10,switch_11;
    Switch switch_IN_1,switch_IN_2,switch_IN_3,switch_IN_4,switch_IN_5,switch_IN_6,switch_IN_7,switch_IN_8;

    Button btn_Enviar,btn_Config;

    TextView text_H3,text_H2,text_H1,text_P3,text_P2,text_P1,text_T3,text_K2,text_K3,text_K1,text_T2,text_T1;

    TextView text_IN_1,text_IN_2,text_IN_3,text_IN_4,text_IN_5,text_IN_6,text_IN_7,text_IN_8;
    TextView text_Out_1 ,text_Out_2 ,text_Out_3 ,text_Out_4 ,text_Out_5 ,text_Out_6 ,text_Out_7 ,text_Out_8 ,text_Out_9 ,text_Out_10 ,text_Out_11 ;

    String IP,port,password;
    String switch_1_pref,switch_2_pref,switch_3_pref,switch_4_pref,switch_5_pref,switch_6_pref,switch_7_pref,switch_8_pref,switch_9_pref,switch_10_pref,switch_11_pref;
    String switch_In1_pref,switch_In2_pref,switch_In3_pref,switch_In4_pref,switch_In5_pref,switch_In6_pref,switch_In7_pref,switch_In8_pref;

    CheckBox checkBox_Conf,checkBox_Auto;

    String TAG="TrackDroid";
    public SharedPreferences preferencias;
    ClientAsyncTask client;
    Dialog customDialog,dialogoEditTexto;
    Boolean Auto=true;;
    MiThread hilo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferencias=getSharedPreferences("MisPref", Context.MODE_PRIVATE);
        Levantar_XML();
        HabilitarSw(false);
        Acciones();

    }

    @Override
    protected void onStart() {
        super.onStart();

        LevantarPreferencias();
        client =new ClientAsyncTask();
        client.execute(IP,port,"000");

    }

    @Override
    protected void onStop(){
        super.onStop();

        AlmacenarPreferencias();
        checkBox_Conf.setChecked(false);
        checkBox_Auto.setChecked(false);
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

        text_IN_1=(TextView)findViewById(R.id.text_IN_1);
        text_IN_2=(TextView)findViewById(R.id.text_IN_2);
        text_IN_3=(TextView)findViewById(R.id.text_IN_3);
        text_IN_4=(TextView)findViewById(R.id.text_IN_4);
        text_IN_5=(TextView)findViewById(R.id.text_IN_5);
        text_IN_6=(TextView)findViewById(R.id.text_IN_6);
        text_IN_7=(TextView)findViewById(R.id.text_IN_7);
        text_IN_8=(TextView)findViewById(R.id.text_IN_8);

        text_Out_1=(TextView)findViewById(R.id.text_Out_1);
        text_Out_2=(TextView)findViewById(R.id.text_Out_2);
        text_Out_3=(TextView)findViewById(R.id.text_Out_3);
        text_Out_4=(TextView)findViewById(R.id.text_Out_4);
        text_Out_5=(TextView)findViewById(R.id.text_Out_5);
        text_Out_6=(TextView)findViewById(R.id.text_Out_6);
        text_Out_7=(TextView)findViewById(R.id.text_Out_7);
        text_Out_8=(TextView)findViewById(R.id.text_Out_8);
        text_Out_9=(TextView)findViewById(R.id.text_Out_9);
        text_Out_10=(TextView)findViewById(R.id.text_Out_10);
        text_Out_11=(TextView)findViewById(R.id.text_Out_11);

        checkBox_Conf=(CheckBox)findViewById(R.id.checkBox_conf);
        checkBox_Auto=(CheckBox)findViewById(R.id.checkBox_Auto);
    }

    void Acciones(){

        AccionesTextView();
        AccionesSwitch();

        checkBox_Auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(getApplicationContext(),"Actualización automática",Toast.LENGTH_SHORT).show();
                    Auto=true;
                     hilo = new MiThread();
                     hilo.start();
                }else{
                    Auto=false;

                }
            }
        });

        checkBox_Conf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    checkBox_Auto.setChecked(true);
                    HabilitarSw(false);
                }else{
                    checkBox_Auto.setChecked(false);
                    DialogoPedirPassword();
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

                startActivity(intento);

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
            //  Toast.makeText(getApplicationContext(),"Comando no recibido",Toast.LENGTH_SHORT).show();
                d(TAG, "DATO VACIO");

            } else {
                Toast toast=Toast.makeText(getApplicationContext(),"Actualizado",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

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
                    }else{P1="-";}

                    if(posP2!=-1){P2 = dato[posP2+1];
                    }else{P2="-";}

                    if(posP3!=-1){P3 = dato[posP3+1];
                    }else{P3="-";}

                    if(posT1!=-1){T1 = dato[posT1+1];
                    }else{T1="-";}

                    if(posT2!=-1){T2 = dato[posT2+1];
                    }else{T2="-";}

                    if(posT3!=-1){T3 = dato[posT3+1];
                    }else{T3="-";}

                    if(posH1!=-1){H1 = dato[posH1+1];
                    }else{H1="-";}

                    if(posH2!=-1){H2 = dato[posH2+1];
                    }else{H2="-";}

                    if(posH3!=-1){H3 = dato[posH3+1];
                    }else{H3="-";}

                    if(posK1!=-1){K1 = dato[posK1+1];
                    }else{K1="-";}

                    if(posK2!=-1){K2 = dato[posK2+1];
                    }else{K2="-";}

                    if(posK3!=-1){K3 = dato[posK3+1];
                    }else{K3="-";}

                    if(posDI!=-1){DI = dato[posDI+1];
                    }
                    if(posDO!=-1){DO = dato[posDO+1];


                    }

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

    private void LevantarPreferencias(){

        IP=preferencias.getString("IP", "localhost");
        if(IP.toString().equals("localhost")){Toast.makeText(getApplicationContext(),"Falta configurar el Servidor",Toast.LENGTH_SHORT).show();}
        port=preferencias.getString("port", "9000");
        password=preferencias.getString("password", "1234");

        switch_1_pref = preferencias.getString("switch_1", "switch 1");
        switch_2_pref = preferencias.getString("switch_2", "switch 2");
        switch_3_pref = preferencias.getString("switch_3", "switch 3");
        switch_4_pref = preferencias.getString("switch_4", "switch 4");
        switch_5_pref = preferencias.getString("switch_5", "switch 5");
        switch_6_pref = preferencias.getString("switch_6", "switch 6");
        switch_7_pref = preferencias.getString("switch_7", "switch 7");
        switch_8_pref = preferencias.getString("switch_8", "switch 8");
        switch_9_pref = preferencias.getString("switch_9", "switch 9");
        switch_10_pref= preferencias.getString("switch_10", "switch 10");
        switch_11_pref= preferencias.getString("switch_11", "switch 11");

        switch_In1_pref=preferencias.getString("switch_IN_1", "switchIn 1");
        switch_In2_pref=preferencias.getString("switch_IN_2", "switchIn 2");
        switch_In3_pref=preferencias.getString("switch_IN_3", "switchIn 3");
        switch_In4_pref=preferencias.getString("switch_IN_4", "switchIn 4");
        switch_In5_pref=preferencias.getString("switch_IN_5", "switchIn 5");
        switch_In6_pref=preferencias.getString("switch_IN_6", "switchIn 6");
        switch_In7_pref=preferencias.getString("switch_IN_7", "switchIn 7");
        switch_In8_pref=preferencias.getString("switch_IN_8", "switchIn 8");

        SetearTextoEtiquetas();

       }

    private void AlmacenarPreferencias(){

        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("switch_1", text_Out_1.getText().toString());
        editor.putString("switch_2", text_Out_2.getText().toString());
        editor.putString("switch_3", text_Out_3.getText().toString());
        editor.putString("switch_4", text_Out_4.getText().toString());
        editor.putString("switch_5", text_Out_5.getText().toString());
        editor.putString("switch_6", text_Out_6.getText().toString());
        editor.putString("switch_7", text_Out_7.getText().toString());
        editor.putString("switch_8", text_Out_8.getText().toString());
        editor.putString("switch_9", text_Out_9.getText().toString());
        editor.putString("switch_10", text_Out_10.getText().toString());
        editor.putString("switch_11", text_Out_11.getText().toString());

        editor.putString("switch_IN_1", text_IN_1.getText().toString());
        editor.putString("switch_IN_2", text_IN_2.getText().toString());
        editor.putString("switch_IN_3", text_IN_3.getText().toString());
        editor.putString("switch_IN_4", text_IN_4.getText().toString());
        editor.putString("switch_IN_5", text_IN_5.getText().toString());
        editor.putString("switch_IN_6", text_IN_6.getText().toString());
        editor.putString("switch_IN_7", text_IN_7.getText().toString());
        editor.putString("switch_IN_8", text_IN_8.getText().toString());

        editor.commit();

    }

    public void DialogoPedirPassword()  {
        // con este tema personalizado evitamos los bordes por defecto
        customDialog = new Dialog(this,R.style.Theme_AppCompat_Dialog);
        //deshabilitamos el título por defecto
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        customDialog.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        customDialog.setContentView(R.layout.activity_password);
        final EditText editPass=(EditText)customDialog.findViewById(R.id.EditText_Pwd);
        Button botonAcep=(Button) customDialog.findViewById(R.id.btn_acep_pwd);
        Button botonCancel=(Button) customDialog.findViewById(R.id.btn_can_pwd);

        botonAcep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                if(editPass.getText().toString().equals(password)){


                    HabilitarSw(true);
                    Toast.makeText(getApplicationContext(), "Control Manual Habilitado", Toast.LENGTH_SHORT).show();

                }else {
                     HabilitarSw(false);
                    checkBox_Conf.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Error de Contraseña", Toast.LENGTH_SHORT).show();
                }


            }
        });

        botonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();

                HabilitarSw(false);
                checkBox_Conf.setChecked(false);
            }
        });

        customDialog.show();
    }

    public void DialogoCambiarTexto(final TextView tx)  {
        // con este tema personalizado evitamos los bordes por defecto
        dialogoEditTexto = new Dialog(this,R.style.Theme_AppCompat_Dialog_Alert);
        //deshabilitamos el título por defecto
        dialogoEditTexto.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //obligamos al usuario a pulsar los botones para cerrarlo
        dialogoEditTexto.setCancelable(false);
        //establecemos el contenido de nuestro dialog
        dialogoEditTexto.setContentView(R.layout.activity_editartexto);

        final EditText edittexto=(EditText)dialogoEditTexto.findViewById(R.id.edit_Nombre);
        Button btn_acep_edit=(Button) dialogoEditTexto.findViewById(R.id.btn_acep_edit);

        edittexto.setText(tx.getText().toString());

        btn_acep_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edittexto.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Ingrese Etiqueta",Toast.LENGTH_SHORT).show();
                }else {
                    tx.setText(edittexto.getText().toString());
                    dialogoEditTexto.dismiss();
                }
              }
        });

        dialogoEditTexto.show();
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

        return super.onOptionsItemSelected(item);
    }

    class MiThread extends Thread {
        @Override
        public void run() {

                while (Auto) {
                    try {


                    sleep(5000);
                    d(TAG, "hilo puto");
                    client = new ClientAsyncTask();
                    client.execute(IP, port, "000");

                } catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    void SetearTextoEtiquetas(){

        text_Out_1.setText(switch_1_pref);
        text_Out_2.setText(switch_2_pref);
        text_Out_3.setText(switch_3_pref);
        text_Out_4.setText(switch_4_pref);
        text_Out_5.setText(switch_5_pref);
        text_Out_6.setText(switch_6_pref);
        text_Out_7.setText(switch_7_pref);
        text_Out_8.setText(switch_8_pref);
        text_Out_9.setText(switch_9_pref);
        text_Out_10.setText(switch_10_pref);
        text_Out_11.setText(switch_11_pref);

        text_IN_1.setText(switch_In1_pref);
        text_IN_2.setText(switch_In2_pref);
        text_IN_3.setText(switch_In3_pref);
        text_IN_4.setText(switch_In4_pref);
        text_IN_5.setText(switch_In5_pref);
        text_IN_6.setText(switch_In6_pref);
        text_IN_7.setText(switch_In7_pref);
        text_IN_8.setText(switch_In8_pref);

    }

    void AccionesTextView(){
        text_IN_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_1);
                return false;
            }
        });

        text_IN_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_2);
              return false;
            }
        });

        text_IN_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_3);
                return false;
            }
        });

        text_IN_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_4);
                return false;
            }
        });


        text_IN_5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_5);
                return false;
            }
        });


        text_IN_6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_6);
                return false;
            }
        });

        text_IN_7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_7);

                return false;
            }
        });

        text_IN_8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_IN_8);
                return false;
            }
        });


        text_Out_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_1);
                return false;
            }
        });

        text_Out_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_2);
                return false;
            }
        });

        text_Out_3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_3);
                return false;
            }
        });

        text_Out_4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_4);
                return false;
            }
        });

        text_Out_5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_5);
                return false;
            }
        });

        text_Out_6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_6);
                return false;
            }
        });

        text_Out_7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_7);
                return false;
            }
        });

        text_Out_8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_8);
                return false;
            }
        });

        text_Out_9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_9);
                return false;
            }
        });

        text_Out_10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_10);
                return false;
            }
        });

        text_Out_11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogoCambiarTexto(text_Out_11);
                return false;
            }
        });
    }

    void AccionesSwitch(){

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
                client=new ClientAsyncTask();
                if(isChecked){

                    client.execute(new String[]{IP,port,"901"});
                }else{
                    client.execute(new String[]{IP,port,"900"});
                }
            }
        });


    }

}

