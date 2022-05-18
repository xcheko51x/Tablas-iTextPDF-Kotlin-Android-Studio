package com.example.itextpdfkotlin

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    var listaUsuarios: ArrayList<Usuario> = ArrayList()
    lateinit var btnCrearPdf: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(checkPermission()) {
            Toast.makeText(this, "Permiso Aceptado", Toast.LENGTH_LONG).show();
        } else {
            requestPermissions();
        }

        btnCrearPdf = findViewById(R.id.btnCrearPdf)

        listaUsuarios.add(Usuario("xcheko51x", "Sergio Peralta", "sergiop@local.com"))
        listaUsuarios.add(Usuario("laurap", "Laura Perez", "laurap@local.com"))
        listaUsuarios.add(Usuario("juanm", "Juan Morales", "juanm@local.com"))

        btnCrearPdf.setOnClickListener {
            crearPdf()
        }

    }

    fun crearPdf() {
        var path = Environment.getExternalStorageDirectory().absolutePath+"/EjemploITextPDF"

        val dir = File(path)
        if (!dir.exists())
            dir.mkdirs()

        val file = File(dir, "usuarios.pdf")
        val fileOutputStream = FileOutputStream(file)

        val documento = Document()
        PdfWriter.getInstance(documento, fileOutputStream)

        documento.open()

        val titulo = Paragraph(
            "Lista de Usuarios \n\n\n",
            FontFactory.getFont("arial", 22f, Font.BOLD, BaseColor.BLUE)
        )

        documento.add(titulo)

        var tabla = PdfPTable(3)
        tabla.addCell("USUARIO")
        tabla.addCell("NOMBRE")
        tabla.addCell("CORREO")

        for (item in listaUsuarios){
            tabla.addCell(item.usuario)
            tabla.addCell(item.nombre)
            tabla.addCell(item.email)
        }

        documento.add(tabla)

        documento.close()
    }

    private fun checkPermission(): Boolean {
        val permission1 =
            ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE)
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            200
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 200) {
            if (grantResults.size > 0) {
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}