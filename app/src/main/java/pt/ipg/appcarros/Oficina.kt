package pt.ipg.appcarros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_mostra_carros.*

class Oficina : AppCompatActivity() {
    lateinit var ref: DatabaseReference
    lateinit var oficina: MutableList<Mecanico>
    lateinit var oficinaAdapter: OficinaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostra_carros)

        //ToolBar
        supportActionBar?.title = "Oficina"

        //Iniciar Firebase
        ref = FirebaseDatabase.getInstance().getReference("Oficina")
        oficina = mutableListOf()

        var btn_ins_oficina = findViewById<Button>(R.id.btn_ins_oficina)

        btn_ins_oficina.setOnClickListener {

            abrirOficina()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    oficina.clear()
                    for (prt in dataSnapshot.children) {
                        val oficinas = prt.getValue(Mecanico::class.java)
                        oficina.add(oficinas!!)
                    }

                    oficinaAdapter = OficinaAdapter(this@Oficina, oficina)

                    recy_Oficina.apply {
                        layoutManager = LinearLayoutManager(this@Oficina)
                        adapter = oficinaAdapter
                    }
                }
            }
        })
    }

    private fun abrirOficina() {

        val intent = Intent(this@Oficina, InserirOficina::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}