package pt.ipg.appcarros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_carro.*

class CarroActivity : AppCompatActivity() {


    lateinit var ref: DatabaseReference
    lateinit var carro: MutableList<Carro>
    lateinit var carroAdapter: CarroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        //ToolBar
        supportActionBar?.title = "Carro"

        //Iniciar Firebase
        ref = FirebaseDatabase.getInstance().getReference("Carro")
        carro = mutableListOf()

        var btn_ins_carro = findViewById<Button>(R.id.btn_ins_carro)

        btn_ins_carro.setOnClickListener {

            abrirCarroInsPag()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    carro.clear()
                    for (prt in dataSnapshot.children) {
                        val carros = prt.getValue(Carro::class.java)
                        carro.add(carros!!)
                    }

                    carroAdapter = CarroAdapter(this@CarroActivity, carro)

                    recy_Carro.apply {
                        layoutManager = LinearLayoutManager(this@CarroActivity)
                        adapter = carroAdapter
                    }
                }
            }
        })
    }

    private fun abrirCarroInsPag() {

        val intent = Intent(this@CarroActivity, InserirCarroActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}