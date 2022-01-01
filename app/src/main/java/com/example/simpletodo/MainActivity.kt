package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveitems()
            }

        }

        loaditems()

//        //Detecting when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            //Code in here executes when the user clicks on a button
//
//        }


        //lookup recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener)
        //Attach the adapter to the recyclerview to populate items \
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //Set the button and input field so that the user can enter a task and add it
        //to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //Set a reference to the button
        //and the set the onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. Grab the text the user has inputted into @+id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            saveitems()

        }
    }

    //Save the data that the user has inputted
    //Save the data by writing and reading from a file


    //Create a method to get the file we need
    fun getDataFile() : File {
        return File(filesDir, "file.txt")
    }


    //Load the items by reading every line in the data file
    fun loaditems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


    //Save items by writing them onto the data file
    fun saveitems(){
        try {
           org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}