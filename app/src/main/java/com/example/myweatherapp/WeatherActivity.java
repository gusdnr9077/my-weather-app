import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    private TextView weatherText;
    private ImageView clothesImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherText = findViewById(R.id.weatherText);
        clothesImage = findViewById(R.id.clothesImage);

        Intent intent = getIntent();
        int stnId = intent.getIntExtra("stnId", -1);

        if (stnId != -1) {
            WeatherApiHelper.getWeatherData(stnId, (weatherInfo, avgTemp) -> {
                runOnUiThread(() -> {
                    weatherText.setText(weatherInfo);
                    updateClothingImage(avgTemp);
                });
            });
        }
    }

    private void updateClothingImage(double temp) {
        if (temp >= 26) {
            clothesImage.setImageResource(R.drawable.summer);
        } else if (temp >= 20) {
            clothesImage.setImageResource(R.drawable.spring);
        } else if (temp >= 10) {
            clothesImage.setImageResource(R.drawable.fall);
        } else {
            clothesImage.setImageResource(R.drawable.winter);
        }
    }
}
