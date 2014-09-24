package com.example.triangle;

import com.example.triangle.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Class for creating a user interface for Triangle App , to be run on an AVD emulator or any Android device 
 * @author kannanb
 *
 */
public class MainActivity extends Activity {

	public final char [] valid_chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', ' '};
	
	public final String SCALENE_TRIANGLE = "Scalene Triangle";
	public final String ISOSCELES_TRIANGLE = "Isosceles Triangle";
	public final String EQUILATERAL_TRIANGLE = "Equilateral Triangle";
	
	private final char COMMA = ',';
	private final char SPACE = ' ';
	private char DELIM = COMMA;
			
	String errMsg = "";
	View view = null;
	Canvas canvas = null;
	
	/**
	 * Method loading all the required UI resources from activity_main.xml 
	 * 
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tView = (TextView)findViewById(R.id.textView2);
        tView.setText("Result");
        final EditText input_value = (EditText)findViewById(R.id.editText1);
        
        input_value.setSingleLine(true);
        final Button validateButton = (Button) findViewById(R.id.button1);
        validateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           
            	if (parseInput(input_value.getText().toString())) {
            		tView.setText(input_value.getText());
            	} else {
            		tView.setText(errMsg);
            	}
            }
        });
        
        final Button clearButton = (Button) findViewById(R.id.Button01);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	input_value.setText("");
            }
        });
        /*view = (View) findViewById(R.id.vMain);
        canvas = new Canvas();
        draw(canvas);
        view.draw(canvas);*/
    }

    public void draw(Canvas c) {
        int x = view.getWidth();
        int y = view.getHeight();
        int radius;
        radius = 100;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawPaint(paint);
        // Use Color.parseColor to define HTML colors
        paint.setColor(Color.parseColor("#CD5C5C"));
        c.drawCircle(x / 2, y / 2, radius, paint);
    }
    
    /**
     * Method to parse the input string that should consist of 3 integers separated by comma or space
     * @param str input string
     * @return true if the string is in the correct format and in the expected range. false otherwise
     */
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
        	AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        	dialog.setTitle("Triangle App");
        	dialog.setMessage("Thanks for using TriangleApp. Goodbye!");
        	dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			});
        	dialog.create().show();
        	//System.exit(0);
        	return false;
        }

        if (commas == 0 && spaces == 0) {
        	errMsg = "Invalid Input: Please provide exactly 3 integers separated by comma or space";
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
    
   /**
    * Method to determine if a valid triangle can be formed with length of 3 sides given
    * @param side1 length of first side
    * @param side2 length of second side
    * @param side3 length of third side
    * @return true if the triangle is a valid one, false otherwise
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
   
   /**
    * Method to determine the type of a triangle given the length of each of its sides
    * @param length of first side
    * @param length of second side
    * @param length of third side
    * @return a string containing the triangle type (Equilateral or Isosceles or Scalene)
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
   
   public Point getIntersection(int x1, int y1, int r1, int x2, int y2, int r2) {
	   Point intersectPt = new Point();
	   int d = x2 - x1;
	   double d1 = (Math.pow(r1,  2) - Math.pow(r2, 2) + Math.pow(d, 2)) / 2 * d;
	   double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(d1,  2));
	   double x3 = x1 + (d1 * (x2 - x1)) / d;
	   double y3 = y1 + (d1 * (y2 - y1)) / d;
	   double x4_i = x3 + (h * (y2 - y1)) / d;
	   double y4_i = y3 - (h * (x2 - x1)) / d;
	   double x4_ii = x3 - (h * (y2 - y1)) / d;
	   double y4_ii = y3 + (h * (x2 - x1)) / d;
	   
	   if (y4_i > 0) {
		   intersectPt.x = (int)x4_i;
		   intersectPt.y = (int)y4_i;
	   } else {
		   intersectPt.x = (int)x4_ii;
		   intersectPt.y = (int)y4_ii;
	   }
 	   return intersectPt;
   }
   
   public void drawTriangle(int side1, int side2, int side3, Canvas g) {
	   Paint paint = new Paint();
	   paint.setColor(Color.RED);
	   g.drawLine(0, 0, side1, 0, paint);
	   Point pt = getIntersection(0, 0, side2, side1, 0, side3);
	   g.drawLine(0, 0, pt.x, pt.y, paint);
	   g.drawLine(side1, 0, pt.x, pt.y, paint);
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
    
    public class MyView extends View {
        public MyView(Context c) {
             super(c);
        }

        public void onDraw(Canvas c) {
           int x = getWidth();
           int y = getHeight();
           int radius;
           radius = 100;
           Paint paint = new Paint();
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           c.drawPaint(paint);
           // Use Color.parseColor to define HTML colors
           paint.setColor(Color.parseColor("#CD5C5C"));
           c.drawCircle(x / 2, y / 2, radius, paint);
       }
    }
}
