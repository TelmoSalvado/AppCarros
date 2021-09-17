package pt.ipg.appcarros

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.carroitem.view.*
import kotlinx.android.synthetic.main.update_carro.view.*

class CarroAdapter(val context: Context, val carro: List<Carro>) :
    RecyclerView.Adapter<CarroAdapter.PratosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PratosViewHolder {

            return PratosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.carroitem, parent, false))
    }

    override fun onBindViewHolder(holder: PratosViewHolder, position: Int) {

        holder.bindItem(carro[position])
    }

    override fun getItemCount(): Int  = carro.size

    inner class PratosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNome = itemView.item_carro_nome
        val tvKm = itemView.item_carro_Km
        val tvMatri = itemView.item_carro_Matricula
        val tvAno = itemView.item_carro_ano
        val btnUpdate = itemView.btn_update_carro
        val btnDelete = itemView.btn_delete_carro

        fun bindItem(carro: Carro) {
            tvNome.text = carro.marca
            tvKm.text = carro.kilometro
            tvMatri.text = carro.matricula
            tvAno.text = carro.ano

            btnUpdate.setOnClickListener { showUpdateDialog(carro) }
            btnDelete.setOnClickListener { deleteInfo(carro) }
        }

        fun showUpdateDialog(carro: Carro) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Atualizar")

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.update_carro, null)

            view.update_carro_nome.setText(carro.marca)
            view.update_carro_km.setText(carro.kilometro)
            view.update_carro_matri.setText(carro.matricula)
            view.update_carro_ano.setText(carro.ano)

            builder.setView(view)
            builder.setPositiveButton("Atualizar") { dialog, which ->
                val dbUsers = FirebaseDatabase.getInstance().getReference("Carro")

                val marca = view.update_carro_nome.text.toString().trim()
                val kilometro = view.update_carro_km.text.toString().trim()
                val matricula = view.update_carro_matri.text.toString().trim()
                val ano = view.update_carro_ano.text.toString().trim()

                if (marca.isEmpty()) {
                    view.update_carro_nome.error = "Valor Inválido"
                    view.update_carro_nome.requestFocus()
                    return@setPositiveButton
                }else if (kilometro.isEmpty()){
                    view.update_carro_km.error = "Valor Inválido"
                    view.update_carro_km.requestFocus()
                    return@setPositiveButton
                }else if (matricula.isEmpty()){
                    view.update_carro_matri.error = "Valor Inválido"
                    view.update_carro_matri.requestFocus()
                    return@setPositiveButton
                }else if (ano.isEmpty()){
                    view.update_carro_ano.error = "Valor Inválido"
                    view.update_carro_ano.requestFocus()
                    return@setPositiveButton
                }


                val prato = Carro(carro.id, marca, kilometro, matricula, ano)

                dbUsers.child(prato.id).setValue(prato).addOnCompleteListener {
                    Toast.makeText(context, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Não") { dialog, which ->

            }

            val alert = builder.create()
            alert.show()
        }

        fun deleteInfo(carro: Carro) {
            val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Eliminar...")
            progressDialog.show()

            val mydatabase = FirebaseDatabase.getInstance().getReference("Carro")
            mydatabase.child(carro.id).removeValue()

            Toast.makeText(context, "Eliminado com Sucesso", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, CarroActivity::class.java)
            context.startActivity(intent)
        }
    }


}


