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

class InserirOficina : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inserir_oficina)

        //Iniciar Firabase
        ref = FirebaseDatabase.getInstance().getReference("Oficina")

        var btn_save = findViewById<Button>(R.id.btn_ins_ins_registo)
        var btn_cancel = findViewById<Button>(R.id.btn_prato_back)

        btn_save.setOnClickListener {

            savedata()
        }

        btn_cancel.setOnClickListener {

            voltarOficina()
        }


    }

    private fun savedata (){

        if (!validarcampo()){
            return
        }

        var edit_nome = findViewById<EditText>(R.id.ins_oficina_nome)
        var edit_km = findViewById<EditText>(R.id.ins_oficina_km)
        var edit_preco = findViewById<EditText>(R.id.ins_oficina_preco)
        var edit_desc = findViewById<EditText>(R.id.ins_oficina_desc)

        val oficina = Mecanico()
        oficina.id = ref.push().key.toString()
        oficina.nome = edit_nome.text.toString()
        oficina.kilometros = edit_km.text.toString()
        oficina.descricao = edit_desc.text.toString()
        oficina.preco = edit_preco.text.toString()

        ref.child(oficina.id).setValue(oficina).addOnCompleteListener {
            Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
            edit_nome.setText("")
            edit_km.setText("")
            edit_preco.setText("")
            edit_desc.setText("")

        }
        val intent = Intent(this@InserirOficina, Oficina::class.java)
        startActivity(intent)
    }

    private fun validarcampo (): Boolean{

        var valido = true
        var edit_nome = findViewById<EditText>(R.id.ins_oficina_nome)
        var edit_quanti = findViewById<EditText>(R.id.ins_oficina_km)
        var edit_preco = findViewById<EditText>(R.id.ins_oficina_preco)
        var edit_desc = findViewById<EditText>(R.id.ins_oficina_desc)

        if (TextUtils.isEmpty(edit_nome.text.toString())) {
            edit_nome.setError("Valor Inválido")
            edit_nome.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_quanti.text.toString()) || edit_quanti.text.toString() <= "0"){
            edit_quanti.setError("Valor Inválido")
            edit_quanti.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_preco.text.toString()) || edit_preco.text.toString() <= "0"){
            edit_preco.setError("Valor Inválido")
            edit_preco.requestFocus()
            valido = false
        }else if (TextUtils.isEmpty(edit_desc.text.toString())){
            edit_desc.setError("Valor Inválido")
            edit_desc.requestFocus()
            valido = false
        }else{
            edit_nome.setError(null)
            edit_quanti.setError(null)
            edit_preco.setError(null)
            edit_desc.setError(null)
        }

        return  valido
    }

    private fun voltarOficina (){

        val intent = Intent(this@InserirOficina, Oficina::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }


}