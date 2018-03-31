package cryptographer.tanat.com.cryptographer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText valueEditText;
    private EditText keyOneEditText;
    private EditText keyTwoEditText;
    private TextView encryptTextView;

    private Button encryptButton;
    private Button decipherButton;
    private Button goToUpButton;

    private LinearLayout decipherLayout;

    private String encryptText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    private void initView() {
        decipherLayout = (LinearLayout) findViewById(R.id.decipherLayout);

        valueEditText = (EditText) findViewById(R.id.valueEditText);
        keyOneEditText = (EditText) findViewById(R.id.keyOneEditText);
        keyTwoEditText = (EditText) findViewById(R.id.keyTwoEditText);
        encryptTextView = (TextView) findViewById(R.id.ecipherTextView);

        encryptButton = (Button) findViewById(R.id.encryptButton);
        decipherButton = (Button) findViewById(R.id.decipherButton);
        goToUpButton = (Button) findViewById(R.id.goToUpButton);

        encryptButton.setOnClickListener(encryptClickListener);
        decipherButton.setOnClickListener(dencryptClickListener);
        goToUpButton.setOnClickListener(goToUpClickListener);
    }

    View.OnClickListener encryptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            encrypt(valueEditText.getText().toString(), keyOneEditText.getText().toString(), keyTwoEditText.getText().toString());
            decipherLayout.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener dencryptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dencrypt(valueEditText.getText().toString(), keyOneEditText.getText().toString(), keyTwoEditText.getText().toString());
        }
    };

    View.OnClickListener goToUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valueEditText.setText(encryptTextView.getText().toString());
        }
    };

    public void encrypt(String value, String keyOne, String twoOne) {
        char[] keyOneChar = keyOne.toCharArray();
        char[] twoOneChar = twoOne.toCharArray();
        char[] valueChar = value.toCharArray();

        int[] keyOneInt = new int[keyOneChar.length];
        int[] keyTwoInt = new int[twoOneChar.length];
        int i = 0;
        for (char item : keyOneChar) {
            keyOneInt[i] = ((int) item) - 48;
            i++;
        }
        i = 0;
        for (char item : twoOneChar) {
            keyTwoInt[i] = ((int) item) - 48;
            i++;
        }

        char[][] matrix = new char[keyOneInt.length][keyTwoInt.length];

        i = 0;
        for (int x = 0; x < keyOneInt.length; x++) {
            for (int y = 0; y < keyTwoInt.length; y++) {
                if (valueChar.length > i) {
                    matrix[x][y] = valueChar[i];
                    i++;
                } else {
                    matrix[x][y] = (" ").charAt(0);
                }
            }
        }

        char[][] matrixEncryptForX = new char[keyOneInt.length][keyTwoInt.length];
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                matrixEncryptForX[x][keyOneInt[y] - 1] = matrix[x][y];
            }
        }

        char[][] matrixEncrypt = new char[keyOneInt.length][keyTwoInt.length];
        for (int y = 0; y < matrixEncryptForX.length; y++) {
            for (int x = 0; x < matrixEncryptForX[y].length; x++) {
                matrixEncrypt[keyTwoInt[x] - 1][y] = matrixEncryptForX[x][y];
            }
        }

        String encryptText = "";
        for (int x = 0; x < keyOneInt.length; x++) {
            for (int y = 0; y < keyTwoInt.length; y++) {
                encryptText = encryptText + matrixEncrypt[x][y];
            }
        }
        encryptTextView.setText(encryptText);
    }

    public void dencrypt(String value, String keyOne, String twoOne) {
        char[] keyOneChar = keyOne.toCharArray();
        char[] twoOneChar = twoOne.toCharArray();
        char[] valueChar = value.toCharArray();

        int[] keyOneInt = new int[keyOneChar.length];
        int[] keyTwoInt = new int[twoOneChar.length];
        int i = 0;
        for (char item : keyOneChar) {
            keyOneInt[i] = ((int) item) - 48;
            i++;
        }
        i = 0;
        for (char item : twoOneChar) {
            keyTwoInt[i] = ((int) item) - 48;
            i++;
        }

        char[][] matrix = new char[keyOneInt.length][keyTwoInt.length];

        i = 0;
        for (int x = 0; x < keyOneInt.length; x++) {
            for (int y = 0; y < keyTwoInt.length; y++) {
                if (valueChar.length > i) {
                    matrix[x][y] = valueChar[i];
                    i++;
                } else {
                    matrix[x][y] = (" ").charAt(0);
                }
            }
        }

        char[][] matrixEncryptForY = new char[keyOneInt.length][keyTwoInt.length];
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                matrixEncryptForY[x][y] = matrix[keyTwoInt[x] - 1][y];
            }
        }

        char[][] matrixEncrypt = new char[keyOneInt.length][keyTwoInt.length];
        for (int y = 0; y < matrixEncryptForY.length; y++) {
            for (int x = 0; x < matrixEncryptForY[y].length; x++) {
                matrixEncrypt[x][y] = matrixEncryptForY[x][keyOneInt[y] - 1];
            }
        }

        encryptText = "";
        for (int x = 0; x < keyOneInt.length; x++) {
            for (int y = 0; y < keyTwoInt.length; y++) {
                encryptText = encryptText + matrixEncrypt[x][y];
            }
        }
        encryptTextView.setText(encryptText);
    }
}
