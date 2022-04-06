package br.infnet.marianabs.anvisaApp.ui.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.infnet.marianabs.anvisaApp.R
import br.infnet.marianabs.anvisaApp.model.Quiz
import br.infnet.marianabs.anvisaApp.database.Database
import br.infnet.marianabs.anvisaApp.database.DbFab
import br.infnet.marianabs.anvisaApp.ui.user.MenuActivity
import br.infnet.marianabs.anvisaApp.ui.utils.Dialog
import kotlinx.android.synthetic.main.activity_statistics.*
import android.os.AsyncTask

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        var finaltext = text_dados_sintet
        var db = DbFab.getInstance(null)

        DownloadingStatistics(this,db,finaltext).execute()

        btn_voltar_sintetizado.setOnClickListener {
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    class DownloadingStatistics(activity: Activity , db : Database , finaltxt : TextView) : AsyncTask<Void, Void, Unit>() {
        var activity = activity
        var db = db
        var finaltxt = finaltxt
        var dialogApi = Dialog(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Carregando estatisticas...")
        }

        override fun doInBackground(vararg params: Void?) {

            var list = db.quizDao().getAllData()

            if(list == null)
                showText(finaltxt,"Opsss.. não há avaliações no sistema.. comece agora!")
            else
            {
                buildDataList(list)
            }
            dialogApi.dismiss()
        }

        fun showText(txtFinal : TextView , txt : String)
        {
            txtFinal.text = txt
        }

        fun buildDataList(list : List<Quiz>){

            var result = mutableMapOf<String, MutableList<Quiz>>()
            var lastLocalization = ""
            var dataTxt = ""
            var cont = 0

            var ordenateList = list.sortedBy { it.location }

            for(aval in ordenateList)
            {
                if(aval.location != lastLocalization)
                {
                    lastLocalization = aval.location!!
                    result.put(aval.location, mutableListOf(aval))
                }
                else
                {
                     var l = result[aval.location]!!.add(aval)
                }

            }

            dataTxt += "    BAIRRO      -   QTIDADE QUIZES      - MÉDIA POSITIVAS (%)\n\n"

            for(pair in result)
            {
                var quantityOfQuiz = pair.value.size
                var answersCollections = takePositiveAnswers(pair.value)

                dataTxt += "     ${pair.key}, $quantityOfQuiz , "

                while(cont < 6)
                {
                    if(cont == 5)
                        dataTxt += "${cont+1} - ${String.format("%.2f",((answersCollections[cont] / quantityOfQuiz)*100.0))}%\n"
                    else
                        dataTxt += "${cont+1} - ${String.format("%.2f",(answersCollections[cont] / quantityOfQuiz)*100.0)}%, "

                    cont++
                }
                cont = 0
            }

            showText(finaltxt,dataTxt)
        }

        fun takePositiveAnswers(locationList : MutableList<Quiz>) : DoubleArray{

            var answersCollections : DoubleArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            var contResp = 0

            for(aval in locationList)
            {
                var parseResp = aval.answers!!.split(",")

                while(contResp < 6)
                {
                    if(parseResp[contResp] == "1")
                        answersCollections[contResp]++

                    contResp++
                }
                contResp = 0
            }

            return answersCollections
        }
    }
}
