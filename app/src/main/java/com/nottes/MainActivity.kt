package com.nottes

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

//import kotlinx.android.synthetic.main.activity main.*



class MainActivity : AppCompatActivity() {
    lateinit var add:FloatingActionButton
    lateinit var notte_list_view:ListView
    lateinit var loading:TextView
    var ref:DatabaseReference?=null
    var noteList:ArrayList<Notte>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
GlobalScope.launch {
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    ref  = database.getReference("nottes")
}


        initialisation()

        loading.setText("loading")

        noteList =ArrayList()
        loading.setText("")
        showDialogaddNote()

    }

    override fun onStart() {
        super.onStart()
        ref?.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                noteList?.clear()
                for ( n in p0.children){
                    val not = n.getValue(Notte::class.java)
                    noteList!!.add(not!!)
                    val notteAdapter = notteAdapter(applicationContext, noteList!!)
                    notte_list_view.adapter = notteAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                val err = error.message
                Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun initialisation(){
        add=findViewById(R.id.floating)
        notte_list_view=findViewById(R.id.liste_notte)
        loading=findViewById(R.id.load)
    }
    private fun showDialogaddNote(){
        add.setOnClickListener {
            val alertBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.add_notte, null)
            alertBuilder.setView(view)
            val alertDialog = alertBuilder.create()
            alertDialog.show()
            view.findViewById<Button>(R.id.btn_saved_notte).setOnClickListener {
                val title_edit_text = view.findViewById<TextInputEditText>(R.id.title_edit_text).text.toString()
                val notte_edit_text = view.findViewById<TextInputEditText>(R.id.notte_edit_text).text.toString()
                if (title_edit_text.isNotEmpty() && notte_edit_text.isNotEmpty()){
                    val id = ref!!.push().key.toString()
                    val notte = Notte(id, title_edit_text, notte_edit_text, getDate())
                    ref!!.child(id).setValue(notte)
                    alertDialog.dismiss()
                }
                else{
                    Toast.makeText(this, "error input is empty", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
@SuppressLint("SimpleDateFormat")
private fun getDate():String{
    val calendar = Calendar.getInstance()
    val mdformat = SimpleDateFormat("EEEE hh:mm a")
    val strDate = mdformat.format(calendar.time)
    return strDate

}
}