package pt.ipg.appcarros

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homeactivity)
        //Iniciar Firebase
        auth = Firebase.auth

        var btn_logout = findViewById<Button>(R.id.btn_logout)
        btn_logout.setOnClickListener {

            logout()
        }
    }

    fun abrirCarro (view: View){

        val intent = Intent(this@HomeActivity, CarroActivity::class.java)
       startActivity(intent)
    }

    fun abriroficina (view: View){

      val intent = Intent(this@HomeActivity, Oficina::class.java)
      startActivity(intent)
    }


    fun abrirPrefil (view: View){

       val intent = Intent(this@HomeActivity, PerfilActivity::class.java)
       startActivity(intent)
    }

    private fun logout(){

        Firebase.auth.signOut()
       val intent = Intent(this@HomeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


}