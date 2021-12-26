package livevasiliy.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;


public class MainActivity extends AppCompatActivity {
    EditText editText=findViewById(R.id.word);
    TextView tran=findViewById(R.id.text);
    Button button=findViewById(R.id.button);
    public static final int nouns = -1800001;
    public static final int verbs = -1800000;
    public static final int adjectives = -1800002;
    public static String excepted;
    public HashMap<String,String> words=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excepted=editText.getText().toString();
                tran.setText(words.get(excepted));
            }
        });

        String data = "";
        try {
            data = readFile(nouns);
            readCsv(data);
            data=readFile(adjectives);
            readCsv(data);
            data=readFile(verbs);
            readCsv(data);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Cant read this", Toast.LENGTH_LONG);
        }

    }
    String readFile(int file) throws IOException{
        InputStream inputStream=getResources().openRawResource(file);
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder=new StringBuilder();
        String line=bufferedReader.readLine();
        while (line!=null){
            builder.append(line).append("\n");
            line=bufferedReader.readLine();
        }
        return builder.toString();
    }
    void readCsv(String data){
        CsvReader reader=CsvReader.builder().fieldSeparator('\t').build(data);

        for(CsvRow csvRow:reader){
            if (csvRow.getFieldCount()>=3){
                String bare=csvRow.getField(0);
                String translitions=csvRow.getField(3);
                words.put(bare,translitions);
            }
        }
        System.out.println(words);
    }

}

