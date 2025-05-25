package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.*
import com.airbnb.lottie.LottieAnimationView
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        fetchWeatherData("Patna")
        searchCity()
    }

    private fun searchCity() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Trim the query to remove leading and trailing spaces
                val trimmedQuery = query?.trim()
                if (!trimmedQuery.isNullOrEmpty()) {
                    fetchWeatherData(trimmedQuery)  // Use the trimmed query
                } else {
                    Log.e("MainActivity", "Search query is empty or just spaces.")
                    Toast.makeText(this@MainActivity, "Please enter a valid city name.", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun fetchWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val response = retrofit.getWeatherData(cityName, "8fcfabb4d6124e61e82794fd31359eed", "metric")
        response.enqueue(object : Callback<weatherApp> {
            override fun onResponse(call: Call<weatherApp>, response: Response<weatherApp>) {
                val responseBody = response.body()!!
                if (response.isSuccessful && response.body() != null) {

                    val temperature = responseBody.main.temp.toString()
                    val humidity = responseBody.main.humidity.toString();
                    val windSpeed = responseBody.wind.speed.toString();
                    val sunRise = responseBody.sys.sunrise.toString().toLong()
                    val sunset = responseBody.sys.sunset.toString().toLong()
                    val seaLavel = responseBody.main.pressure.toString();
                    val condition = responseBody.weather.firstOrNull()?.main?:"unknown".toString();
                    val maxTemp = responseBody.main.temp_max.toString();
                    val minTemp = responseBody.main.temp_min.toString();

                    binding.tempratuer.text= "$temperature °C"
                    binding.weather.text = condition;
                    binding.maxtemp.text = "Max Temp: $maxTemp °C";
                    binding.mintemp.text = "Min Temp: $minTemp °C";
                    binding.humadity.text = "$humidity %";
                    binding.speed.text = "$windSpeed m/s";
                    binding.sunrise.text = "${time(sunRise)}"
                    binding.sunset.text = "${time(sunset)}"
                    binding.sea.text = "$seaLavel"
                    binding.sunny.text = condition
                    binding.day.text=dayName(System.currentTimeMillis())
                    binding.date.text=date()
                    binding.cityname.text="$cityName"


//                    Log.d(TAG, "Temperature: $temperature°C")  // Corrected TAG usage

                    changeImagesAccordingTOWeather(condition)
                }
            }

            override fun onFailure(call: Call<weatherApp>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data: ${t.message}")  // Corrected TAG usage
            }
        })

    }

    fun changeImagesAccordingTOWeather(condition: String) {
        when(condition){
            "Clear Sky", "Sunny", "Clear" ->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.weatherAnimation.setAnimation(R.raw.sun)
            }
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Foggy" ->{
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.weatherAnimation.setAnimation(R.raw.cloud)
            }
            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" ->{
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.weatherAnimation.setAnimation(R.raw.rain)
            }
            "Light snow", "Heavy snow", "Moderate snow", "Blizzard" ->{
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.weatherAnimation.setAnimation(R.raw.snow)
            }
            else->{
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.weatherAnimation.setAnimation(R.raw.sun)
            }
        }
        binding.weatherAnimation.playAnimation()
    }

    fun date():String{
        val sdf = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())
        return sdf.format(Date())
    }
    fun time(timestamp: Long):String{
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp*1000))
    }

    fun dayName(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timestamp)) // Use the timestamp to create a Date object
    }

}
