package com.example.test1;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	public final char [] valid_chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', ' '};
	
	public final String SCALENE_TRIANGLE = "Scalene Triangle";
	public final String ISOSCELES_TRIANGLE = "Isosceles Triangle";
	public final String EQUILATERAL_TRIANGLE = "Equilateral Triangle";
	
	private final char COMMA = ',';
	private final char SPACE = ' ';
	private char DELIM = COMMA;
			
	String errMsg = "";
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tView = (TextView)findViewById(R.id.textView2);
        final EditText input_value = (EditText)findViewById(R.id.editText1);
        /*input_value.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				String value = s.toString();
				tView.setText(input_value.getText());
				for (int i=0; i<valid_chars.length; i++) {
				   //if (value.charAt(0))
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});*/
        input_value.setSingleLine(true);
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           
            	if (parseInput(input_value.getText().toString())) {
            		tView.setText(input_value.getText());
            	} else {
            		tView.setText(errMsg);
            	}
            	//tView.setText(input_value.getText());
                // Perform action on click
            }
        });
    }

    public boolean parseInput(String str) {
        String value = str.trim();
        errMsg = "";
        int commas = 0;
        int spaces = 0;
        for (int i=0; i<value.length(); i++) {
        	char c = value.charAt(i);
        	boolean bValid = false;
            for (int j=0; j<valid_chars.length; j++) {
                 if (c == valid_chars[j])	{
                	 bValid = true;
                	 break;
                 }
            }
            if (!bValid) {
            	errMsg = "Invalid character typed:" + c;
                System.out.println(errMsg);
                return false;
            }
            if (c == COMMA) {
            	commas++;
            }
            if (c == SPACE) {
            	spaces++;
            }
        }
        if (value.equals("0")) {
        	errMsg = "Application will exit!";
        	return false;
        }
        if (commas > 2) {
        	errMsg = "Invalid Input: Please provide exactly 3 integers separated by comma or space";
        	return false;
        } else if (commas > 0 && commas < 2) {
        	errMsg = "Invalid Input: Please provide exactly 3 integers separated by comma or space";
        	return false;
        } 
        
        System.out.println("commas = " + commas);
        
        if (commas != 2) {
        	if (spaces > 2) {
        		errMsg = "Invalid Input: Please provide exactly 3 integers separated by comma or space";
        		return false;
        	} else if (spaces > 0 && spaces < 2) {
        		errMsg = "Invalid Input: Please provide exactly 3 integers separated by comma or space";
        		return false;
        	}
        }
        if (commas == 2) {
        	DELIM = COMMA;
        } else if (spaces == 2) {
        	DELIM = SPACE;
        }
        
        int index = str.indexOf(DELIM);
        String side_1 = str.substring(0, index).trim();
        String side_2 = str.substring(index+1, str.indexOf(DELIM, index+1)).trim();
        String side_3 = str.substring(str.indexOf(DELIM, index+1)+1, str.length()).trim();
        errMsg = side_1 + "/" + side_2 + "/" + side_3;
        int side1 = Integer.valueOf(side_1);
        if (side1 < 1 || side1 > 100) {
        	errMsg = "Length of side one should be between 1 to 100";
        	return false;
        }
        int side2 = Integer.valueOf(side_2);
        if (side2 < 1 || side2 > 100) {
        	errMsg = "Length of side two should be between 1 to 100";
        	return false;
        }
        int side3 = Integer.valueOf(side_3);
        if (side3 < 1 || side3 > 100) {
        	errMsg = "Length of side three should be between 1 to 100";
        	return false;
        }
        if (! isValidTriangle(side1, side2, side3)) {
            return false;
        }
        determineTriangleType(side1, side2, side3);
        return false ;
    }
    
   /*
    * 
    */
   public boolean isValidTriangle(int side1, int side2, int side3) {
       if (side1 + side2 < side3) {
        	errMsg = "Invalid triangle: sum of side1 and side2 is less than side3: ( " + side1 + " + " + side2 + " ) " + " < " + side3;
         	return false;
       } else if (side2 + side3 < side1) {
        	errMsg = "Invalid triangle: sum of side2 and side3 is less than side1: ( " + side2 + " + " + side3 + " ) " + " < " + side1;
        	return false;
       } else if (side1 + side3 < side2) {
        	errMsg = "Invalid triangle: sum of side1 and side3 is less than side2: ( " + side1 + " + " + side3 + " ) " + " < " + side2;
        	return false;
       }
       return true;
   }
   
   /*
    * 
    */
   public String determineTriangleType(int a, int b, int c) {
	   String type = "";
	   if (a == b && b == c) {
		   type = EQUILATERAL_TRIANGLE;
	   } else if (a == b || b == c || a == c) {
		   type = ISOSCELES_TRIANGLE;
	   } else {
	       type = SCALENE_TRIANGLE;
	   }
	   errMsg = type;
	   return type;
   }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
