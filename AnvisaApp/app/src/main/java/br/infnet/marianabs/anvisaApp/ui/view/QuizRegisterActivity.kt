package br.infnet.marianabs.anvisaApp.ui.view

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.infnet.marianabs.anvisaApp.R
import com.google.firebase.auth.FirebaseAuth
import br.infnet.marianabs.anvisaApp.model.Quiz
import br.infnet.marianabs.anvisaApp.database.Database
import br.infnet.marianabs.anvisaApp.database.DbFab
import br.infnet.marianabs.anvisaApp.database.utils.SecurityMaker
import br.infnet.marianabs.anvisaApp.ui.user.MenuActivity
import br.infnet.marianabs.anvisaApp.ui.utils.Dialog
import kotlinx.android.synthetic.main.activity_quiz_register.*

class QuizRegisterActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_register)


        auth = FirebaseAuth.getInstance()

        btn_send_aval.setOnClickListener {
            var organization = cadastro_empresa.text.toString()
            var localization = bairro_cadastro.text.toString()



            if(!organization.isNullOrBlank() && !localization.isNullOrBlank() &&
                !checkAnswers()) {
                var db = DbFab.getInstance(null)
                RegisterQuizAsync(this, db,auth.currentUser!!.uid,organization,localization.capitalize(),takeAnswers()).execute()
            }
            else
                Toast.makeText(this,"Preencha os campos corretamente!",Toast.LENGTH_SHORT)
        }
    }

    class RegisterQuizAsync(activity: Activity , db : Database , serie : String , organization : String , localization : String , answers : String) : AsyncTask<Void, Void, Void>() {


        var activity = activity
        var organization = organization
        var localization = localization
        var answers = answers
        var db = db
        var serie = serie
        var dialogApi = Dialog(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Cadastrando avaliação...")
        }

        override fun doInBackground(vararg params: Void?): Void? {

            var organizationSecurity = SecurityMaker()
            organizationSecurity.setClearText(organization)

            db.quizDao().insertAll(Quiz(0,serie, organizationSecurity, localization,answers))
            dialogApi.dismiss()

            var intent = Intent(activity, MenuActivity::class.java)
            activity.startActivity(intent)
            return null
        }
    }

    fun checkAnswers() : Boolean{
        if(respostaSim1.isChecked || respostaNao1.isChecked)
            return false
        if(respostaSim2.isChecked || respostaNao2.isChecked)
            return false
        if(respostaSim3.isChecked || respostaNao3.isChecked)
            return false
        if(respostaSim4.isChecked || respostaNao4.isChecked)
            return false
        if(respostaSim5.isChecked || respostaNao5.isChecked)
            return false
        if(respostaSim6.isChecked || respostaNao6.isChecked)
            return false

        return true
    }

    fun takeAnswers() : String{
        var answers = ""

        if(respostaSim1.isChecked)
            answers += "1,"
        else
            answers += "0,"

        if(respostaSim2.isChecked)
            answers += "1,"
        else
            answers += "0,"

        if(respostaSim3.isChecked)
            answers += "1,"
        else
            answers += "0,"

        if(respostaSim4.isChecked)
            answers += "1,"
        else
            answers += "0,"

        if(respostaSim5.isChecked)
            answers += "1,"
        else
            answers += "0,"

        if(respostaSim6.isChecked)
            answers += "1"
        else
            answers += "0"

        return answers
    }
}
