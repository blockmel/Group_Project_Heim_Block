package de.hsas.inf.group_project_heim_block

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
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
    var accelerometerArray: Array<HashMap<String, Float>> = arrayOf()
    val db = Firebase.firestore
    lateinit var currentUser: String
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentUser = intent.getStringExtra("studentID").toString()
        val currentUser = intent.getStringExtra("studentID")

        val data = currentUser?.let { db.collection("Users").document(it) }
        data?.get()?.addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                Log.d(TAG, "DocumentSnapshot data 2: ${document.data?.get("year")}")
                binding.nameEdit.setText(document.data?.get("name").toString())
                binding.courseEdit.setText(document.data?.get("course").toString())
                binding.yearEdit.setText(document.data?.get("year").toString())
            } else {
                Log.d(TAG, "No such document")
            }
        }?.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }



        binding.updateDetails.setOnClickListener {
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

        binding.goToRank.setOnClickListener {
            val intent = Intent(this, RankActivity::class.java)
            startActivity(intent)
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
        if(accelerometerArray.size <1000){
            if (p0 != null) {
                val accelerometerHelpArray = accelerometerArray
                val newAccelerometerDataArray = Array<HashMap<String, Float>>(1){hashMapOf("x" to p0.values[0], "y" to p0.values[1], "z" to p0.values[2])}
                val accelerometerHelpArrayLen = accelerometerHelpArray.size
                val newAccelerometerDataArrayLen = newAccelerometerDataArray.size
                accelerometerArray = Array<HashMap<String, Float>>(accelerometerHelpArrayLen + newAccelerometerDataArrayLen){hashMapOf("x" to 0F, "y" to 0F, "z" to 0F)}

                System.arraycopy(accelerometerHelpArray, 0, accelerometerArray, 0, accelerometerHelpArrayLen)
                System.arraycopy(newAccelerometerDataArray, 0, accelerometerArray, accelerometerHelpArrayLen, newAccelerometerDataArrayLen)


                if (accelerometerArray.size == 1000){
                    val accelerometerData = hashMapOf("accelerometer_data" to accelerometerArray.asList())
                    db.collection("Users").document(currentUser)
                        .set(accelerometerData, SetOptions.merge())
                        .addOnSuccessListener { Log.d(TAG, "accelerometerData added") }
                        .addOnFailureListener { Log.d(TAG, "accelerometerData not added") }
                    accelerometerArray = arrayOf()
                }
            }

        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}