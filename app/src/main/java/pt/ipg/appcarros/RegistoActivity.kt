package pt.ipg.appcarros

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistoActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.registaractivity)

        //Iniciar Firebase
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("Empresa")

        var btn_registar = findViewById<Button>(R.id.btn_reg_user)
        var btn_voltar_login = findViewById<Button>(R.id.btn_reg_go_login)

        btn_registar.setOnClickListener {

            registarUser()
        }

        btn_voltar_login.setOnClickListener {

            irPaginaLogin()
        }
    }

    private fun registarUser (){
        if (!validarcampo()){
            return
        }

        var edit_nome = findViewById<EditText>(R.id.user_nome)
        var edit_email = findViewById<EditText>(R.id.user_email)
        var edit_pass = findViewById<EditText>(R.id.user_pass)
        var edit_tele = findViewById<EditText>(R.id.user_tele)
        var edit_morada = findViewById<EditText>(R.id.user_morada)
        var edit_cidade = findViewById<EditText>(R.id.user_cidade)

        auth.createUserWithEmailAndPassword(edit_email.text.toString(), edit_pass.text.toString())
            .addOnCompleteListener {
                if (it.isSuccessful){

                    val currentUser = auth.currentUser
                    val empresa = Empresa()

                    if (currentUser != null) {
                        empresa.uid = currentUser.uid.toString()
                    }
                    empresa.nome = edit_nome.text.toString()
                    empresa.mail = edit_email.text.toString()
                    empresa.contatotelefone = edit_tele.text.toString()
                    empresa.morada = edit_morada.text.toString()
                    empresa.localidade = edit_cidade.text.toString()

                    empresa.uid?.let { it -> databaseReference?.child(it)?.setValue(empresa) }
                    Toast.makeText(this@RegistoActivity, "Registo com Sucesso", Toast.LENGTH_SHORT).show()
                    irPaginaLogin()
                }else{

                    Toast.makeText(this@RegistoActivity, "Este email já existe", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun validarcampo(): Boolean{

        var valido = true

        var edit_nome = findViewById<EditText>(R.id.user_nome)
        var edit_email = findViewById<EditText>(R.id.user_email)
        var edit_pass = findViewById<EditText>(R.id.user_pass)
        var edit_tele = findViewById<EditText>(R.id.user_tele)
        var edit_morada = findViewById<EditText>(R.id.user_morada)

        var edit_cidade = findViewById<EditText>(R.id.user_cidade)

        if (TextUtils.isEmpty(edit_nome.text.toString())){
            edit_nome.setError("Introduza o nome de Restaurantes")
            edit_nome.requestFocus()
            valido = false
        }else if (!isValidEmail(edit_email.text.toString())){
            edit_email.setError("Introduza o email válido")
            edit_email.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_pass.text.toString()) || edit_pass.text.length < 6){
            edit_pass.setError("Password inválida, tem que ser maior ou igaul 6 caracter")
            edit_pass.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_tele.text.toString())){
            edit_tele.setError("Introduza o número de telemóvel")
            edit_tele.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_morada.text.toString())){
            edit_morada.setError("Introduza a morada")
            edit_morada.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_morada.text.toString())){

            valido = false
        }else if (TextUtils.isEmpty(edit_cidade.text.toString())){
            edit_cidade.setError("Introduza Cidade")
            edit_cidade.requestFocus()
            valido = false
        }else{

            edit_nome.setError(null)
            edit_email.setError(null)
            edit_pass.setError(null)
            edit_tele.setError(null)
            edit_morada.setError(null)
  
            edit_cidade.setError(null)
        }

        return valido
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun irPaginaLogin (){

        val intent = Intent(this@RegistoActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}