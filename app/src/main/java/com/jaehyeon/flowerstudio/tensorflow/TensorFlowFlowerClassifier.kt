package com.jaehyeon.flowerstudio.tensorflow

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.graphics.Bitmap
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

class TensorFlowFlowerClassifier private constructor(
    assetManager: AssetManager,
    MODEL_PATH: String,
    LABEL_PATH: String,
    INPUT_SIZE: Int
) : Classifier {
    /*tensorflow*/
    private var interpreter: Interpreter?
    private val inputSize: Int
    private val labelList: List<String>
    override fun recognizeImage(bitmap: Bitmap): List<Classifier.Recognition> {
        val byteBuffer = convertBitmapToByteBuffer(bitmap)
        val result = Array(1) { FloatArray(labelList.size) }
        interpreter!!.run(byteBuffer, result)
        return getSortedResult(result)
    }

    override fun close() {
        interpreter!!.close()
        interpreter = null
    }

    @Throws(IOException::class)
    private fun loadModelFile(
        assetManager: AssetManager,
        MODEL_PATH: String
    ): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(MODEL_PATH)
        val inputStream =
            FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            startOffset,
            declaredLength
        )
    }

    @Throws(IOException::class)
    private fun loadLabelList(
        assetManager: AssetManager,
        LABEL_PATH: String
    ): List<String> {
        val labelList: MutableList<String> =
            ArrayList()
        val reader =
            BufferedReader(InputStreamReader(assetManager.open(LABEL_PATH)))
        var line: String
        while (reader.readLine().also { line = it } != null) {
            labelList.add(line)
        }
        reader.close()
        return labelList
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer =
            ByteBuffer.allocateDirect(BATCH_SIZE * inputSize * inputSize * PIXEL_SIZE * NumBytesPerChannel)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(inputSize * inputSize)
        bitmap.getPixels(
            intValues,
            0,
            bitmap.width,
            0,
            0,
            bitmap.width,
            bitmap.height
        )
        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val `val` = intValues[pixel++]
                byteBuffer.putFloat(((`val` shr 16 and 0xFF) - 128) / 128.0f)
                byteBuffer.putFloat(((`val` shr 8 and 0xFF) - 128) / 128.0f)
                byteBuffer.putFloat(((`val` and 0xFF) - 128) / 128.0f)
            }
        }
        return byteBuffer
    }

    @SuppressLint("DefaultLocale")
    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Classifier.Recognition> {
        val pq: PriorityQueue<Classifier.Recognition> = PriorityQueue(
            MAX_RESULTS,
            Comparator { lhs: Classifier.Recognition, rhs: Classifier.Recognition ->
                java.lang.Float.compare(
                    rhs.confidence,
                    lhs.confidence
                )
            })
        for (i in labelList.indices) {
            val confidence = labelProbArray[0][i]
            if (confidence > THRESHOLD) {
                pq.add(
                    Classifier.Recognition(
                        "" + i,
                        if (labelList.size > i) labelList[i] else "unknown",
                        confidence
                    )
                )
            }
        }
        val recognitions: ArrayList<Classifier.Recognition> = ArrayList<Classifier.Recognition>()
        val recognitionsSize =
            pq.size.coerceAtMost(MAX_RESULTS)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll()!!)
        }
        return recognitions
    }

    companion object {
        private const val TAG = "TENSORFLOW IMAGE CLASSIFIER"
        private const val MAX_RESULTS = 3 // Max cont of result
        private const val BATCH_SIZE = 1 // batch size : Google Inception V3 is 1
        private const val PIXEL_SIZE = 3 // pixel size : Google Inception V3 is 3
        private const val THRESHOLD = 0.1f //
        private const val NumBytesPerChannel = 4 // a 32bit float value requires 4 bytes
        private lateinit var tensorFlowClassifier: TensorFlowFlowerClassifier


//        private const val MODEL_PATH = "model.tflite"
//        private const val LABEL_PATH = "labels.txt"
//        private const val INPUT_SIZE = 299

        @Throws(IOException::class)
        fun createFlowerClassifier(
            assetManager: AssetManager,
            MODEL_PATH: String,
            LABEL_PATH: String,
            INPUT_SIZE: Int
        ) {
            tensorFlowClassifier =
                create(
                    assetManager,
                    MODEL_PATH,
                    LABEL_PATH,
                    INPUT_SIZE
                )
        }

        @Throws(IOException::class)
        fun create(
            assetManager: AssetManager,
            MODEL_PATH: String,
            LABEL_PATH: String,
            INPUT_SIZE: Int
        ): TensorFlowFlowerClassifier {
            return TensorFlowFlowerClassifier(assetManager, MODEL_PATH, LABEL_PATH, INPUT_SIZE)
        }

    }

    init {
        interpreter = Interpreter(
            loadModelFile(assetManager, MODEL_PATH),
            Interpreter.Options()
        )
        labelList = loadLabelList(assetManager, LABEL_PATH)
        this.inputSize = INPUT_SIZE
    }
}