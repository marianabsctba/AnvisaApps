package br.infnet.marianabs.anvisaApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.infnet.marianabs.anvisaApp.R
import br.infnet.marianabs.anvisaApp.model.Quiz

class QuizAdapter (quizes : List<Quiz>) :
    RecyclerView.Adapter<QuizAdapter.UserQuizesViewHolder>(){

    var listOfQuizes = quizes

    class UserQuizesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var organization = itemView.findViewById<TextView>(R.id.empresa_lista_aval)
        var location = itemView.findViewById<TextView>(R.id.bairro_lista_aval)
        var answers = itemView.findViewById<TextView>(R.id.respostas_lista_aval)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserQuizesViewHolder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.user_items_aval, parent, false)

        return UserQuizesViewHolder(card)
    }


    override fun getItemCount() = listOfQuizes.size


    override fun onBindViewHolder(holder: UserQuizesViewHolder , position: Int) {

        holder.organization.text = listOfQuizes[position].organization!!.getClearText()
        holder.location.text = listOfQuizes[position].location!!
        listOfAnswers(holder.answers, listOfQuizes[position].answers!!)
    }

    fun listOfAnswers(txtAnswers : TextView , answers : String){
        var listofAnswers = answers.split(",")
        var format = ""
        var cont = 1

        while (cont <= 6)
        {
            format += if(listofAnswers[cont-1] == "1")
                "$cont-Sim"
            else
                "$cont-Não"

            if(cont < 6)
                format += ", "

            cont++
        }

        txtAnswers.text = format
    }
}