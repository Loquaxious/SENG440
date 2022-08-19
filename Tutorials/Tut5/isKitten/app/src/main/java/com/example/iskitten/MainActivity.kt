package com.example.iskitten

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.RemoteActionCompatParcelizer.read
import android.system.Os.read
import android.util.JsonReader
import android.util.JsonWriter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    private lateinit var questionText: TextView
    private var root = Node("Does it have a tail?", Node("kitten"), Node("egg"))
    private var node = root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questionText = findViewById(R.id.questionText)
        val yesButton: Button = findViewById(R.id.yesButton)
        val noButton: Button = findViewById(R.id.noButton)

        yesButton.setOnClickListener {
            if (node.isLeaf()) {
                playAgain()
            } else {
                visit(node.left!!)
            }
        }

        noButton.setOnClickListener {
            if (node.isLeaf()) {
                bifurcate()
            } else {
                visit(node.right!!)
            }
        }

        visit(root)

    }

    override fun onStop() {
        super.onStop()
        val file = openFileOutput("tree.json", Context.MODE_PRIVATE)
        val writer = JsonWriter(OutputStreamWriter(file))
        writer.setIndent("  ")
        root.write(writer)
        writer.close()
    }

    override fun onStart() {
        super.onStart()
        try {
            val file = openFileInput("tree.json")
            val reader = JsonReader(InputStreamReader(file))
            root = Node.read(reader)
            reader.close()
        } catch (e: FileNotFoundException) {
            root = Node("Does it have a tail?", Node("kitten"), Node("egg"))
        }
        visit(root)
    }


    private fun visit(node: Node) {
        questionText.text = node.question()
        this.node = node
    }

    private fun playAgain() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Play again?")
        builder.setPositiveButton("Play") { _, _ ->
            visit(root)
        }
        builder.show()
    }

    private fun bifurcate() {
        val builder = AlertDialog.Builder(this)

        val form = layoutInflater.inflate(R.layout.dialog, null, false)
        builder.setView(form)

        val thingBox: EditText = form.findViewById(R.id.thingBox)
        val questionBox: EditText = form.findViewById(R.id.questionBox)
        val questionPrompt: TextView = form.findViewById(R.id.questionPrompt)

        questionPrompt.text = questionPrompt.text.toString().replace("OLD", node.text)

        builder.setPositiveButton("Add") {_, _ ->
            node.bifurcate(thingBox.text.toString(), questionBox.text.toString())
            playAgain()
        }

        builder.show()
    }
}