package mx.tecnm.tepic.u2_tarea2_procesamiento_en_paralelo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var contadorHilo=0
    var hilito=Hilo(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnHilo.setOnClickListener {
            try{
                hilito.start();
            }catch(e: Exception){
                AlertDialog.Builder(this)
                        .setMessage("El Hilo no se puede volver a ejecutar")
                        .setTitle("Atención")
                        .setPositiveButton("ok"){
                            d,i->
                        }
            }
        }
        btnPause.setOnClickListener {
            hilito.pausar();
        }
        btnEnd.setOnClickListener {
            hilito.terminarHilo();
        }
        btnReboot.setOnClickListener {
            hilito.reiniciar();
        }
    }
}

//------------------AQUI ESTAMOS CREANDO UNA NUEVA CLASE
//CLASE HILO, RECORDEMOS QUE UN HILO ES UNA CLASE SIEMPRE (Se le llama clase anónima cuando no tiene un archivo para ella sola)
class Hilo(p:MainActivity) : Thread(){// los dos puntos quiere decir que se hereda a partir de la clase de Kotlin/java "Thread"
var puntero=p;
    var mantener=true;
    var despausado=true;
    fun terminarHilo(){
        mantener=false;
    }
    fun pausar(){
        despausado=!despausado;
    }
    fun reiniciar(){
        puntero.contadorHilo=0;
    }
    override fun run(){
        super.run() //Ejecuta algo en segundo plano todo el tiempo una sola vez
        while(mantener){ //Permite mantener en ejecucion permanente al run en 2do plano
            if(despausado==true) {
                puntero.contadorHilo++;
                puntero.runOnUiThread {
                    puntero.txtHilo.text = "Hilo: " + puntero.contadorHilo;
                }
            }
            sleep(500) //Tiempo que duerme el hilo
        }
    }
}
