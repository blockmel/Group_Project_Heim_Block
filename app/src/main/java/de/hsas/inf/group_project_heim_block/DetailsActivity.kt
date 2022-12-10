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
import kotlin.collections.HashMap

class DetailsActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityDetailsBinding
    var accelerometerList : MutableList<HashMap<String, Float>> = mutableListOf()
    val db = Firebase.firestore
    lateinit var currentUser: String
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
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
                db.collection("Users").document(currentUser)
                    .set(userData)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            }
        }


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, sensor)
    }


    override fun onPostResume() {
        super.onPostResume()
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(accelerometerList.size <1000){
            if (p0 != null) {
                val data = hashMapOf(
                    "x" to p0.values[0],
                    "y" to p0.values[1],
                    "z" to p0.values[2]
                )
                accelerometerList.add(data)


                if (accelerometerList.size == 1000){
                    val accelerometerData = hashMapOf("accelerometer_data" to accelerometerList)
                    db.collection("Users").document(currentUser)
                        .set(accelerometerData, SetOptions.merge())
                        .addOnSuccessListener { Log.d(TAG, "accelerometerData added") }
                        .addOnFailureListener { Log.d(TAG, "accelerometerData not added") }
                    accelerometerList.clear()
                }
            }

        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}