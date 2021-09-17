package pt.ipg.appcarros

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperaPassActivity : AppCompatActivity()  {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recuperarpassactivity)
        var btn_recupass = findViewById<Button>(R.id.btn_recupass)

        btn_recupass.setOnClickListener {

            recuperarPass()
        }
    }
    private fun recuperarPass (){

        if (!validarcampo()){
            return
        }

        var edit_mail = findViewById<EditText>(R.id.recupass_email)

        auth?.sendPasswordResetEmail(edit_mail.text.toString())
            ?.addOnCompleteListener {
                if (it.isSuccessful){

                    Toast.makeText(this@RecuperaPassActivity, "Enviar Email da Confirmação", Toast.LENGTH_SHORT).show()
                }else{

                    Toast.makeText(this@RecuperaPassActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    fun voltarLogin(view: View){

        val intent = Intent(this@RecuperaPassActivity, MainActivity ::class.java)
        startActivity(intent)
        finish()
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_mail = findViewById<EditText>(R.id.recupass_email)

        if (!isValidEmail(edit_mail.text.toString())){
            edit_mail.setError("Introduza o email válido")
            edit_mail.requestFocus()
            valido = false
        }else {
            edit_mail.setError(null)
        }

        return  valido
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}