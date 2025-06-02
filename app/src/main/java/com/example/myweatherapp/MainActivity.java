import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText locationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationInput = findViewById(R.id.locationInput);
        Button checkWeatherBtn = findViewById(R.id.checkWeatherBtn);

        checkWeatherBtn.setOnClickListener(v -> {
            String location = locationInput.getText().toString().trim();
            if (!location.isEmpty()) {
                int stnId = getStnIdByLocation(location);
                if (stnId != -1) {
                    Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                    intent.putExtra("stnId", stnId);
                    intent.putExtra("location", location);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "해당 지역의 날씨 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 위치 이름을 기상청 stnId로 변환
    private int getStnIdByLocation(String location) {
        switch (location) {
            case "서울": return 108;
            case "부산": return 159;
            case "대전": return 133;
            case "광주": return 156;
            case "대구": return 143;
            case "울산": return 152;
            case "수원": return 119;
            case "춘천": return 101;
            case "강릉": return 105;
            case "청주": return 131;
            case "전주": return 146;
            case "제주": return 184;
            default: return -1;
        }
    }
}
