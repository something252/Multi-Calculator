package end.multicalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;
    final int MENU_OPTIONS_ID = 3;

    EditText etInput1;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    Button btnMod;
    Button btnPow;

    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnZero;

    Button btnDot;
    Button btnPLeft;
    Button btnPRight;
    public static Button btnEquals;
    Button btnBackSpace;
    Button btnClear;

    TextView tvResult;
    TextView tvDebug;

    boolean numberCommasFlag;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       final Context ThisContext = this;

        // check if menu key exists (only above certain API) and change layout accordingly
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setContentView(R.layout.activity_main_with_menu);
            Button btnSettings = (Button) findViewById(R.id.btnSettings);
            btnSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenSettings();
                }
            });
        }
        else {
            if (ViewConfiguration.get(this).hasPermanentMenuKey() == true) {
                //this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
                setContentView(R.layout.activity_main);
            } else {
                setContentView(R.layout.activity_main_with_menu);
                Button btnSettings = (Button) findViewById(R.id.btnSettings);
                btnSettings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OpenSettings();
                    }
                });
            }
        }

        // find the elements
        etInput1 = (EditText) findViewById(R.id.etInput1);
        etInput1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyClipboardManager copy1 = new MyClipboardManager();
                copy1.copyToClipboard(ThisContext, etInput1.getText().toString());
                return true;
            }
        });

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnBackSpace = (Button) findViewById(R.id.btnBackSpace);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnZero = (Button) findViewById(R.id.btnZero);
        btnOne = (Button) findViewById(R.id.btnOne);
        btnTwo = (Button) findViewById(R.id.btnTwo);
        btnThree = (Button) findViewById(R.id.btnThree);
        btnFour = (Button) findViewById(R.id.btnFour);
        btnFive = (Button) findViewById(R.id.btnFive);
        btnSix = (Button) findViewById(R.id.btnSix);
        btnSeven = (Button) findViewById(R.id.btnSeven);
        btnEight = (Button) findViewById(R.id.btnEight);
        btnNine = (Button) findViewById(R.id.btnNine);
        btnMod = (Button) findViewById(R.id.btnMod);
        btnPow = (Button) findViewById(R.id.btnPow);
        btnPLeft = (Button) findViewById(R.id.btnPLeft);
        btnPRight = (Button) findViewById(R.id.btnPRight);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MyClipboardManager copy1 = new MyClipboardManager();
                copy1.copyToClipboard(ThisContext, tvResult.getText().toString());
                return true;
            }
        });
        //tvDebug =  (TextView) findViewById(R.id.tvDebug);

        // set a listener
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMod.setOnClickListener(this);
        btnPow.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnPLeft.setOnClickListener(this);
        btnPRight.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBackSpace.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);

        String lang = Locale.getDefault().getLanguage();
        if(lang.equals("de")) {
            btnClear.setTextSize(TypedValue.COMPLEX_UNIT_PT, 14); // shrink size down
        }// else if(lang.equals("es")) {
        //
        //} else if(lang.equals("ja")) {
        //
        //}

        //Locale current = getResources().getConfiguration().locale;
        NumberFormat nf = NumberFormat.getNumberInstance(getResources().getConfiguration().locale);
        //NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRENCH);
        String tmp1 = nf.format(1.1);
        if(tmp1.equals("1,1")) {
            numberCommasFlag = true;
        } else { // no commas in decimal numbers
            numberCommasFlag = false;
        }

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void OpenSettings() {
        Intent newIntent = new Intent(this, UserSettingActivity.class);
        //newIntent.putExtra("key", value); //Optional parameters
        startActivity(newIntent);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean autoCalc = sharedPrefs.getBoolean("prefAutoCalculate", true);

        switch (v.getId()) {
            case R.id.btnZero:
                etInput1.append("0"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnOne:
                etInput1.append("1"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnTwo:
                etInput1.append("2"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnThree:
                etInput1.append("3"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnFour:
                etInput1.append("4"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnFive:
                etInput1.append("5"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnSix:
                etInput1.append("6"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnSeven:
                etInput1.append("7"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnEight:
                etInput1.append("8"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnNine:
                etInput1.append("9"); VarJar.count++;
                if(autoCalc) onClick(btnEquals);
                break;
            case R.id.btnPLeft:
                if (etInput1.length() == 0) { // allow first char PLeft so dot check doesn't crash
                    etInput1.append("("); VarJar.PL_Count++; VarJar.count++;
                    if(autoCalc) onClick(btnEquals);
                } else { // else greater than 0 length
                    Editable b = etInput1.getText();
                    if(b.charAt(b.length()-1)!= '.') { // last char not '.'
                        etInput1.append("("); VarJar.PL_Count++; VarJar.count++;
                        if(autoCalc) onClick(btnEquals);
                    }
                }
                break;
            case R.id.btnClear:
                //etInput1.getText().clear();
                etInput1.setText("");
                tvResult.setText("");
                VarJar.PL_Count=0; VarJar.PR_Count=0; // reset parentheses counter
                VarJar.count++;	debugView();
                return;
            case R.id.btnBackSpace: VarJar.count++;
                Editable text = etInput1.getText();
                if (text.length() > 0) {
                    char a = text.charAt(text.length()-1);
                    if(a == '(')
                        VarJar.PL_Count--; // subtract one from parenthesis left counter
                    else if(a == ')')
                        VarJar.PR_Count--; // subtract one from parenthesis right counter

                    text.delete(etInput1.length() - 1, text.length());
                    etInput1.setSelection(etInput1.getText().length());

                    if (text.length() > 0) {
                        if(autoCalc) onClick(btnEquals);
                    } else { // length = 0 so clear result
                        onClick(btnClear);
                    }

                    debugView();
                }
                return;
            case R.id.btnDot:
                if (etInput1.length() > 0) {
                    Editable input = etInput1.getText();
                    for(int i=0; i < input.length(); i++) { // check (backspacing a symbol and then making a dot can make multiple dots)
                        char a = input.charAt(input.length()-1 - i); // check each char going backwards including current
                        if(a == '+' || a == '-' || a == '*' || a == '/' || a == '%' || a == '^' || a == '(' || a == ')') { VarJar.count++; debugView();
                            etInput1.append(getString(R.string.dot)); // did not previous encounter dot
                            if(autoCalc) onClick(btnEquals);
                            return;
                        } else if(a == getString(R.string.dot).charAt(0)) { debugView();
                            return; // found a dot previous dot before hitting a symbol so quit
                        }
                    }
                }
                etInput1.append(getString(R.string.dot)); // just numbers or otherwise harmless (hopefully)
                if(autoCalc) onClick(btnEquals);

                VarJar.count++; debugView();
                return;
            case R.id.btnSub:
                if (etInput1.length() > 0) {
                    Editable input = etInput1.getText();
                    char a = input.charAt(input.length()-1);
                    if(a != '.') {
                        etInput1.append("-"); VarJar.count++;
                        if(autoCalc) onClick(btnEquals);
                    }
                }
                else {
                    etInput1.append("-");
                    if(autoCalc) onClick(btnEquals);
                }
                VarJar.count++; debugView();
                break;
            default:
                break;
        }

        // check if operation symbol exists at end of input already and if etInput1 > 0
        if (etInput1.length() > 0) {
            Editable tmp = etInput1.getText();
            char character = tmp.charAt(tmp.length()-1); // set to last char
            if (character == '+' || character == '-' || character == '*' || character == '/'
                    || character == '.' || character == '%' || character == '^') {
                return; // prevent adding more than one consecutive symbol
            }
        }
        else
            return; // input field is empty

        // will cause crash if no prior "etInput1.length() > 0" check
        Editable tmp = etInput1.getText();
        char character = tmp.charAt(tmp.length()-1); // set to last char

        switch (v.getId()) {
            case R.id.btnPRight:
                if(character != '(' && VarJar.PL_Count > VarJar.PR_Count) {
                    etInput1.append(")"); VarJar.PR_Count++; VarJar.count++;
                    if(autoCalc) onClick(btnEquals);
                }
                break;
            case R.id.btnAdd:
                if (character != '(') {
                    etInput1.append("+"); VarJar.count++;
                }
                break;
            case R.id.btnMult:
                if(character != '(') {
                    etInput1.append("*"); VarJar.count++;
                }
                break;
            case R.id.btnDiv:
                if(character != '(') {
                    etInput1.append("/"); VarJar.count++;
                }
                break;
            case R.id.btnMod:
                if(character != '(') {
                    etInput1.append("%"); VarJar.count++;
                }
                break;
            case R.id.btnPow:
                if(character != '(') {
                    etInput1.append("^"); VarJar.count++;
                }
                break;
            case R.id.btnEquals:
                if(divByZero(etInput1.getText().toString())) { // divide by zero check
                    if(VarJar.PL_Count == VarJar.PR_Count) // all parentheses are closed/equal
                        SolveNow();
                    else if(character != '(' && ((VarJar.PL_Count - VarJar.PR_Count) == 1 || (VarJar.PL_Count - VarJar.PR_Count) == -1)) {
                        etInput1.append(")"); VarJar.PR_Count++;
                        SolveNow();
                        if(autoCalc) {
                            Editable text = etInput1.getText();
                            text.delete(etInput1.length() - 1, text.length());
                            etInput1.setSelection(etInput1.getText().length());
                            VarJar.PR_Count--;
                        }
                    }
                    else
                        tvResult.setText(getString(R.string.fix_parentheses)); // fix parentheses
                }
                else
                    tvResult.setText(getString(R.string.undefined)); // undefined
                return;
            default:
                break;
        }

        debugView();
    }

    /** divide by zero check (find "/0" string) (also mod by zero) */
    private boolean divByZero(String tmp) {
        for(int i=0; i < tmp.length()-1; i++) {
            // check each char going forwards from start
            if((tmp.charAt(i) == '/' || tmp.charAt(i) == '%') && (tmp.charAt(i + 1) == '0' || tmp.charAt(i + 1) == '.')) {
                if (i + 2 > tmp.length()-1) {
                    return false;
                } else {
                    int k=i+2;
                    for( ; k < tmp.length(); k++) {
                        if (tmp.charAt(k) != '0' && tmp.charAt(k) != '.' ) {
                            if(tmp.charAt(k) == '1' || tmp.charAt(k) == '2' || tmp.charAt(k) == '3' || tmp.charAt(k) == '4' ||
                                    tmp.charAt(k) == '5' || tmp.charAt(k) == '6' || tmp.charAt(k) == '7' || tmp.charAt(k) == '8' ||
                                    tmp.charAt(k) == '9') {
                                i = k - 1;
                                break;
                            } else return false; // confirm divide by zero scenario
                        }
                    }
                    if (k >= tmp.length()) {
                        return false; // confirm divide by zero scenario
                    }
                }
            }
        }
        return true; // no divide by zero found
    }

    /** method for initiating calculating of the current user input */
    private void SolveNow() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean normalResult = true;

        // check for end parentheses and number to insert multiplication [ex: (666)5 -> (666)*5]
        String inputTemp = etInput1.getText().toString();
        for(int i = 0; i < inputTemp.length(); i++) {
            if((inputTemp.charAt(i) == ')') && (i+1 < inputTemp.length()) ) {
                switch (inputTemp.charAt(i+1)) {
                    case '1': case '2': case '3': case '4':
                    case '5': case '6': case '7': case '8':
                    case '9': case '0': case '.': case ',':
                        inputTemp = new StringBuffer(inputTemp).insert(i+1, "*").toString();
                        i++;
                        break;
                }
            }
        }

        String solution;
        if(numberCommasFlag == false) {
            solution = EquationSolver.solver(inputTemp);
        } else {
            // temporarily use dot as decimal again
            solution = EquationSolver.solver(inputTemp.replace(',','.'));
        }

        if(solution.equals("Infinity")) {
            tvResult.setText(getString(R.string.Infinity));
            normalResult = false;
        } else {
            if (sharedPrefs.getBoolean("prefRoundingEnabled", true)) {
                boolean decimalFlag = false;
                for(int i = 0; i < solution.length() -1; i++) { // check if there is a decimal
                    if(solution.charAt(i) == '.') {
                        decimalFlag = true;
                        break;
                    }
                }
                if(decimalFlag) {
                    BigDecimal bd = new BigDecimal(solution);
                    int precision = Integer.parseInt(sharedPrefs.getString("prefRoundingPrecision", "2"));
                    switch (sharedPrefs.getString("prefRoundingMode", "Up")) {
                        case "Up": // RoundingMode.ROUND_UP
                            bd = bd.setScale(precision, RoundingMode.UP);
                            break;
                        case "Down": // RoundingMode.ROUND_DOWN
                            bd = bd.setScale(precision, RoundingMode.DOWN);
                            break;
                        case "Ceiling": // RoundingMode.ROUND_CEILING
                            bd = bd.setScale(precision, RoundingMode.CEILING);
                            break;
                        case "Floor": // RoundingMode.ROUND_FLOOR
                            bd = bd.setScale(precision, RoundingMode.FLOOR);
                            break;
                        case "Half Up": // RoundingMode.ROUND_HALF_UP
                            bd = bd.setScale(precision, RoundingMode.HALF_UP);
                            break;
                        case "Half Down": // RoundingMode.ROUND_HALF_DOWN
                            bd = bd.setScale(precision, RoundingMode.HALF_DOWN);
                            break;
                        case "Half Even": // RoundingMode.ROUND_HALF_EVEN
                            bd = bd.setScale(precision, RoundingMode.HALF_EVEN);
                            break;
                        default:
                            bd = bd.setScale(precision, RoundingMode.UNNECESSARY);
                            throw new IllegalArgumentException("Invalid rounding mode");
                            //break;
                    }
                    //String solution = Double.toString(bd.doubleValue());
                    solution = bd.toPlainString();
                }
            }
        }

        if(normalResult) {
            // prevent pointless "4.0" type outputs in favor of "4" instead
            if(solution.length() > 2 && solution.charAt(solution.length() - 1) == '0') {
                for(int i = solution.length() - 2; i >= 0; i--) {
                    char ch = solution.charAt(i);
                    if(ch != '0' && ch != '.' && ch != ',' || (i == 0)) {
                        if (!numberCommasFlag) tvResult.setText(solution);
                        else tvResult.setText(solution.replace('.',','));
                        break;
                    } else if(ch == '.' || ch == ',') {
                        tvResult.setText(solution.substring(0, i));
                        break;
                    }
                }
            } else {
                if (!numberCommasFlag) tvResult.setText(solution);
                else tvResult.setText(solution.replace('.',','));
            }
        }
    }

    /** method for debug purposes only */
    public void debugView() {
        //tvResult.setText("count =" + VarJar.count + ", PL =" + VarJar.PL_Count + ", PR =" + VarJar.PR_Count);
    }

    /** menu creation */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        //menu.add(0, MENU_RESET_ID, 0, "Reset");
        //menu.add(0, MENU_OPTIONS_ID, 0, getString(R.string.menu_settings));
        //menu.add(0, MENU_QUIT_ID, 0, "Quit");
        getMenuInflater().inflate(R.menu.menu_main, menu); // Inflate the menu resource
        return super.onCreateOptionsMenu(menu);
    }

    /** process menu item clicks */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            //case MENU_RESET_ID: // clear the input field
            //    etInput1.setText("");
            //    tvResult.setText("");
            //    return true;
            //case MENU_QUIT_ID: // exit the application
            //    finish();
            //    return true;
            case  R.id.menu_settings: // settings button
                OpenSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
     }
}