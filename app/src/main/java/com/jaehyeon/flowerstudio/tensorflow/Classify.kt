//package com.jaehyeon.flowerstudio.tensorflow
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import android.os.Bundle
//import android.os.Parcelable
//import android.provider.MediaStore
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.jaehyeon.flowerstudio.R
//import org.tensorflow.lite.Interpreter
//import java.nio.ByteBuffer
//import java.nio.ByteOrder
//import java.util.*
//import kotlin.Comparator
//import kotlin.collections.ArrayList
//
//
//class Classify : AppCompatActivity() {
//    // options for model interpreter
//    private val tfliteOptions: Interpreter.Options = Interpreter.Options()
//
//    // tflite graph
//    private var tflite: Interpreter? = null
//
//    // holds all the possible labels for model
//    private var labelList: List<String>? = null
//
//    // holds the selected image data as bytes
//    private var imgData: ByteBuffer? = null
//
//    // holds the probabilities of each label for non-quantized graphs
//    private var labelProbArray: Array<FloatArray>? = null
//
//    // holds the probabilities of each label for quantized graphs
//    private var labelProbArrayB: Array<ByteArray>? = null
//
//    // array that holds the labels with the highest probabilities
//    private var topLables: Array<String?>? = null
//
//    // array that holds the highest probabilities
//    private var topConfidence: Array<String?>? = null
//
//    // selected classifier information received from extras
//    private var chosen: String? = null
//    private var quant = false
//
//    // input image dimensions for the Inception Model
//    private val DIM_IMG_SIZE_X = 299
//    private val DIM_IMG_SIZE_Y = 299
//    private val DIM_PIXEL_SIZE = 3
//
//    // int array to hold image data
//    private lateinit var intValues: IntArray
//
//    // activity elements
//    private var selected_image: ImageView? = null
//    private var classify_button: Button? = null
//    private var back_button: Button? = null
//    private var label1: TextView? = null
//    private var label2: TextView? = null
//    private var label3: TextView? = null
//    private var Confidence1: TextView? = null
//    private var Confidence2: TextView? = null
//    private var Confidence3: TextView? = null
//
//    // priority queue that will hold the top results from the CNN
//    private val sortedLabels: PriorityQueue<Map.Entry<String, Float>> =
//        PriorityQueue(
//            RESULTS_TO_SHOW,
//            Comparator<Map.Entry<String?, Float?>?> { o1, o2 -> o1?.value!!.compareTo(o2!!.value!!)})
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        // get all selected classifier data from classifiers
//        chosen = intent.getStringExtra("chosen") as String
//
//        // initialize array that holds image data
//        intValues = IntArray(DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y)
//        super.onCreate(savedInstanceState)
//
//        //initilize graph and labels
//        try {
//            tflite = Interpreter(loadModelFile(), tfliteOptions)
//            labelList = loadLabelList()
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//        }
//
//        // initialize byte array. The size depends if the input data needs to be quantized or not
//        imgData = ByteBuffer.allocateDirect(4 * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE)
//
//        imgData?.order(ByteOrder.nativeOrder())
//
//        // initialize probabilities array. The datatypes that array holds depends if the input data needs to be quantized or not
//        if (quant) {
//            labelProbArrayB =
//                Array(1) { ByteArray(labelList!!.size) }
//        } else {
//            labelProbArray =
//                Array(1) { FloatArray(labelList!!.size) }
//        }
//        setContentView(R.layout.activity_classify)
//
//        // labels that hold top three results of CNN
//        label1 = findViewById<View>(R.id.label1) as TextView
//        label2 = findViewById<View>(R.id.label2) as TextView
//        label3 = findViewById<View>(R.id.label3) as TextView
//        // displays the probabilities of top labels
//        Confidence1 = findViewById<View>(R.id.Confidence1) as TextView
//        Confidence2 = findViewById<View>(R.id.Confidence2) as TextView
//        Confidence3 = findViewById<View>(R.id.Confidence3) as TextView
//        // initialize imageView that displays selected image to the user
//        selected_image = findViewById<View>(R.id.selected_image) as ImageView
//
//        // initialize array to hold top labels
//        topLables = arrayOfNulls(RESULTS_TO_SHOW)
//        // initialize array to hold top probabilities
//        topConfidence = arrayOfNulls(RESULTS_TO_SHOW)
//
//        // allows user to go back to activity to select a different image
//        back_button = findViewById<View>(R.id.back_button) as Button
//        back_button.setOnClickListener(object : OnClickListener() {
//            fun onClick(view: View?) {
//                val i = Intent(this@Classify, ChooseModel::class.java)
//                startActivity(i)
//            }
//        })
//
//        // classify current dispalyed image
//        classify_button = findViewById<View>(R.id.classify_image) as Button
//        classify_button.setOnClickListener(object : OnClickListener() {
//            fun onClick(view: View?) {
//                // get current bitmap from imageView
//                val bitmap_orig =
//                    (selected_image.getDrawable() as BitmapDrawable).bitmap
//                // resize the bitmap to the required input size to the CNN
//                val bitmap = getResizedBitmap(bitmap_orig, DIM_IMG_SIZE_X, DIM_IMG_SIZE_Y)
//                // convert bitmap to byte array
//                convertBitmapToByteBuffer(bitmap)
//                // pass byte data to the graph
//
//                tflite.run(imgData, labelProbArray)
//
//                // display the results
//                printTopKLabels()
//            }
//        })
//
//        // get image from previous activity to show in the imageView
//        val uri: Uri = intent.getParcelableExtra<Parcelable>("resID_uri") as Uri
//        try {
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//            selected_image.setImageBitmap(bitmap)
//            // not sure why this happens, but without this the image appears on its side
//            selected_image.setRotation(selected_image.getRotation() + 90)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    // loads tflite grapg from file
//    @Throws(IOException::class)
//    private fun loadModelFile(): MappedByteBuffer {
//        val fileDescriptor = this.assets.openFd(chosen!!)
//        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
//        val fileChannel: FileChannel = inputStream.getChannel()
//        val startOffset = fileDescriptor.startOffset
//        val declaredLength = fileDescriptor.declaredLength
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
//    }
//
//    // converts bitmap to byte array which is passed in the tflite graph
//    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
//        if (imgData == null) {
//            return
//        }
//        imgData.rewind()
//        bitmap.getPixels(
//            intValues,
//            0,
//            bitmap.width,
//            0,
//            0,
//            bitmap.width,
//            bitmap.height
//        )
//        // loop through all pixels
//        var pixel = 0
//        for (i in 0 until DIM_IMG_SIZE_X) {
//            for (j in 0 until DIM_IMG_SIZE_Y) {
//                val `val` = intValues[pixel++]
//                // get rgb values from intValues where each int holds the rgb values for a pixel.
//                // if quantized, convert each rgb value to a byte, otherwise to a float
//                if (quant) {
//                    imgData.put((`val` shr 16 and 0xFF).toByte())
//                    imgData.put((`val` shr 8 and 0xFF).toByte())
//                    imgData.put((`val` and 0xFF).toByte())
//                } else {
//                    imgData.putFloat(((`val` shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    imgData.putFloat(((`val` shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                    imgData.putFloat(((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
//                }
//            }
//        }
//    }
//
//    // loads the labels from the label txt file in assets into a string array
//    @Throws(IOException::class)
//    private fun loadLabelList(): List<String> {
//        val labelList: MutableList<String> = ArrayList()
//        val reader =
//            BufferedReader(InputStreamReader(this.assets.open("labels.txt")))
//        var line: String
//        while (reader.readLine().also({ line = it }) != null) {
//            labelList.add(line)
//        }
//        reader.close()
//        return labelList
//    }
//
//    // print the top labels and respective confidences
//    private fun printTopKLabels() {
//        // add all results to priority queue
//        for (i in labelList!!.indices) {
//            if (quant) {
//                sortedLabels.add(
//                    SimpleEntry(labelList!![i], (labelProbArrayB!![0][i] and 0xff) / 255.0f)
//                )
//            } else {
//                sortedLabels.add(
//                    SimpleEntry(labelList!![i], labelProbArray!![0][i])
//                )
//            }
//            if (sortedLabels.size() > RESULTS_TO_SHOW) {
//                sortedLabels.poll()
//            }
//        }
//
//        // get top results from priority queue
//        val size: Int = sortedLabels.size()
//        for (i in 0 until size) {
//            val label: Map.Entry<String, Float> =
//                sortedLabels.poll()
//            topLables!![i] = label.key
//            topConfidence!![i] = String.format("%.0f%%", label.value * 100)
//        }
//
//        // set the corresponding textviews with the results
//        label1!!.text = "1. " + topLables!![2]
//        label2!!.text = "2. " + topLables!![1]
//        label3!!.text = "3. " + topLables!![0]
//        Confidence1!!.text = topConfidence!![2]
//        Confidence2!!.text = topConfidence!![1]
//        Confidence3!!.text = topConfidence!![0]
//    }
//
//    // resizes bitmap to given dimensions
//    fun getResizedBitmap(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
//        val width = bm.width
//        val height = bm.height
//        val scaleWidth = newWidth.toFloat() / width
//        val scaleHeight = newHeight.toFloat() / height
//        val matrix = Matrix()
//        matrix.postScale(scaleWidth, scaleHeight)
//        return Bitmap.createBitmap(
//            bm, 0, 0, width, height, matrix, false
//        )
//    }
//
//    companion object {
//        // presets for rgb conversion
//        const val RESULTS_TO_SHOW = 3
//        const val IMAGE_MEAN = 128
//        const val IMAGE_STD = 128.0f
//    }
//}