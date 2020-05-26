package com.jaehyeon.flowerstudio

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.numberprogressbar.NumberProgressBar
import com.daimajia.numberprogressbar.OnProgressBarListener
import com.jaehyeon.flowerstudio.ui.CharacterActivity
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*


class LoadingActivity : AppCompatActivity() {

    companion object {
        // presets for rgb conversion
        const val RESULTS_TO_SHOW = 3
        private const val IMAGE_MEAN = 128
        private const val IMAGE_STD = 128.0f
    }

    private val LOADING_TIME_OUT:Long = 8000 // 10 sec

    private val MODEL = "inception_float.tflite"
    private val LABEL = "labels.txt"

    private val tfliteOptions: Interpreter.Options = Interpreter.Options()

    // tflite graph
    private var tflite: Interpreter? = null

    // holds all the possible labels for model
    private var labelList: List<String>? = null

    // holds the selected image data as bytes
    private var imgData: ByteBuffer? = null

    // holds the probabilities of each label for non-quantized graphs
    private var labelProbArray: Array<FloatArray>? = null

    // array that holds the labels with the highest probabilities
    private var topLabels: Array<String?>? = null

    // array that holds the highest probabilities
    private var topConfidence: Array<String?>? = null

    // input image dimensions for the Inception Model
    private val DIM_IMG_SIZE_X = 299
    private val DIM_IMG_SIZE_Y = 299
    private val DIM_PIXEL_SIZE = 3

    // int array to hold image data
    private lateinit var intValues: IntArray

    // priority queue that will hold the top results from the CNN
    private val sortedLabels: PriorityQueue<Map.Entry<String, Float>> =
        PriorityQueue(
            RESULTS_TO_SHOW,
            Comparator<Map.Entry<String?, Float?>?> { o1, o2 -> o1?.value!!.compareTo(o2!!.value!!)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        Log.d("123123 loading", "success loading!!")
        //initilize graph and labels
        try {
            tflite = Interpreter(loadModelFile(), tfliteOptions)
            labelList = loadLabelList()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        // initialize byte array. The size depends if the input data needs to be quantized or not
        imgData = ByteBuffer.allocateDirect(4 * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE)
        imgData?.order(ByteOrder.nativeOrder())

        labelProbArray = Array(1) { FloatArray(labelList!!.size) }

        // initialize array to hold top labels
        topLabels = arrayOfNulls(RESULTS_TO_SHOW)
        // initialize array to hold top probabilities
        topConfidence = arrayOfNulls(RESULTS_TO_SHOW)

        val classify: Bitmap? = intent.getParcelableExtra("classify")
        val fContext = intent.getStringExtra("flowerContext")
        val fImage = intent.getByteArrayExtra("flowerImg")

        val check = intent.getStringExtra("character")
        val loadingText = findViewById<TextView>(R.id.loading_text)

        if(check == "character"){
            loadingText.text = getString(R.string.loading_text2)
        }

        convertBitmapToByteBuffer(classify!!)

        tflite?.run(imgData, labelProbArray)

        // display the results
        val fName = printTopKLabels()

        // progress_bar % increase
        val npb = findViewById<NumberProgressBar>(R.id.number_progress_bar);
        val timer = Timer()
        npb.setOnProgressBarListener(OnProgressBarListener { current, max ->
            if (current == max) {
                timer.cancel()
            }
        })

        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { npb.incrementProgressBy(1) }
            }
        }, 5, 15)

        Handler().postDelayed({
            if(check == "character"){
                startActivity(Intent(this, CharacterActivity::class.java)
                    .putExtra("flowerName", fName)
                    .putExtra("flowerContext", fContext)
                    .putExtra("flowerImg", fImage)
                )
                finish()
            }else{
                startActivity(Intent(this, CameraResultActivity::class.java)
                    .putExtra("flowerName", fName)
                    .putExtra("flowerContext", fContext)
                    .putExtra("flowerImg", fImage)
                )
                finish()
            }
        }, LOADING_TIME_OUT)
    }

    // converts bitmap to byte array which is passed in the tflite graph
    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        if (imgData == null) {
            return
        }
        imgData!!.rewind()
        bitmap.getPixels(
            intValues,
            0,
            bitmap.width,
            0,
            0,
            bitmap.width,
            bitmap.height
        )
        // loop through all pixels
        var pixel = 0
        for (i in 0 until DIM_IMG_SIZE_X) {
            for (j in 0 until DIM_IMG_SIZE_Y) {
                val `val` = intValues[pixel++]
                // get rgb values from intValues where each int holds the rgb values for a pixel.
                // if quantized, convert each rgb value to a byte, otherwise to a float

                imgData!!.putFloat(((`val` shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                imgData!!.putFloat(((`val` shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                imgData!!.putFloat(((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD)

            }
        }
    }

    // loads tflite grapg from file
    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = this.assets.openFd(MODEL)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    // loads the labels from the label txt file in assets into a string array
    @Throws(IOException::class)
    private fun loadLabelList(): List<String> {
        val labelList: MutableList<String> = ArrayList()
        val reader = BufferedReader(InputStreamReader(this.assets.open(LABEL)))
        var line: String
        while (reader.readLine().also { line = it } != null) {
            labelList.add(line)
        }
        reader.close()
        return labelList
    }

    // print the top labels and respective confidences
    private fun printTopKLabels(): String {
        // add all results to priority queue
        for (i in labelList!!.indices) {
            if (sortedLabels.size > RESULTS_TO_SHOW) {
                sortedLabels.poll()
            } else {
                sortedLabels.add(
                    AbstractMap.SimpleEntry(labelList!![i], labelProbArray!![0][i])
                )
            }
        }

        // get top results from priority queue
        val size: Int = sortedLabels.size
        for (i in 0 until size) {
            val label: Map.Entry<String, Float>? =
                sortedLabels.poll()
            topLabels!![i] = label?.key
            topConfidence!![i] = String.format("%.0f%%", label?.value?.times(100))
        }

        // set the corresponding textviews with the results
        return topLabels!![2] + "    " + topConfidence!![2]
    }

    // 로딩화면에서 뒤로가기 막기
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            false
        } else super.onKeyDown(keyCode, event)
    }

}
