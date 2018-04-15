package cryptographer.tanat.com.cryptographer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {
    private EditText valueEditText;
    private EditText keyOneEditText;
    private EditText keyTwoEditText;
    private TextView encryptTextView;

    private Button encryptButton;
    private Button decipherButton;
    private Button goToUpButton;

    private LinearLayout decipherLayout;
    private LinearLayout reshuffleLayout;
    private LinearLayout caesarLayout;
    private LinearLayout desLayout;
    private LinearLayout decipherCaesarLayout;
    private LinearLayout decipherDesLayout;

    private String encryptText;

    private EditText valueCaesarEditText;
    private EditText keyCaesarEditText;
    private TextView ecipherCaesarTextView;

    private Button caesarEncriptButton;
    private Button caesarDecipherButton;
    private Button goToUpCaesarButton;

    private String encryptCaesarText;

    private EditText valueDesEditText;
    private EditText keyDesEditText;
    private TextView ecipherDesTextView;

    private Button desEncriptButton;
    private Button desDecipherButton;
    private Button goToUpDesButton;

    private Cipher desCipher;
    private SecretKey myDesKey;
    private byte[] textEncrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_caesar) {
            caesar();
            return true;
        } else if (id == R.id.action_reshuffle) {
            reshuffle();
            return true;
        } else if (id == R.id.action_des) {
            des();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        // for encrypt reshuffle
        decipherLayout = (LinearLayout) findViewById(R.id.decipherLayout);
        reshuffleLayout = (LinearLayout) findViewById(R.id.reshuffleLayout);
        caesarLayout = (LinearLayout) findViewById(R.id.caesarLayout);
        desLayout = (LinearLayout) findViewById(R.id.desLayout);

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

        // for encrypt Caesar
        decipherCaesarLayout = (LinearLayout) findViewById(R.id.decipherCaesarLayout);

        valueCaesarEditText = (EditText) findViewById(R.id.valueCaesarEditText);
        keyCaesarEditText = (EditText) findViewById(R.id.keyCaesarEditText);
        ecipherCaesarTextView = (TextView) findViewById(R.id.ecipherCaesarTextView);

        caesarEncriptButton = (Button) findViewById(R.id.caesarEncriptButton);
        caesarDecipherButton = (Button) findViewById(R.id.caesarDecipherButton);
        goToUpCaesarButton = (Button) findViewById(R.id.goToUpCaesarButton);

        caesarEncriptButton.setOnClickListener(caesarEncriptClickListener);
        caesarDecipherButton.setOnClickListener(caesarDecipherClickListener);
        goToUpCaesarButton.setOnClickListener(goToUpCaesarClickListener);

        // for encrypt DES
        decipherDesLayout = (LinearLayout) findViewById(R.id.decipherDesLayout);

        valueDesEditText = (EditText) findViewById(R.id.valueDesEditText);
        keyDesEditText = (EditText) findViewById(R.id.keyDesEditText);
        ecipherDesTextView = (TextView) findViewById(R.id.ecipherDesTextView);

        desEncriptButton = (Button) findViewById(R.id.desEncriptButton);
        desDecipherButton = (Button) findViewById(R.id.desDecipherButton);
        goToUpDesButton = (Button) findViewById(R.id.goToUpDesButton);

        desEncriptButton.setOnClickListener(desEncriptClickListener);
        desDecipherButton.setOnClickListener(desDecipherClickListener);
        goToUpDesButton.setOnClickListener(goToUpDesClickListener);
    }

    View.OnClickListener encryptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            encryptReshuffle(valueEditText.getText().toString(), keyOneEditText.getText().toString(), keyTwoEditText.getText().toString());
            decipherLayout.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener dencryptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dencryptReshuffle(valueEditText.getText().toString(), keyOneEditText.getText().toString(), keyTwoEditText.getText().toString());
        }
    };

    View.OnClickListener goToUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valueEditText.setText(encryptTextView.getText().toString());
        }
    };

    View.OnClickListener caesarEncriptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            encryptCaesar(valueCaesarEditText.getText().toString(), Integer.parseInt(keyCaesarEditText.getText().toString()));
            decipherCaesarLayout.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener caesarDecipherClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dencryptCaesar(valueCaesarEditText.getText().toString(), Integer.parseInt(keyCaesarEditText.getText().toString()));
        }
    };

    View.OnClickListener goToUpCaesarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valueCaesarEditText.setText(ecipherCaesarTextView.getText().toString());
        }
    };

    View.OnClickListener desEncriptClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            encryptDes(valueDesEditText.getText().toString());
            decipherDesLayout.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener desDecipherClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dencryptDes();
        }
    };

    View.OnClickListener goToUpDesClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            valueDesEditText.setText(ecipherDesTextView.getText().toString());
        }
    };

    public void encryptReshuffle(String value, String keyOne, String twoOne) {
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

    public void dencryptReshuffle(String value, String keyOne, String twoOne) {
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

    public void encryptCaesar(String value, int keyOne) {
        char[] valueChar = value.toLowerCase().toCharArray();
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя ";
        char[] alphabetChar = alphabet.toCharArray();
        encryptCaesarText = "";

        for (char symSl : valueChar) {
            for (int i = 0; i < alphabetChar.length; i++) {
                if (String.valueOf(symSl).equals(String.valueOf(alphabetChar[i]))) {
                    String newSym;
                    if (i + keyOne >= alphabetChar.length) {
                        newSym = String.valueOf(alphabetChar[(i + keyOne) - alphabetChar.length]);
                    } else {
                        newSym = String.valueOf(alphabetChar[i + keyOne]);
                    }
                    encryptCaesarText = encryptCaesarText + newSym;
                }
            }
        }

        ecipherCaesarTextView.setText(encryptCaesarText);
    }

    public void dencryptCaesar(String value, int keyOne) {
        char[] valueChar = value.toLowerCase().toCharArray();
        String alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя ";
        char[] alphabetChar = alphabet.toCharArray();
        encryptCaesarText = "";

        for (char symSl : valueChar) {
            for (int i = 0; i < alphabetChar.length; i++) {
                if (String.valueOf(symSl).equals(String.valueOf(alphabetChar[i]))) {
                    String newSym;
                    if (i - keyOne >= 0) {
                        newSym = String.valueOf(alphabetChar[i - keyOne]);
                    } else {
                        newSym = String.valueOf(alphabetChar[alphabetChar.length + (i - keyOne)]);
                    }
                    encryptCaesarText = encryptCaesarText + newSym;
                }
            }
        }

        ecipherCaesarTextView.setText(encryptCaesarText);
    }

    public void encryptDes(String value) {
        byte[] text = value.getBytes();
        textEncrypted = new byte[0];
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            myDesKey = keygenerator.generateKey();
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            textEncrypted = desCipher.doFinal(text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        keyDesEditText.setText("" + myDesKey.getEncoded());
        ecipherDesTextView.setText("" + textEncrypted);
    }

    public void dencryptDes() {
        byte[] textDecrypted = null;
        try {
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            textDecrypted = desCipher.doFinal(textEncrypted);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        ecipherDesTextView.setText(new String(textDecrypted));
    }

    public void reshuffle() {
        reshuffleLayout.setVisibility(View.VISIBLE);
        caesarLayout.setVisibility(View.GONE);
        desLayout.setVisibility(View.GONE);
    }

    public void caesar() {
        reshuffleLayout.setVisibility(View.GONE);
        caesarLayout.setVisibility(View.VISIBLE);
        desLayout.setVisibility(View.GONE);
    }

    public void des() {
        reshuffleLayout.setVisibility(View.GONE);
        caesarLayout.setVisibility(View.GONE);
        desLayout.setVisibility(View.VISIBLE);
    }
}
