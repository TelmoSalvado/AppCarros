package pt.ipg.appcarros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InserirCarroActivity : AppCompatActivity() {



    lateinit var ref: DatabaseReference

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_inserir_carro)

        //Iniciar Firabase
        ref = FirebaseDatabase.getInstance().getReference("Carro")

        var btn_save = findViewById<Button>(R.id.btn_ins_ins_carro)
        var btn_cancel = findViewById<Button>(R.id.btn_carro_back)

        btn_save.setOnClickListener {

            savedata()
        }

        btn_cancel.setOnClickListener {

            voltarCarro()
        }


    }

    private fun savedata (){

        if (!validarcampo()){
            return
        }

        var edit_nome = findViewById<EditText>(R.id.ins_carro_nome)
        var edit_kilo = findViewById<EditText>(R.id.ins_carro_kilo)
        var edit_matri = findViewById<EditText>(R.id.ins_carro_matri)
        var edit_ano = findViewById<EditText>(R.id.ins_carro_ano)

        val carro = Carro()
        carro.id = ref.push().key.toString()
        carro.marca = edit_nome.text.toString()
        carro.kilometro = edit_kilo.text.toString()
        carro.matricula = edit_matri.text.toString()
        carro.ano = edit_ano.text.toString()

        ref.child(carro.id).setValue(carro).addOnCompleteListener {
            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            edit_nome.setText("")
            edit_kilo.setText("")
            edit_matri.setText("")
            edit_ano.setText("")

        }
        var intent = Intent(this@InserirCarroActivity, CarroActivity :: class.java)
        startActivity(intent)
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_nome = findViewById<EditText>(R.id.ins_carro_nome)
        var edit_kilo = findViewById<EditText>(R.id.ins_carro_kilo)
        var edit_matri = findViewById<EditText>(R.id.ins_carro_matri)
        var edit_ano = findViewById<EditText>(R.id.ins_carro_ano)

        if (TextUtils.isEmpty(edit_nome.text.toString()) ) {
            edit_nome.setError("Valor inválido")
            edit_nome.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_kilo.text.toString()) || edit_kilo.text.toString() < "0"){
            edit_kilo.setError("Valor inválido")
            edit_kilo.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_matri.text.toString())) {
            edit_matri.setError("Valor inválido")
            edit_matri.requestFocus()
            valido = false
        /*} else if (edit_matri.text.get(3) != "-" && edit_matri.text.get(6) != '-' ){
                edit_matri.setError("Insira no Formato LL - NN - LL")
                edit_matri.requestFocus()
                valido = false*/
        }else if (TextUtils.isEmpty(edit_ano.text.toString()) || edit_ano.text.length != 4){
            edit_ano.setError("Valor inválido")
            edit_ano.requestFocus()
            valido = false
        }else{
            edit_nome.setError(null)
            edit_kilo.setError(null)
            edit_matri.setError(null)
            edit_ano.setError(null)
        }

        return  valido
    }

    private fun voltarCarro (){

        val intent = Intent(this@InserirCarroActivity, CarroActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}