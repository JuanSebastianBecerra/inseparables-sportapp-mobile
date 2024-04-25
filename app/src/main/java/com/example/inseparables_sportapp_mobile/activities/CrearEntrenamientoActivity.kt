package com.example.inseparables_sportapp_mobile.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class CrearEntrenamientoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var volleyBroker: VolleyBroker

    private lateinit var pickTimeFin: Button
    private lateinit var textPickerFin: TextView
    private lateinit var pickTimeInicio: Button
    private lateinit var textPickerInicio: TextView
    private lateinit var spinnerDeportes: Spinner
    private lateinit var radioDiario: RadioButton
    private lateinit var radioSemanal: RadioButton
    private lateinit var radioFines: RadioButton
    private lateinit var textoNombre: EditText
    private lateinit var textoLugar: EditText
    private lateinit var textoDetalle: EditText
    private lateinit var botonCancelar: MaterialButton
    private lateinit var botonGuardar: MaterialButton

    private lateinit var frecuenciaSeleccionada: String;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_entrenamiento)
        volleyBroker = VolleyBroker(this.applicationContext)
        iniciarComponentes()
        crearTimePickerHoraInicio()
        crearTimePickerHoraFin()
        crearSpinnerDeportes()
        iniciarRadios()
        listenerCancelarEntrenamiento()
        listenerGuardarEntrenamineto()
    }

    private fun iniciarComponentes(){
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun crearTimePickerHoraInicio() {
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

    private fun crearTimePickerHoraFin() {
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

    private fun crearSpinnerDeportes() {
        ArrayAdapter.createFromResource(
            this,
            R.array.deportes_lista,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDeportes.adapter = adapter
        }
        spinnerDeportes.prompt = "Deporte ";
        spinnerDeportes.onItemSelectedListener = this

    }

    private fun iniciarRadios(){
        radioDiario.isChecked = true;
        radioDiario.setOnCheckedChangeListener { buttonView, isChecked ->
            frecuenciaSeleccionada = "DIARIO"
        }

        radioSemanal.setOnCheckedChangeListener { buttonView, isChecked ->
            frecuenciaSeleccionada = "SEMANAL"
        }

        radioFines.setOnCheckedChangeListener { buttonView, isChecked ->
            frecuenciaSeleccionada = "FINES_SEMANA"
        }
    }

    private fun listenerCancelarEntrenamiento(){
        botonCancelar.setOnClickListener {
            this.finish()
        }
    }

    private fun listenerGuardarEntrenamineto(){
        botonGuardar.setOnClickListener {
            val postParams = mapOf<String, Any>(
                "email" to txtUsuario.text.toString(),
                "password" to txtContra.text.toString()
            )

            volleyBroker.instance.add(
                VolleyBroker.postRequest(
                    "${Constants.BASE_URL_PERSONAS}/ingresar", JSONObject(postParams),
                    { response ->
                        // Display the first 500 characters of the response string.
                        Toast.makeText(this, "Bienvenido",
                            Toast.LENGTH_LONG).show();
                        val sharedPreferences = applicationContext.getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
                        sharedPreferences.edit().putString(Constants.TOKEN_KEY, response.getString("token")).apply()
                        sharedPreferences.edit().putString(Constants.ROL_KEY, response.getString("rol")).apply()
                        val intent = Intent(this, InicioActivity::class.java)
                        startActivity(intent)
                    },
                    {
                        Log.d("TAG", it.toString())
                        Toast.makeText(this, "Credenciales incorrectas",
                            Toast.LENGTH_LONG).show();
                    }
                ))
        }

    }


}