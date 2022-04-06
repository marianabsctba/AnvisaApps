package br.infnet.marianabs.anvisaApp.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.infnet.marianabs.anvisaApp.ui.view.QuizRegisterActivity
import br.infnet.marianabs.anvisaApp.ui.view.StatisticsActivity
import br.infnet.marianabs.anvisaApp.ui.view.UserQuizActivity
import br.infnet.marianabs.anvisaApp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btn_cadastar_avaliacao.setOnClickListener {
            var intent = Intent(this, QuizRegisterActivity::class.java)
            startActivity(intent)
        }

        btn_minhas_avaliacoes.setOnClickListener {
            var intent = Intent(this, UserQuizActivity::class.java)
            startActivity(intent)
        }

        btn_dados_sintetizados.setOnClickListener {
            var intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
