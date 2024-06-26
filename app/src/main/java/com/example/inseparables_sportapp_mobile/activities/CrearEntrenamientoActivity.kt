package com.example.inseparables_sportapp_mobile.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.json.JSONObject

class CrearEntrenamientoActivity : AppCompatActivity(){

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;

    lateinit var pickTimeFin: Button
    lateinit var textPickerFin: TextView
    lateinit var pickTimeInicio: Button
    lateinit var textPickerInicio: TextView
    lateinit var spinnerDeportes: Spinner
    lateinit var radioDiario: RadioButton
    lateinit var radioSemanal: RadioButton
    lateinit var radioFines: RadioButton
    lateinit var textoNombre: EditText
    lateinit var textoLugar: EditText
    lateinit var textoDetalle: EditText
    lateinit var botonCancelar: MaterialButton
    lateinit var botonGuardar: MaterialButton

    lateinit var frecuenciaSeleccionada: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_entrenamiento)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        iniciarComponentes()
        crearTimePickerHoraInicio()
        crearTimePickerHoraFin()
        crearSpinnerDeportes()
        iniciarRadios()
        listenerCancelarEntrenamiento()
        listenerGuardarEntrenamineto()
    }

    fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun iniciarComponentes(){
         pickTimeFin = findViewById(R.id.pick_time_hora_fin)
         textPickerFin = findViewById(R.id.preview_hora_fin)
         pickTimeInicio = findViewById(R.id.pick_time_hora_inicio)
         textPickerInicio = findViewById(R.id.preview_hora_inicio)
         spinnerDeportes = findViewById(R.id.spinner_deportes)
         radioDiario = findViewById(R.id.radio_diario);
         radioSemanal = findViewById(R.id.radio_semanal);
         radioFines = findViewById(R.id.radio_fines);
         textoNombre = findViewById(R.id.editTextNombreEntrenamiento);
         textoLugar = findViewById(R.id.editTextLugar);
         textoDetalle = findViewById(R.id.editTextDetalle);
         botonCancelar = findViewById(R.id.cancelar_button_entrenamiento);
         botonGuardar = findViewById(R.id.guardar_button_entrenamiento);
    }

    fun crearTimePickerHoraInicio() {
        pickTimeInicio.setOnClickListener {
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                .setTitleText("Selecciona la hora")
                .setHour(12)
                .setMinute(10)
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()
            materialTimePicker.show(supportFragmentManager, "CrearEntrenamientoActivity")
            materialTimePicker.addOnPositiveButtonClickListener {
                val pickedHour: Int = materialTimePicker.hour
                val pickedMinute: Int = materialTimePicker.minute
                val formattedTime: String = when {
                    pickedHour > 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                        }
                    }
                    pickedHour == 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                        }
                    }
                    pickedHour == 0 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                        }
                    }
                    else -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                        }
                    }
                }
                textPickerInicio.text = formattedTime
            }
        }
    }

    fun crearTimePickerHoraFin() {
        pickTimeFin.setOnClickListener {
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                .setTitleText("Selecciona la hora")
                .setHour(12)
                .setMinute(10)
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()
            materialTimePicker.show(supportFragmentManager, "CrearEntrenamientoActivity")
            materialTimePicker.addOnPositiveButtonClickListener {
                val pickedHour: Int = materialTimePicker.hour
                val pickedMinute: Int = materialTimePicker.minute
                val formattedTime: String = when {
                    pickedHour > 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                        }
                    }
                    pickedHour == 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                        }
                    }
                    pickedHour == 0 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                        }
                    }
                    else -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                        }
                    }
                }
                textPickerFin.text = formattedTime
            }
        }
    }

    fun crearSpinnerDeportes() {
        ArrayAdapter.createFromResource(
            this,
            R.array.deportes_lista,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDeportes.adapter = adapter
        }
        spinnerDeportes.prompt = "Deporte ";

    }

    fun iniciarRadios(){
        radioDiario.isChecked = true;
        radioDiario.setOnCheckedChangeListener { _, _ ->
            frecuenciaSeleccionada = "DIARIO"
        }

        radioSemanal.setOnCheckedChangeListener { _, _ ->
            frecuenciaSeleccionada = "SEMANAL"
        }

        radioFines.setOnCheckedChangeListener { _, _ ->
            frecuenciaSeleccionada = "FINES_SEMANA"
        }
    }

    fun listenerCancelarEntrenamiento(){
        botonCancelar.setOnClickListener {
            this.finish()
        }
    }

    fun listenerGuardarEntrenamineto(){
        botonGuardar.setOnClickListener {
            val headersParams: MutableMap<String, String> = HashMap()
            headersParams["Authorization"] = "Bearer $token"
            val postParams = mapOf<String, Any>(
                "nombre" to textoNombre.text.toString(),
                "hora_inicio" to textPickerInicio.text.toString(),
                "hora_fin" to textPickerFin.text.toString(),
                "lugar" to textoLugar.text.toString(),
                "frecuencia" to frecuenciaSeleccionada,
                "detalle" to textoDetalle.text.toString(),
                "deporte" to spinnerDeportes.selectedItem.toString()

            )

            volleyBroker.instance.add(
                VolleyBroker.postRequestWithHeaders(
                    "${Constants.BASE_URL_DEPORTE}/entrenamiento",
                    JSONObject(postParams),
                    {
                        Toast.makeText(this, "Entrenamiento registrado",
                            Toast.LENGTH_LONG).show();
                        Handler(Looper.getMainLooper()).postDelayed({
                            this.finish()
                        }, 3000)
                    },
                    {
                        Log.d("TAG", it.toString())
                        Toast.makeText(this, "Error al registrar el entrenamiento",
                            Toast.LENGTH_LONG).show();
                    },
                    headersParams as HashMap<String, String>
                ))
        }

    }


}