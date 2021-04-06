package com.innova.ticketsapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.SQLException;
import android.device.PrinterManager;
import android.device.ScanManager;
import android.device.scanner.configuration.PropertyID;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.innova.ticketsapp.fragmentos.MenuFrameLayout;
import com.mit.api.pr_api_base.model.Ticket;
import com.innova.ticketsapp.utils.TxTicket;
import com.innova.ticketsapp.utils.TxCorte;
import com.innova.ticketsapp.utils.TxInventario;
import com.mit.api.pr_api_base.model.PrConfiguration;
import com.mit.api.pr_api_base.model.PrRequest;
import com.mit.api.pr_api_base.model.PrResponse;
import com.mit.api.pr_api_base.model.PrAction;
import model.Callback;
import model.Error;
import com.innova.ticketsapp.actividades.CaptureActivityPortrait;
import com.innova.ticketsapp.printer.PrintBillService;

import com.mit.api.pr_api_base.service.PrintCoreServiceImpl;



import mit.com.mpos.model.intelipos.business.print.MitVaucher;
import mit.com.mpos.model.intelipos.business.print.MitVaucherLine;
import com.example.mitprinterlibrary.MitTools;
import com.example.mitprinterlibrary.wpos.MitPrinterWpos;
import com.example.mitprinterlibrary.wpos.MitPrinterWposJava;
import com.example.mitprinterlibrary.wpoy.MitPrinterWpoy;
import mit.com.mpos.model.enums.MitEnum;
import com.innova.ticketsapp.actividades.ApiApplication;
import com.innova.ticketsapp.utils.Vaucher;








import com.innova.ticketsapp.utils.Aceites;



import com.innova.ticketsapp.utils.ClienteFormaPago;
import com.pos.api.Lcm;
import com.pos.api.Printer;
import com.pos.api.Scan;
import com.innova.ticketsapp.utils.AceitesBD;
import com.innova.ticketsapp.utils.Historial;
import com.innova.ticketsapp.model.RestService;
import com.innova.ticketsapp.printer.FontStylePanel;




