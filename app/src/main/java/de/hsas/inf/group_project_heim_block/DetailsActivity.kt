package de.hsas.inf.group_project_heim_block

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hsas.inf.group_project_heim_block.databinding.ActivityDetailsBinding
import java.util.*

class DetailsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityDetailsBinding
    var accelerometerArray = arrayOf<AccelerometerData>()
    val db = Firebase.firestore
    lateinit var currentUser: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentUser = intent.getStringExtra("email").toString()
        binding.updateDetails.setOnClickListener {
            val currentUser = intent.getStringExtra("email")
            val name = binding.nameEdit.text.toString()
            val course = binding.courseEdit.text.toString()
            val year = binding.yearEdit.text.toString()

            val userData = hashMapOf(
                "name" to name,
                "course" to course,
                "year" to year
            )

            if (currentUser != null) {
                db.collection("users").document(currentUser)
                    .set(userData)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }
        }


        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        Log.d(TAG,"triggered")
        //TODO accelerometer data points

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0 != null) {
            Log.d(TAG, p0.values[0].toString())
        }

        if(accelerometerArray.size <10){
            Log.d(TAG, "accelerometerArray is ${accelerometerArray.size}")
            if (p0 != null) {
                Log.d(TAG, "accelerometerArray gets filled now")
                val addArray = arrayOf(AccelerometerData(p0.values[0], p0.values[1], p0.values[2]))
                val helpArray = accelerometerArray

                val addArrayLength = addArray.size
                val helpArrayLength = helpArray.size

                accelerometerArray = Array(addArrayLength + helpArrayLength) { AccelerometerData(0F,0F,0F) }
                System.arraycopy(helpArray, 0, accelerometerArray, 0, helpArrayLength)
                System.arraycopy(addArray, 0, accelerometerArray, helpArrayLength, addArrayLength)
//                accelerometerArray.plusElement(AccelerometerData(p0.values[0], p0.values[1], p0.values[2]))
//                accelerometerArray.plus(AccelerometerData(p0.values[0], p0.values[1], p0.values[2]))
                //TODO change to 1000
                if (accelerometerArray.size == 10){
                    Log.d(TAG, "accelerometerArray is 10: ${accelerometerArray.asList()}")
                    val data = hashMapOf("accelerometerData" to accelerometerArray.asList())
                    db.collection("users").document(currentUser)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener { Log.d(TAG, "accelerometerData added") }
                        .addOnFailureListener { Log.d(TAG, "accelerometerData not added") }
                    Log.d(TAG, "accelerometerArray is ready to get cleared")
                    accelerometerArray = arrayOf<AccelerometerData>()
                }
            }

        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}