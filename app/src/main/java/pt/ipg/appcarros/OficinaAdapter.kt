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
import kotlinx.android.synthetic.main.oficina_item.view.*
import kotlinx.android.synthetic.main.update_carro.view.*
import kotlinx.android.synthetic.main.update_oficina.view.*

class OficinaAdapter(val context: Context, val oficina: List<Mecanico>) :
    RecyclerView.Adapter<OficinaAdapter.PratosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PratosViewHolder {

        return PratosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.oficina_item, parent, false))
    }

    override fun onBindViewHolder(holder: PratosViewHolder, position: Int) {

        holder.bindItem(oficina[position])
    }

    override fun getItemCount(): Int  = oficina.size

    inner class PratosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvNome = itemView.item_oficina_nome
        val tvKm = itemView.item_oficina_km
        val tvPreco = itemView.item_oficina_preco
        val tvDesc = itemView.item_oficina_disc
        val btnUpdate = itemView.btn_update_oficina
        val btnDelete = itemView.btn_delete_prato

        fun bindItem(oficina: Mecanico) {
            tvNome.text = oficina.nome
            tvKm.text = oficina.kilometros
            tvPreco.text = oficina.preco
            tvDesc.text = oficina.descricao

            btnUpdate.setOnClickListener { showUpdateDialog(oficina) }
            btnDelete.setOnClickListener { deleteInfo(oficina) }
        }

        fun showUpdateDialog(oficina: Mecanico) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Atualizar")

            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.update_oficina, null)

            view.update_oficina_nome.setText(oficina.nome)
            view.update_oficina_km.setText(oficina.kilometros)
            view.update_oficina_preço.setText(oficina.preco)
            view.update_oficina_desc.setText(oficina.descricao)

            builder.setView(view)
            builder.setPositiveButton("Atualizar") { dialog, which ->
                val dbUsers = FirebaseDatabase.getInstance().getReference("Oficina")

                val nome = view.update_oficina_nome.text.toString().trim()
                val kilometros = view.update_oficina_km.text.toString().trim()
                val preco = view.update_oficina_preço.text.toString().trim()
                val descricao = view.update_oficina_desc.text.toString().trim()

                if (nome.isEmpty()) {
                    view.update_oficina_nome.error = "Valor Inválido"
                    view.update_oficina_nome.requestFocus()
                    return@setPositiveButton
                }else if (kilometros.isEmpty()){
                    view.update_oficina_km.error = "Valor Inválido"
                    view.update_oficina_km.requestFocus()
                    return@setPositiveButton
                }else if (preco.isEmpty()){
                    view.update_oficina_preço.error = "Valor Inválido"
                    view.update_oficina_preço.requestFocus()
                    return@setPositiveButton
                }else if (descricao.isEmpty()){
                    view.update_oficina_desc.error = "Valor Inválido"
                    view.update_oficina_desc.requestFocus()
                    return@setPositiveButton
                }


                val oficinas = Mecanico(oficina.id, nome, kilometros, preco, descricao)

                dbUsers.child(oficinas.id).setValue(oficinas).addOnCompleteListener {
                    Toast.makeText(context, "Atualizar com Sucesso !", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Não") { dialog, which ->

            }

            val alert = builder.create()
            alert.show()
        }

        fun deleteInfo(oficina: Mecanico) {
            val progressDialog = ProgressDialog(context, R.style.Theme_MaterialComponents_Light_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Eliminar...")
            progressDialog.show()

            val mydatabase = FirebaseDatabase.getInstance().getReference("Oficina")
            mydatabase.child(oficina.id).removeValue()

            Toast.makeText(context, "Eliminado com Sucesso", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, Oficina::class.java)
            context.startActivity(intent)
        }
    }


}


