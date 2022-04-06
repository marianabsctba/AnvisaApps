package br.infnet.marianabs.anvisaApp.ui.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.infnet.marianabs.anvisaApp.R
import com.google.firebase.auth.FirebaseAuth
import br.infnet.marianabs.anvisaApp.database.DbFab
import br.infnet.marianabs.anvisaApp.ui.utils.Dialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth;
    private lateinit var loading : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        DbFab.getInstance(applicationContext)

        btn_cadastrar_user.setOnClickListener {
            var email = email_cadastrar.text.toString()
            var password = password_cadastrar.text.toString()

            if(!email.isNullOrBlank() && !password.isNullOrBlank())
            {
                loading = Dialog(this)
                loading.startLoadingDialog("Carregando...")
                createUser(email,password)
            }
        }

        btn_logar.setOnClickListener {
            var email = emailexistente.text.toString()
            var password = password_existente.text.toString()

            if(!email.isNullOrBlank() && !password.isNullOrBlank())
            {
                loading = Dialog(this)
                loading.startLoadingDialog("Carregando...")
                userLogin(email,password)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null)
        {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    fun createUser(email : String , password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loading.dismiss()
                    var intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    loading.dismiss()
                    Toast.makeText(

                        this, "falhou..verifique se vc ja tem conta.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun userLogin(email : String , password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loading.dismiss()
                    var intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                } else {
                    loading.dismiss()
                    Toast.makeText(
                        this, "falhou..verifique se vc tem conta.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
