package br.infnet.marianabs.anvisaApp.ui.view

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.infnet.marianabs.anvisaApp.R
import br.infnet.marianabs.anvisaApp.adapter.QuizAdapter
import com.google.firebase.auth.FirebaseAuth
import br.infnet.marianabs.anvisaApp.model.Quiz
import br.infnet.marianabs.anvisaApp.database.Database
import br.infnet.marianabs.anvisaApp.database.DbFab
import br.infnet.marianabs.anvisaApp.ui.user.MenuActivity
import br.infnet.marianabs.anvisaApp.ui.utils.Dialog
import kotlinx.android.synthetic.main.activity_user_quiz.*

class UserQuizActivity : AppCompatActivity() {

    private lateinit var listaQuiz: MutableList<Quiz>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_quiz)



        var serie = FirebaseAuth.getInstance().currentUser!!.uid
        var database = DbFab.getInstance(null)

        btn_voltar_minhas_aval.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        listaQuiz = mutableListOf<Quiz>()
        SearchListQuiz(this,listaQuiz,database,serie).execute()
    }



    class SearchListQuiz(activity: Activity , listaAval : MutableList<Quiz> , db : Database , serie : String) : AsyncTask<Void, Void, List<Quiz>?>() {

        var activity = activity
        var list = listaAval
        var database = db
        var serie = serie
        var dialogApi = Dialog(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Buscando suas avalições...")
        }

        override fun doInBackground(vararg params: Void?): List<Quiz>? {
            var list = database.quizDao().loadBySerie(serie)
            dialogApi.dismiss()
            return list
        }

        override fun onPostExecute(result: List<Quiz>?) {
            super.onPostExecute(result)

            var list = activity!!.findViewById<RecyclerView>(R.id.listagem_minhas_aval)

            if (!result.isNullOrEmpty()) {
                result.forEach {
                    this.list.add(it)
                }

                list.layoutManager = LinearLayoutManager(activity)
                list.adapter = QuizAdapter(this.list)
            }

            else {

                var vazio = activity!!.findViewById<TextView>(R.id.empty_minhas_aval)
                list.visibility = View.GONE
                vazio.visibility = View.VISIBLE
            }

        }
    }
}