import com.innova.ticketsapp.utils.NetCliente;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.innova.ticketsapp.adaptador.RecyclerViewAdapter;
import com.innova.ticketsapp.adaptador.SwipeToDeleteCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<PrResponse> {
    /** Called when the activity is first created. */
    private Scan scan;
    private TextView textRecord;
    private Button btnScanRead;
    private Button btnScanTrigger;
    private Button btnSet;
    private String TAG = "ScanTest";
    private String codigo = "",cantidad="";
    AceitesBD aceites;
    Aceites aceite;
    EditText editScan;
    Vector codigos;
    Vector AceitesIm;
    ArrayList<Aceites> TotalAceites;
    private Double ImporteTotal=0.0;
    private DecimalFormat decimales;
    private String ipDB = "";
    private String estacion="";
    private String direccion="";
    private String rfc="";
    private String siic="";
    private String isla="";
    private Integer inD=-1;
    private Integer finturno;
    private Integer transaccion;
    private String fecha,hora;
    private boolean conectado=false;
    private NetCliente webService;
    private String result;
    private JSONObject ticket;
    private JSONObject formapago;
    private Integer indice;
    private String error;
    private String out;
    private String IP;
    private int codigoHTTP;
    private String respuestaJSON;
    private Context ctx;
    private ScanManager mScanManager;
    int[] idbuf = new int[]{PropertyID.WEDGE_INTENT_ACTION_NAME, PropertyID.WEDGE_INTENT_DATA_STRING_TAG};
    int[] idmodebuf = new int[]{PropertyID.WEDGE_KEYBOARD_ENABLE, PropertyID.TRIGGERING_MODES, PropertyID.LABEL_APPEND_ENTER};
    String[] action_value_buf = new String[]{ScanManager.ACTION_DECODE, ScanManager.BARCODE_STRING_TAG};
    int[] idmode;
    public final static String PRNT_ACTION = "action.printer.message";
    PrinterManager printer = new PrinterManager();
    private FontStylePanel mFontStylePanel;


    private final int DEF_TEMP_THROSHOLD = 90;
    private int mTempThresholdValue = DEF_TEMP_THROSHOLD;

    private int mVoltTempPair[][] = {
            {898, 80},
            {1008, 75},
            {1130, 70},
            {1263, 65},
            {1405, 60},
            {1555, 55},
            {1713, 50},
            {1871, 45},
            {2026, 40},
            {2185, 35},
            {2335, 30},
            {2475, 25},
            {2605, 20},
            {2722, 15},
            {2825, 10},
            {2915, 5},
            {2991, 0},
            {3054, -5},
            {3107, -10},
            {3149, -15},
            {3182, -20},
            {3209, -25},
            {3231, -30},
            {3247, -35},
            {3261, -40},
    };

    private static final String[] mTempThresholdTable = {
            "80", "75", "70", "65", "60",
            "55", "50", "45", "40", "35",
            "30", "25", "20", "15", "10",
            "5", "0", "-5", "-10", "-15",
            "-20", "-25", "-30", "-35", "-40",
    };

    private static final String[] mBarTypeTable = {
            "3", "20", "25",
            "29", "34", "55", "58",
            "71", "84", "92",
    };

    private final static int DEF_PRINTER_HUE_VALUE = 0;
    private final static int MIN_PRINTER_HUE_VALUE = 0;
    private final static int MAX_PRINTER_HUE_VALUE = 4;

    private final static int DEF_PRINTER_SPEED_VALUE = 9;
    private final static int MIN_PRINTER_SPEED_VALUE = 0;
    private final static int MAX_PRINTER_SPEED_VALUE = 9;

    // Printer Status
    private final static int PRNSTS_OK = 0;                // OK
    private final static int PRNSTS_OUT_OF_PAPER = -1;    // Out of paper
    private final static int PRNSTS_OVER_HEAT = -2;        // Over heat
    private final static int PRNSTS_UNDER_VOLTAGE = -3;    // under voltage
    private final static int PRNSTS_BUSY = -4;            // Device is busy
    private final static int PRNSTS_ERR = -256;            // Common error
    private final static int PRNSTS_ERR_DRIVER = -257;    // Printer Driver error



    private static final int RECORD_PROMPT_MSG = 0x06;
    private Timer mScanCodeTimer;
    private TimerTask mScanCodeTask;
    private static int CHECK_CONNECT_STATUS_TIMEOUT = 4000;//5s
    private boolean timeout = false;
    Connection conexion;
    private AppCompatSpinner mop;
    private Printer impresora;
    private Lcm lcm;
    private AppCompatSpinner pos;
    private AppCompatSpinner tipoV;
    private Historial accesoDB;
    ClienteFormaPago fpago;
    private RestService restServiceG;
    private RestService restServiceP;
    private RestService restServiceM;
    private RestService restServiceI;
    int opcion;
    LinearLayout llProgressBar;

    RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    //CoordinatorLayout coordinatorLayout;
    DrawerLayout coordinatorLayout;

    //menu
    private Toolbar toolbar;
    private MenuFrameLayout sifl;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView ndList;
    //

    private Handler promptHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECORD_PROMPT_MSG:
                    showTextRecordMsg(msg);
                    break;
                default:
                    break;
            }

        }
    };

    private void showTextRecordMsg( Message msg)
    {
        Bundle b = msg.getData();
        String strInfo = b.getString("MSG");
        if(strInfo.equals("")) textRecord.setText("");
        else textRecord.append(strInfo);

    }

    public void SendPromptMsg(String strInfo){
        Message msg = new Message();
        msg.what = RECORD_PROMPT_MSG;
        Bundle b = new Bundle();
        b.putString("MSG", strInfo);
        msg.setData(b);
        promptHandler.sendMessage(msg);
    }

    public void SendCodigo(String strInfo){
        editScan.setText(strInfo);
    }

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            byte[] barcode = intent.getByteArrayExtra(ScanManager.DECODE_DATA_TAG);
            int barcodelen = intent.getIntExtra(ScanManager.BARCODE_LENGTH_TAG, 0);
            byte temp = intent.getByteExtra(ScanManager.BARCODE_TYPE_TAG, (byte) 0);
            String result = intent.getStringExtra(action_value_buf[1]);
            /*if(barcodelen != 0)
                barcodeStr = new String(barcode, 0, barcodelen);
            else
                barcodeStr = intent.getStringExtra("barcode_string");*/
            if(result != null) {
                //Toast.makeText(MainActivity.this, result+" "+action_value_buf [0]+" "+action_value_buf [1], Toast.LENGTH_LONG).show();
                setAceites(result);
                //decode_length.setText("" + result.length());
            }
        }

    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        this.ctx = (Context)this;
        mFontStylePanel = new FontStylePanel(this);

        //menu
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        sifl = (MenuFrameLayout)findViewById(R.id.menuFrameLayout);
        ndList = (ListView)findViewById(R.id.menulist);

        final String[] opciones = new String[]{"Corte Parcial", "Fin de Turno", "Inv Tanques"};

        ArrayAdapter<String> ndMenuAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_activated_1, opciones);

        ndList.setAdapter(ndMenuAdapter);

        ndList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Fragment fragment = null;

                switch (pos) {
                    case 0:
                        //fragment = new Fragment1();
                        break;
                    case 1:
                        //fragment = new Fragment2();
                        break;
                    case 2:
                        //fragment = new Fragment3();
                        break;
                }
                ndList.setItemChecked(pos, true);
                drawerLayout.closeDrawer(sifl);
            }
        });

        //Drawer Layout

        drawerLayout = (DrawerLayout)findViewById(R.id.container3);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //


        AceitesIm=new Vector();
        pos = (AppCompatSpinner) findViewById(R.id.pos);
        mop = (AppCompatSpinner) findViewById(R.id.mop);
        tipoV = (AppCompatSpinner) findViewById(R.id.tipoV);

        mScanManager = new ScanManager();
        mScanManager.openScanner();
        action_value_buf = mScanManager.getParameterString(idbuf);
        idmode = mScanManager.getParameterInts(idmodebuf);
        idmode[0] = 0;
        mScanManager.setParameterInts(idmodebuf, idmode);

        cargarPreferencia();



        btnScanRead = (Button) findViewById(R.id.btnScanRead);
        btnScanRead.setOnClickListener(this);
        btnScanTrigger = (Button) findViewById(R.id.btnScanTrigger);
        btnScanTrigger.setOnClickListener(this);
        //btnSet = (Button) findViewById(R.id.btnSet);
        //btnSet.setOnClickListener(this);

        textRecord=(TextView)this.findViewById(R.id.textRecord);
        textRecord.setMovementMethod(new ScrollingMovementMethod());
        TotalAceites = new ArrayList<>();
        decimales=new DecimalFormat("#######.00");



        restServiceG = new RestService(mHandlerG, this, IP+"/apiterminal/Corte", RestService.POST); //Create new rest service for get
        restServiceG.addHeader("Content-Type","application/json");

        restServiceP = new RestService(mHandlerP, this, IP+"/apiterminal/tiketventa", RestService.POST); //Create new rest service for get
        restServiceP.addHeader("Content-Type","application/json");

        restServiceM = new RestService(mHandlerM, this, IP+"/apiterminal/FormasDePago", RestService.POST); //Create new rest service for get
        restServiceM.addHeader("Content-Type","application/json");

        restServiceI = new RestService(mHandlerI, this, IP+"/apiterminal/InventarioTanques", RestService.POST); //Create new rest service for get
        restServiceI.addHeader("Content-Type","application/json");

        cargarCombo2();
        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.container3);
        populateRecyclerView();
        enableSwipeToDeleteAndUndo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Alternativa 1

        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Alternativa 2

        //menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Opcion1")
        //		.setIcon(android.R.drawable.ic_menu_preferences);
        //menu.add(Menu.NONE, MNU_OPC2, Menu.NONE, "Opcion2")
        //		.setIcon(android.R.drawable.ic_menu_compass);

        //SubMenu smnu = menu.
        //		addSubMenu(Menu.NONE, MNU_OPC1, Menu.NONE, "Opcion3")
        //			.setIcon(android.R.drawable.ic_menu_agenda);
        //smnu.add(Menu.NONE, SMNU_OPC1, Menu.NONE, "Opcion 3.1");
        //smnu.add(Menu.NONE, SMNU_OPC2, Menu.NONE, "Opcion 3.2");

        return true;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        int iRet = -1;

        switch(v.getId())
        {
            case R.id.btnScanRead:
                mScanManager.startDecode();
                //scanQr();
                break;
            case R.id.btnScanTrigger:

                String json;
                json = crearJSON().toString();
                System.out.println("InputStream...." + json);
                restServiceP.setEntity(json);
                restServiceP.execute();
                break;
        }

    }



    public void setAceites(final String parametro) {
        try {


            //codigos=new Vector();
            indice=inLista(parametro);
            codigos=((indice==-1)?new Vector():(Vector)AceitesIm.elementAt(indice));
            setCantidad2(parametro);


            /*
            SendPromptMsg("codigo producto:"+parametro+" \r\n");
            Thread thread = new Thread(){
                public void run(){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            setCantidad(parametro);
                        }
                    });
                }
            };
            thread.start();

             */


        } catch (Exception e) {
            SendPromptMsg("CLASSNOTFOUND");
        }
    }


    private void setCantidad2(final String barras){

        /*
        if(indice==-1){
            codigos.add(barras);
            codigos.add(1);
            AceitesIm.add(codigos);

        }else{
            codigos.set(1,(Integer.valueOf(codigos.get(1).toString())+1));
        }

         */
        //mostrarAceites();
        codigos.add(barras);
        codigos.add(1);
        AceitesIm.add(codigos);
        ((RecyclerViewAdapter)recyclerView.getAdapter()).getData().add(barras);
        recyclerView.getAdapter().notifyDataSetChanged();

    //setResult();
    }



    private void setCantidad(final String barras){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escribe la cantidad");
        final EditText input = new EditText(this);
        //input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(((indice==-1)?"1":String.valueOf(Integer.valueOf(codigos.get(1).toString())+1)));
        input.selectAll();
        input.requestFocus();
        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.setHint("1");
        builder.setView(input);

        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().length()>0){
                    if(indice==-1){
                        codigos.add(barras);
                        codigos.add(input.getText().toString());
                        AceitesIm.add(codigos);
                    }else{
                        codigos.set(1,input.getText().toString());
                    }
                }
                mostrarAceites();
            }
        });
        builder.setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                outLista(barras);
                mostrarAceites();
                dialog.cancel();
            }
        });

        builder.show();


    }


    private Integer inLista(String scan){
        int index=-1;
        if(AceitesIm!=null){
            for( int i=0;i<AceitesIm.size();i++){
                Vector temp=(Vector)AceitesIm.get(i);
                if(temp.get(0).toString().equalsIgnoreCase(scan)){
                    index= i;
                    break;
                }
            }

        }
        return index;

    }
    private Integer outLista(String scan){
        int index=-1;
        if(AceitesIm!=null){
            for( int i=0;i<AceitesIm.size();i++){
                Vector temp=(Vector)AceitesIm.get(i);
                if(temp.get(0).toString().equalsIgnoreCase(scan)){
                    AceitesIm.remove(i);
                    break;
                }
            }

        }
        return index;

    }

    private void eliminarLista(Aceites parA){

        if(!TotalAceites.isEmpty()){
            for( int i=0;i< TotalAceites.size();i++){
                if(TotalAceites.get(i).getCodigoFabrica().equalsIgnoreCase(parA.getCodigoFabrica())){
                    TotalAceites.remove(i);
                }
            }

        }


    }

    private void eliminarLista(){

        if(TotalAceites!=null){
            for( int i=0;i< TotalAceites.size();i++){
                TotalAceites.remove(i);
            }

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.MnuOpc1:
                //setConfiguracion();
                //setConfiguracion2();
                abrirConfiguracion();
                return true;
            case R.id.MnuOpc2:
                opcion=2;
               Corte();
               return true;

            case R.id.MnuOpc3:
                opcion=3;
                Corte();
                return true;

            case R.id.MnuOpc4:
                opcion=4;
                inventarioTanques();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirConfiguracion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escribe la contraseña");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD );
        //input.setInputType(InputType.TYPE_CLASS_NUMBER);
        //input.setText(ipDB);
        //input.selectAll();
        input.requestFocus();
        input.setFocusable(true);
        //input.setFocusableInTouchMode(true);
        builder.setView(input);

        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if(input.getText().toString().equalsIgnoreCase("1nn0v4@3n3rc0n")){
                if(input.getText().toString().equalsIgnoreCase("#1nn0v4@3n3rc0n#E")){
                    setConfiguracion2();
                }else{
                    Toast.makeText(getApplicationContext(), "Acceso denegado", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void setConfiguracion2(){
        createLoginDialogo().show();

    }

    public android.app.AlertDialog createLoginDialogo() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_config, null);
        builder.setView(v);

        final EditText inputIP = (EditText) v.findViewById(R.id.iptxt);
        inputIP.setInputType(InputType.TYPE_CLASS_TEXT );
        inputIP.setText(IP);



        builder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                IP=(inputIP.getText().length()>0)?inputIP.getText().toString().trim():IP;
                setPreferencias();
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    private void cargarPreferencia(){
        SharedPreferences prefs =
                getPreferences(Context.MODE_PRIVATE);
        IP = prefs.getString("IP", "http://192.168.0.117:8080");

    }

    private void setPreferencias(){
        SharedPreferences prefs =
                getPreferences(Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("IP",this.IP);
        editor.commit();

    }


    public void insertar(Historial ticket) {
        if(TotalAceites!=null){
            if(TotalAceites.size()>0){
                try {
                    String valores=";";
                    String sql = "insert into facturas_con " +
                            "(cantidad,precio,importe,producto,fecha,gasolina,hora,codigo_fabrica,mop,transaccion,turno,isla,referencia) values " ;
                    for( int i=0;i< TotalAceites.size();i++){
                        valores=valores+",('"+TotalAceites.get(i).getCantidad()+"','"+TotalAceites.get(i).getPrecioUnitario()+"','"+TotalAceites.get(i).getTotal()+"','"+TotalAceites.get(i).getProducto()+"',date(now()),'0',time(now()),'"+TotalAceites.get(i).getCodigoFabrica()+"','"+mop.getSelectedItem().toString().toUpperCase()+"','"+ticket.getFolio()+"','"+ticket.getTurno()+"','"+ticket.getIsla()+"','"+ticket.getReferencia()+"')";
                    }
                    Class.forName("com.mysql.jdbc.Driver");
                    String cadenaConexion = "jdbc:mysql://"+ipDB+":3306/baseserver?characterEncoding=latin1&useConfigs=maxPerformance";
                    conexion = DriverManager.getConnection(cadenaConexion,"tecnico", "vic1971");

                    Log.i("connexion",sql);
                    PreparedStatement pstm = conexion.prepareStatement(sql+valores.replaceAll(";,",""));
                    pstm.execute();
                    pstm.close();
                    conexion.close();
                    eliminarLista();

                } catch (SQLException | java.sql.SQLException se) {
                    System.err.println(se);
                    SendPromptMsg("SQLEXCEPTION"+se.getMessage());
                } catch (ClassNotFoundException e) {
                    SendPromptMsg("CLASSNOTFOUND");
                }
            }
        }

    }

    private void displayMonto(String monto){
        byte [] amount = new byte [20];
        amount =decimales.format(Double.valueOf(monto)).replaceAll("\\.","").getBytes();
        Log.i("long",String.valueOf(monto.length()));
        Log.i("valor",decimales.format(Double.valueOf(monto)).replaceAll("\\.",""));
        int longitud=decimales.format(Double.valueOf(monto)).replaceAll("\\.","").length();


        if(inD!= 0) {SendPromptMsg("Falló al inicar display"+Integer.toString(inD)+"\r\n");
        }else{
            lcm.DLL_CustomerDisplayClr();
            lcm.DLL_CustomerDisplayAmount((byte)longitud,amount, (byte)2);
        }
    }


    public void envia(String a) {

        try {
            URL url;

            url = new URL("http://192.168.0.117:8080/ServiceFuelsoft-1.0/ultimaVenta?posicion=" + a);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //String authString = "usuario:pruebas2011";
            //System.out.println(":::::::::::::::::::::::::::::::::::"+autorizacion);
			/*String authString ="pruebas:pruebas2011";
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			conn.setDoOutput(true);*/
            //conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/plain");
            //conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "text/plain");

            String input = "{\"posicion\":1}";

            //String input="{\"PaymentAccountNumber\": \"5143\",\"Currency\": \"MXN\",\"Folio\": \"1000\",\"CfdiType\": \"i\",\"PaymentMethod\": \"01\",\"Issuer\": {\"TaxAddress\": {\"Street\": \"Canada de Gomez\",\"ExteriorNumber\": \"115\",\"Neighborhood\": \"Lomas 4ta\",\"ZipCode\": \"12345\",\"Municipality\": \"SAN LUIS POTOSI\",\"State\": \"SAN LUIS POTOSI\",\"Country\": \"MEXICO\"},\"FiscalRegime\": \"Regimen de Incorporacion Fiscal\",\"Rfc\": \"AAA010101AAA\",\"Name\": \"Expresion en Software SAPI de CV\"},\"Receiver\": {\"Rfc\": \"XAXX010101000\",\"Name\": \"Fulano\"},\"Items\": [{\"Description\": \"110 Folios\",\"Unit\": \"SERVICIO\",\"UnitPrice\": 1.0,\"Quantity\": 2.0,\"Subtotal\": 100.0,\"Discount\": 10.0,\"Taxes\": [{\"Total\": 16.0,\"Name\": \"IVA\",\"Rate\": 16.0}],\"Total\": 116.0}]}";
            /*
            org.json.JSONObject obj = new org.json.JSONObject();
            try {
                obj.put("id", "3");
                obj.put("name", "NAME OF STUDENT");
                obj.put("year", "3rd");
                obj.put("curriculum", "Arts");
                obj.put("birthday", "5/5/1993");
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */


/*
			OutputStream os = conn.getOutputStream();
			java.nio.charset.Charset.forName("UTF-8").encode(input);
			//os.write(input.getBytes("UTF-8"));
			os.write(obj.toString().getBytes("UTF-8"));
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				error=conn.getResponseCode()+" --- "+getStringFromInputStream(conn.getErrorStream());
				Log.e("ERROR",conn.getResponseCode()+" --- "+error);
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode()+" --- "+error);


			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			Log.i("info","Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				out=output;
				Log.i("info",output);
			}
			*/
            if (conn.getResponseCode() != 200) {
                conectado = false;
                result = "Fallo : HTTP error code : "
                        + conn.getResponseCode();

            } else {
                conectado = true;
            }
            InputStream stream = conn.getInputStream();
            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            ticket = new JSONObject(result);
            Log.i("info", "Output from Server .... \n" + result);



            conn.disconnect();

        } catch (MalformedURLException e) {
            conectado = false;
            e.printStackTrace();

        } catch (IOException e) {
            conectado = false;
            e.printStackTrace();

        } catch (org.json.JSONException e) {
            conectado = false;
            e.printStackTrace();

        }
    }

    public void doPost() {

        try {
            URL url;

            //url = new URL("http://192.168.0.121:8080/apiterminal/tiketventa");
            url = new URL("http://192.168.0.117:8080/apiterminal/tiketventa");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //String authString = "usuario:pruebas2011";
            //System.out.println(":::::::::::::::::::::::::::::::::::"+autorizacion);
			/*String authString ="pruebas:pruebas2011";
			String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);*/
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Content-Type", "text/plain");

            //String input = "{\"posicion\":1}";

            //String input="{\"PaymentAccountNumber\": \"5143\",\"Currency\": \"MXN\",\"Folio\": \"1000\",\"CfdiType\": \"i\",\"PaymentMethod\": \"01\",\"Issuer\": {\"TaxAddress\": {\"Street\": \"Canada de Gomez\",\"ExteriorNumber\": \"115\",\"Neighborhood\": \"Lomas 4ta\",\"ZipCode\": \"12345\",\"Municipality\": \"SAN LUIS POTOSI\",\"State\": \"SAN LUIS POTOSI\",\"Country\": \"MEXICO\"},\"FiscalRegime\": \"Regimen de Incorporacion Fiscal\",\"Rfc\": \"AAA010101AAA\",\"Name\": \"Expresion en Software SAPI de CV\"},\"Receiver\": {\"Rfc\": \"XAXX010101000\",\"Name\": \"Fulano\"},\"Items\": [{\"Description\": \"110 Folios\",\"Unit\": \"SERVICIO\",\"UnitPrice\": 1.0,\"Quantity\": 2.0,\"Subtotal\": 100.0,\"Discount\": 10.0,\"Taxes\": [{\"Total\": 16.0,\"Name\": \"IVA\",\"Rate\": 16.0}],\"Total\": 116.0}]}";
            /*
            org.json.JSONObject obj = new org.json.JSONObject();
            try {
                obj.put("id", "3");
                obj.put("name", "NAME OF STUDENT");
                obj.put("year", "3rd");
                obj.put("curriculum", "Arts");
                obj.put("birthday", "5/5/1993");
            } catch (org.json.JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */



            OutputStream os = conn.getOutputStream();
            //java.nio.charset.Charset.forName("UTF-8").encode(input);
            //os.write(input.getBytes("UTF-8"));
            os.write(crearJSON().toString().getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != 200) {
                Log.e("ERROR",String.valueOf(conn.getResponseCode()));
                conectado=false;
                error=conn.getResponseCode()+" --- "+getStringFromInputStream(conn.getErrorStream());
                Log.e("ERROR",conn.getResponseCode()+" --- "+error);
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            Log.i("info","Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                out=output;

            }
            ticket = new JSONObject(out);
            conectado=true;
            Log.i("info",ticket.toString());

/*
            if (conn.getResponseCode() != 200) {
                conectado = false;
                result = "Fallo : HTTP error code : "
                        + conn.getResponseCode();

            } else {
                conectado = true;
            }
            InputStream stream = conn.getInputStream();
            java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";
            ticket = new JSONObject(result);
            Log.i("info", "Output from Server .... \n" + result);

*/

            conn.disconnect();

        } catch (MalformedURLException e) {
            conectado = false;
            e.printStackTrace();

        } catch (IOException e) {
            conectado = false;
            e.printStackTrace();

        }catch (JSONException e) {
            conectado = false;
            e.printStackTrace();

        }
    }

    private JSONObject crearJSON(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("posicion", (pos.getSelectedItemPosition() + 1));
            obj.put("mop", mop.getSelectedItem().toString());
            obj.put("tipoVenta", tipoV.getSelectedItem().toString());
            obj.put("nip", 0);
            obj.put("idEmpleado", 0);
            obj.put("idTerminal", 1);
            JSONArray items=new JSONArray();
            /*
            if(AceitesIm.size()>0){
                for(int i=0;i<AceitesIm.size();i++){
                    JSONObject objA = new JSONObject();
                    Vector temp=(Vector)AceitesIm.get(i);
                    objA.put("id",temp.get(0).toString());
                    objA.put("cantidad",Double.valueOf(temp.get(1).toString()));
                    items.put(objA);
                }
            }*/
            System.err.println("tamaño arreglo "+((RecyclerViewAdapter)recyclerView.getAdapter()).getData().size());
            if(((RecyclerViewAdapter)recyclerView.getAdapter()).getData().size()>0){
                for(int i=0;i<((RecyclerViewAdapter)recyclerView.getAdapter()).getData().size();i++){
                    JSONObject objA = new JSONObject();
                    objA.put("id",((RecyclerViewAdapter)recyclerView.getAdapter()).getData().get(i).toString());
                    objA.put("cantidad",1);
                    items.put(objA);
                }
            }
            obj.put("items",items);
            //obj.put("birthday", "5/5/1993");
        } catch (org.json.JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("info",obj.toString());
        return obj;
    }


    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    private String espacio(int a,int b){
        String s="";
        for(int i=0;i<(b-a);i++){
            s+=" ";
        }
        return s;
    }



    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void cargarCombo2(){
        try{

            String json;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tipo","ticket");
            json = jsonObject.toString();
            System.err.println("InputStream...." + json);
            restServiceM.setEntity(json);
            restServiceM.execute();
        }catch(org.json.JSONException j){
            j.printStackTrace();
        }
    }

    private void cargarCombo(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("estado","forma pago");
                // All your networking logic
                // should be here

                //envia(String.valueOf(pos.getSelectedItemPosition() + 1));
                fpago=new ClienteFormaPago();
                fpago.doPost();

                try {
                    Log.i("tamaño",String.valueOf(fpago.getJSON().getJSONArray("items").length()));
                    ArrayList<String> catList=new ArrayList<>();
                    JSONArray jsonarray = fpago.getJSON().getJSONArray("items");



                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        catList.add(jsonobject.getString("tipoModoPago"));
                        Log.i("valor",jsonobject.getString("tipoModoPago"));
                    }
                    mop.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, catList));
                }catch (Exception e){
                    e.printStackTrace();
                }
/*
                Log.i("estado",String.valueOf(conectado));
                try {
                    Log.i("tamaño",String.valueOf(ticket.getJSONArray("encabezado").length()));
                    Log.i("tamaño",String.valueOf(ticket.getJSONArray("encabezado").get(0)));
                    Log.i("tamaño",String.valueOf(ticket.getJSONArray("encabezado").get(1)));
                }catch (JSONException E){}*/
            }
        });
    }

    private void printVoucher(JSONObject txInfo) {

        // txInfo puede ser el objeto donde almacenes toda la información necesaria para imprimir
        Ticket ticket = new TxTicket(this, txInfo);
        PrRequest request = new PrRequest(ticket);
        PrConfiguration configuration = new PrConfiguration(PrAction.PRINT_TICKET);

        try {

            PrintCoreServiceImpl.getInstance().execute(request, configuration, new Callback<PrResponse>() {
                @Override
                public void onSuccess(PrResponse response) {
                    Log.i("PRINT_ACTIVITY", "Printed");
                }

                @Override
                public void onError(Error error) {
                    Log.i("PRINT_ACTIVITY", "Error: " + error.getMessage());
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }

    private void printVoucher2(String ticket){
        int ret = printer.prn_getStatus();
        if (ret == 0) {
            doprintwork(ticket);

        } else {
            Intent intent = new Intent(PRNT_ACTION);
            intent.putExtra("ret", ret);
            sendBroadcast(intent);
        }
    }

    private void printVoucher3(JSONObject ticket){


        MitVaucher oVaucher = new MitVaucher();

        oVaucher.setVaucherLine(new Vaucher().getTicket(ticket,"venta"));
        MitPrinterWpoy oPrinter = new MitPrinterWpoy(oVaucher, this);
        oPrinter.execute();



/*
        try {

            PrintCoreServiceImpl.getInstance().execute(request, configuration, new Callback<PrResponse>() {
                @Override
                public void onSuccess(PrResponse response) {
                    Log.i("PRINT_ACTIVITY", "Printed");
                }

                @Override
                public void onError(Error error) {
                    Log.i("PRINT_ACTIVITY", "Error: " + error.getMessage());
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

 */
/*
        //if (ApiApplication.DEVICE == MitEnum.Device.WPOS) {
        if (false) {
            MitPrinterWposJava oPrinter = new MitPrinterWposJava(oVaucher, this);
            oPrinter.printVaucher();
        } else {
            MitPrinterWpoy oPrinter = new MitPrinterWpoy(oVaucher, this);
            oPrinter.execute();
        }

 */


    }

    void doprintwork(String msg) {
        if (checkTempThreshold())
            return;

        Intent intentService = new Intent(this, PrintBillService.class);
        intentService.putExtra("SPRT", msg);
        intentService.putExtra("font-info", mFontStylePanel.getFontInfo());
        startService(intentService);
    }

    private void printCorte(JSONObject txInfo) {

        // txInfo puede ser el objeto donde almacenes toda la información necesaria para imprimir
        Ticket ticket = new TxCorte(this, txInfo);
        PrRequest request = new PrRequest(ticket);
        PrConfiguration configuration = new PrConfiguration(PrAction.PRINT_TICKET);

        try {

            PrintCoreServiceImpl.getInstance().execute(request, configuration, new Callback<PrResponse>() {
                @Override
                public void onSuccess(PrResponse response) {
                    Log.i("PRINT_ACTIVITY", "Printed");
                }

                @Override
                public void onError(Error error) {
                    Log.i("PRINT_ACTIVITY", "Error: " + error.getMessage());
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }

    private void printInventario(JSONObject txInfo) {

        // txInfo puede ser el objeto donde almacenes toda la información necesaria para imprimir
        Ticket ticket = new TxInventario(this, txInfo);
        PrRequest request = new PrRequest(ticket);
        PrConfiguration configuration = new PrConfiguration(PrAction.PRINT_TICKET);

        try {

            PrintCoreServiceImpl.getInstance().execute(request, configuration, new Callback<PrResponse>() {
                @Override
                public void onSuccess(PrResponse response) {
                    Log.i("PRINT_ACTIVITY", "Printed");
                }

                @Override
                public void onError(Error error) {
                    Log.i("PRINT_ACTIVITY", "Error: " + error.getMessage());
                }
            });
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }

    }

    private void scanQr() {
        try {
            if (!Build.DEVICE.toLowerCase().contains("wpos")) {
                IntentIntegrator intentIntegrator = new IntentIntegrator((Activity)this);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                intentIntegrator.setPrompt("Escanea codigo de barras");
                //intentIntegrator.setResultDisplayDuration(0L);
                intentIntegrator.addExtra("RESULT_DISPLAY_DURATION_MS", 5000L);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                intentIntegrator.setCameraId(0);
                intentIntegrator.initiateScan();
            } else {
                try {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (Exception exception) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.zxing.client.android")));
                }
            }
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        String str="";
        super.onActivityResult(paramInt1, paramInt2, paramIntent);


        if (!Build.DEVICE.toLowerCase().contains("wpos")) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(paramInt1, paramInt2, paramIntent);
            if (intentResult != null) {
                str = intentResult.getContents();
                if (str == null) {
                    //FragmentUtils.showError((Context)this, "Debes escanear tu cQR de activacipara continuar");
                    return;
                }
                setAceites(str);
                return;
            }
        } else if (paramInt1 == 0) {
            if (paramInt2 == -1) {
                str =paramIntent.getStringExtra("SCAN_RESULT");

            }
            if (paramInt2 != -1) {
                //FragmentUtils.showError((Context)this, "No fue posible recuperar el cde activaci");
                return;
            }
        }
    }

    private final Handler mHandlerG = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // t_query1.setText((String) msg.obj);
            codigoHTTP=msg.arg2;
            respuestaJSON=(String) msg.obj;
            System.out.println("Respuesta del WebService codigo:"+msg.arg2+" json: "+(String) msg.obj);

            try{
                //cliente=new JSONObject(respuestaJSON);
               // hideAllItems(((codigoHTTP==200)?false:true));
                if(codigoHTTP!=200){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.ctx);
                    builder.setTitle("Error de Validacion");
                    builder.setIcon(R.drawable.error);
                    //builder.setMessage(cliente.getString("descripcion"));
                    //builder.setMessage((codigoHTTP==0)?respuestaJSON:new JSONObject(respuestaJSON).getString("descripcion"));
                    builder.setMessage((respuestaJSON.contains("descripcion"))?new JSONObject(respuestaJSON).getString("descripcion"):respuestaJSON.substring(0,((respuestaJSON.length()<200)?respuestaJSON.length():200)));
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                    });
                    builder.show();
                }else{

                    Log.i("info","imprimir");
                    printCorte( new JSONObject(respuestaJSON));
                }
            }catch(Exception e){

            }
        }
    };

    private final Handler mHandlerP = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // t_query1.setText((String) msg.obj);
            codigoHTTP=msg.arg2;
            respuestaJSON=(String) msg.obj;
            System.out.println("Respuesta del WebService codigo:"+msg.arg2+" json: "+(String) msg.obj);

            try{
                //cliente=new JSONObject(respuestaJSON);
                // hideAllItems(((codigoHTTP==200)?false:true));
                if(codigoHTTP!=200){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.ctx);
                    builder.setTitle("Error");
                    builder.setIcon(R.drawable.error);
                    //builder.setMessage(cliente.getString("descripcion"));
                    builder.setMessage((respuestaJSON.contains("descripcion"))?new JSONObject(respuestaJSON).getString("descripcion"):respuestaJSON.substring(0,((respuestaJSON.length()<200)?respuestaJSON.length():200)));
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                    });
                    builder.show();
                }else{

                    ticket=new JSONObject(respuestaJSON);
                    Log.i("info","imprimir");
                    printVoucher2(ticket.toString());
                    AceitesIm.removeAllElements();
                    ((RecyclerViewAdapter)recyclerView.getAdapter()).getData().clear();
                    recyclerView.getAdapter().notifyDataSetChanged();


                }
            }catch(Exception e){
                e.printStackTrace();

            }
        }
    };

    private final Handler mHandlerM = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // t_query1.setText((String) msg.obj);
            codigoHTTP=msg.arg2;
            respuestaJSON=(String) msg.obj;
            System.out.println("Respuesta del WebService codigo:"+msg.arg2+" json: "+(String) msg.obj);

            try{
                //cliente=new JSONObject(respuestaJSON);
                // hideAllItems(((codigoHTTP==200)?false:true));
                if(codigoHTTP!=200){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.ctx);
                    builder.setTitle("Error");
                    builder.setIcon(R.drawable.error);
                    //builder.setMessage(cliente.getString("descripcion"));

                    builder.setMessage((respuestaJSON.contains("descripcion"))?new JSONObject(respuestaJSON).getString("descripcion"):respuestaJSON.substring(0,((respuestaJSON.length()<200)?respuestaJSON.length():200)));
                    //builder.setMessage((codigoHTTP==0)?respuestaJSON:new JSONObject(respuestaJSON).getString("descripcion"));
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                    });
                    builder.show();
                }else{


                    try {
                        formapago=new JSONObject(respuestaJSON);
                        Log.i("tamaño",String.valueOf(formapago.getJSONArray("items").length()));
                        ArrayList<String> catList=new ArrayList<>();
                        JSONArray jsonarray = formapago.getJSONArray("items");



                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            catList.add(jsonobject.getString("tipoModoPago"));
                            Log.i("valor",jsonobject.getString("tipoModoPago"));
                        }
                        mop.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, catList));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }catch(Exception e){

            }
        }
    };

    private final Handler mHandlerI = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // t_query1.setText((String) msg.obj);
            codigoHTTP=msg.arg2;
            respuestaJSON=(String) msg.obj;
            System.out.println("Respuesta del WebService codigo:"+msg.arg2+" json: "+(String) msg.obj);

            try{
                //cliente=new JSONObject(respuestaJSON);
                // hideAllItems(((codigoHTTP==200)?false:true));
                if(codigoHTTP!=200){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this.ctx);
                    builder.setTitle("Error de Validacion");
                    //builder.setMessage(cliente.getString("descripcion"));
                    //builder.setMessage((codigoHTTP==0)?respuestaJSON:new JSONObject(respuestaJSON).getString("descripcion"));
                    builder.setMessage((codigoHTTP!=200 )?respuestaJSON.substring(0,200):new JSONObject(respuestaJSON).getString("descripcion"));
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                    });
                    builder.show();
                }else{

                    Log.i("info","imprimir");
                    printInventario( new JSONObject(respuestaJSON));
                }
            }catch(Exception e){

            }
        }
    };

    private void Corte() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmación");
            builder.setMessage("¿Esta seguro de que quiere realizar el "+((opcion==2)?"Corte Parcial":"Corte Fin Turno"));
            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        String json;
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("tipo", ((opcion==2)?"parcial":"cierre"));
                        json = jsonObject.toString();
                        System.out.println("InputStream...." + json);

                        restServiceG.setEntity(json);
                        restServiceG.execute();
                    }catch (org.json.JSONException j) {
                        j.printStackTrace();
                    }

                }
            });
            builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();



    }

    private void inventarioTanques() {

                try {
                    String json;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("comando", "inventario");
                    json = jsonObject.toString();
                    System.out.println("InputStream...." + json);

                    restServiceI.setEntity(json);
                    restServiceI.execute();
                }catch (org.json.JSONException j) {
                    j.printStackTrace();
                }


    }

    private void mostrarAceites() {
        String cadena="";
        if (!AceitesIm.isEmpty()) {

            for (int i = 0; i < AceitesIm.size(); i++) {
                Vector temp=(Vector)AceitesIm.get(i);
                cadena+=temp.get(0).toString()+" Cantidad:"+temp.get(1).toString()+"\n";
            }

        }
        textRecord.setText((cadena.length()>0)?"Aceites agregados:\n"+cadena:cadena);
    }

    private boolean checkTempThreshold() {
        int currentTemp = getCurrentTemp();
        if (currentTemp == PRNSTS_BUSY) {
            Log.e(TAG, "Printer is busy");
            Toast.makeText(getApplicationContext(),
                    "Impresora occupada"+currentTemp,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (currentTemp == PRNSTS_OVER_HEAT) {

            Log.e(TAG, "Printer is overheat");
            Toast.makeText(getApplicationContext(),
                    "sobrecalentamiento"+currentTemp,
                    Toast.LENGTH_SHORT).show();
            return true;
        }



        if (currentTemp >= mTempThresholdValue) {
            Log.e(TAG, "Printer temperature meets the Threshold: " + mTempThresholdValue);
            Toast.makeText(getApplicationContext(),
                    "sobrecalentamiento"+currentTemp+" "+mTempThresholdValue,
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }


    private int getCurrentTemp() {
        if (printer == null) {
            printer = new PrinterManager();
        }

        int currentTempVolt = 0;//printer.prn_getTemp() ;

//		Log.d("printer", "---------currentTempVolt---------" + currentTempVolt);

        String tmp = String.valueOf(currentTempVolt);
        // get first 4# or first 3#
        if (tmp.length() >= 4) {
            if (tmp.length() == 4 || tmp.length() == 6) {        // when temperature equals 80
                currentTempVolt = Integer.parseInt(tmp.substring(0, 3));
            } else {
                currentTempVolt = Integer.parseInt(tmp.substring(0, 4));
            }
        }

//		Log.d("printer", "getCurrentTemp =============== " + currentTempVolt);
        if (currentTempVolt < 0)
            return currentTempVolt;
        return voltToTemp(mVoltTempPair, currentTempVolt);
    }

    private int voltToTemp(int[][] table, int voltValue) {
        int left_side = 0;
        int right_side = table.length - 1;
        int mid;

        int realTemp = 0;

        while (left_side <= right_side) {
            mid = (left_side + right_side) / 2;

            if (mid == 0 || mid == table.length - 1 ||
                    (table[mid][0] <= voltValue && table[mid + 1][0] > voltValue)) {
                realTemp = table[mid][1];
                break;
            } else if (voltValue - table[mid][0] > 0)
                left_side = mid + 1;
            else
                right_side = mid - 1;
        }

        return realTemp;
    }


        @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        unregisterReceiver(mScanReceiver);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        action_value_buf = mScanManager.getParameterString(idbuf);
        filter.addAction(action_value_buf[0]);
        registerReceiver(mScanReceiver, filter);
    }


    @Override
    public void onSuccess(PrResponse po) {
        Log.d("TAG", "Se imprimio de manera correcta");
    }

    @Override
    public void  onError(Error po) {
        Log.e("TAG", po.getMessage()+"Error no controlado en la impresión");
    }


    private void populateRecyclerView() {
        mAdapter = new RecyclerViewAdapter(stringArrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 1,RecyclerView.VERTICAL, false));
    }

    private void setResult(){
        try{

            stringArrayList.clear();
            if (!AceitesIm.isEmpty()) {

                for (int i = 0; i < AceitesIm.size(); i++) {
                    Vector temp=(Vector)AceitesIm.get(i);
                    stringArrayList.add("Aceite: "+temp.get(0).toString());
                }

            }



            mAdapter = new RecyclerViewAdapter(stringArrayList);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();


        }catch(Exception e){
            e.printStackTrace();

        }

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final String item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Un Item fue removido de la lista", Snackbar.LENGTH_LONG);
                snackbar.setAction("DESHACER", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


}