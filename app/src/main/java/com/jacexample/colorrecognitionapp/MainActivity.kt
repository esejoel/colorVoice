package com.jacexample.colorrecognitionapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnMic: Button
    private lateinit var txtRecognizedColor: TextView
    private lateinit var gridLayoutBlue: GridLayout
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas
        btnMic = findViewById(R.id.btnMic)
        txtRecognizedColor = findViewById(R.id.txtRecognizedColor)
        gridLayoutBlue = findViewById(R.id.gridLayoutBlue) // Cambiar a gridLayoutBlue del XML
        btnExit = findViewById(R.id.btnExit)

        // Configurar botón del micrófono
        btnMic.setOnClickListener {
            checkPermissionsAndStartRecognition()
        }

        // Configurar botón de salir
        btnExit.setOnClickListener {
            finish() // Cierra la actividad actual
        }
    }

    // Función para verificar permisos y comenzar el reconocimiento de voz
    private fun checkPermissionsAndStartRecognition() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            // Solicitar permiso si no está concedido
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
        } else {
            // Iniciar reconocimiento de voz si el permiso está concedido
            startVoiceRecognition()
        }
    }

    // Iniciar reconocimiento de voz
    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Dí un color")

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 100)
        } else {
            Toast.makeText(this, "Reconocimiento de voz no soportado", Toast.LENGTH_SHORT).show()
        }
    }

    // Procesar el resultado del reconocimiento de voz
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val recognizedText = results?.get(0)?.lowercase(Locale.ROOT)

            if (recognizedText != null) {
                identifyColor(recognizedText)
            }
        }
    }

    // Función para identificar el color y cambiar el fondo
    private fun identifyColor(colorName: String) {
        val color = when (colorName) {
            "rojo" -> R.color.mi_rojo           // Rojo
            "azul" -> R.color.mi_azul           // Azul
            "verde" -> R.color.mi_verde         // Verde
            "amarillo" -> R.color.mi_amarillo   // Amarillo
            "negro" -> R.color.mi_negro         // Negro
            "blanco" -> R.color.mi_blanco       // Blanco

            // Colores secundarios
            "naranja" -> R.color.mi_naranja     // Naranja
            "morado" -> R.color.mi_morado       // Morado
            "rosado" -> R.color.mi_rosado       // Rosado
            "gris" -> R.color.mi_gris           // Gris
            "violeta" -> R.color.mi_violeta     // Violeta
            "celeste" -> R.color.mi_celeste     // Celeste
            "café" -> R.color.mi_café           // Café

            // Colores adicionales
            "turquesa" -> R.color.mi_turquesa   // Turquesa
            "oliva" -> R.color.mi_oliva         // Oliva
            "dorado" -> R.color.mi_dorado       // Dorado
            "plateado" -> R.color.mi_plateado   // Plateado
            "cian" -> R.color.mi_cian           // Cian
            "magenta" -> R.color.mi_magenta     // Magenta
            "lima" -> R.color.mi_lima           // Lima

            else -> null // Si no es un color reconocido
        }

        if (color == null) {
            gridLayoutBlue.setBackgroundColor(ContextCompat.getColor(this, R.color.mi_gris)) // Usar gris como color por defecto
            txtRecognizedColor.text = "Color no reconocido: $colorName"
            Toast.makeText(this, "Color no reconocible: $colorName", Toast.LENGTH_SHORT).show()
        } else {
            gridLayoutBlue.setBackgroundColor(ContextCompat.getColor(this, color))
            txtRecognizedColor.text = "Color reconocido: $colorName"
        }
    }

}
