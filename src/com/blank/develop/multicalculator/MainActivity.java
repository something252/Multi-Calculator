package com.blank.develop.multicalculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	  final int MENU_RESET_ID = 1;
	  final int MENU_QUIT_ID = 2;

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
	  Button btnEquals;
	  Button btnBackSpace;
	  Button btnClear;

	  TextView tvResult;
	  TextView tvDebug;

	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);

	    // find the elements
	    etInput1 = (EditText) findViewById(R.id.etInput1);

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
	    tvDebug =  (TextView) findViewById(R.id.tvDebug);

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
	  }
	  
	  @Override
	  public void onClick(View v) {
	    // TODO Auto-generated method stub
	    
	    switch (v.getId()) {
	    case R.id.btnZero:
	    	etInput1.append("0"); VarJar.count++;
	    	break;
	    case R.id.btnOne:
	    	etInput1.append("1"); VarJar.count++;
	    	break;
	    case R.id.btnTwo:
	    	etInput1.append("2"); VarJar.count++;
	    	break;
	    case R.id.btnThree:
	    	etInput1.append("3"); VarJar.count++;
	    	break;
	    case R.id.btnFour:
	    	etInput1.append("4"); VarJar.count++;
	    	break;
	    case R.id.btnFive:
	    	etInput1.append("5"); VarJar.count++;
	    	break;
	    case R.id.btnSix:
	    	etInput1.append("6"); VarJar.count++;
	    	break;
	    case R.id.btnSeven:
	    	etInput1.append("7"); VarJar.count++;
	    	break;
	    case R.id.btnEight:
	    	etInput1.append("8"); VarJar.count++;
	    	break;
	    case R.id.btnNine:
	    	etInput1.append("9"); VarJar.count++;
	    	break;
	    case R.id.btnPLeft:
	    	if (etInput1.length() == 0) { // allow first char PLeft so dot check doesn't crash
	    		etInput1.append("("); VarJar.PL_Count++; VarJar.count++;
	    	}
	    	else { // else greater than 0 length
		    	Editable b = etInput1.getText();
		    	if(b.charAt(b.length()-1)!= '.') { // last char not '.'
		    	etInput1.append("("); VarJar.PL_Count++; VarJar.count++;
		    	}
	    	}
	    	break;
	    case R.id.btnClear:
	    	if (etInput1.length() > 0) {	VarJar.count++;	debugView();
	    		etInput1.getText().clear();
	    		tvResult.setText("");
	    		VarJar.PL_Count=0; VarJar.PR_Count=0; // reset parentheses counter
	    	debugView();}
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
	    		//etInput1.setText(text);
	    		etInput1.setSelection(etInput1.getText().length());  
	    	debugView();}
	    	return;
	    case R.id.btnDot:
	    	if (etInput1.length() > 0) {
	    	    Editable input = etInput1.getText();
	    	    char a = input.charAt(input.length()-1);
	    		for(int i=0; i < input.length(); i++) { // check (backspacing a symbol and then making a dot can make multiple dots)
	    			a = input.charAt(input.length()-1 - i); // check each char going backwards including current
	    			if(a == '+' || a == '-' || a == '*' || a == '/'
	    			|| a == '%' || a == '^' || a == '(' || a == ')') { VarJar.count++; debugView();
	    				etInput1.append("."); // did not previous encounter dot
	    				return;
	    			}
	    			else if(a == '.') {debugView();
	    				return; // found a dot previous dot before hitting a symbol so quit
	    			}
	    		}
	    		etInput1.append("."); // just numbers or otherwise harmless (hopefully)
	    	}
	    	else
	    		etInput1.append("."); 
	    	VarJar.count++; debugView();
    		return;
	    case R.id.btnSub:
	    	if (etInput1.length() > 0) {
	    	    Editable input = etInput1.getText();
	    	    char a = input.charAt(input.length()-1);
		    	if(a != '.') {
		    		etInput1.append("-"); VarJar.count++;
		    	}
		    }
	    	else
	    		etInput1.append("-");
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
	    		}
		    	break;
		    case R.id.btnAdd:
		    	if(character != '(') {
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
			    		tvResult.setText("= " + EquationSolver.solver(etInput1.getText().toString()) );
			    	else if(character != '(' && ((VarJar.PL_Count - VarJar.PR_Count) == 1 || (VarJar.PL_Count - VarJar.PR_Count) == -1)) {
			    		etInput1.append(")"); VarJar.PR_Count++;
			    		tvResult.setText("= " + EquationSolver.solver(etInput1.getText().toString()) );
			    	}
			    	else
			    		tvResult.setText("Fix Parentheses");
		    	}
		    	else
		    		tvResult.setText("Division By Zero");
		    	return;
		    default:
		    	break;
	    }
	    
	    debugView();
	  }
	  
	  /** divide by zero check (find "/0" string) */
	  private boolean divByZero(String tmp) {
		  for(int i=0; i < tmp.length(); i++) {
				char character = tmp.charAt(tmp.length()-1 - i); // check each char going backwards from current
			 	if(character == '/' && tmp.charAt(tmp.length()-0 - i) == '0') {
			 		return false; // confirm divide by zero scenario
			 	}
		  }
		  return true; // no divide by zero found
	  }
	  
	  /** method for debug purposes only */
	  public void debugView() {
		  //tvResult.setText("count =" + VarJar.count + ", PL =" + VarJar.PL_Count + ", PR =" + VarJar.PR_Count);
	  }

	/** menu creation */
	  @Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    // TODO Auto-generated method stub
	    menu.add(0, MENU_RESET_ID, 0, "Reset");
	    menu.add(0, MENU_QUIT_ID, 0, "Quit");
	    return super.onCreateOptionsMenu(menu);
	  }

	  /** process menu item clicks */
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    // TODO Auto-generated method stub
	    switch (item.getItemId()) {
	    case MENU_RESET_ID: // clear the input field
	      etInput1.setText("");
	      tvResult.setText("");
	      break;
	    case MENU_QUIT_ID: // exit the application
	      finish();
	      break;
	    }
	    return super.onOptionsItemSelected(item);
	  }
	  
}