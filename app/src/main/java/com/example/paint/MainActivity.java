package com.example.paint;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.paint.shapes.Circle;
import com.example.paint.shapes.Line;
import com.example.paint.shapes.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    DrawView drawView;
    List<Line> lines = new ArrayList<>();
    List<Point> points = new ArrayList<>();
    List<com.example.paint.shapes.Rect> rects = new ArrayList<>();
    List<Circle> circles = new ArrayList<>();
    EditText radius_width;
    EditText lineWidth;
    EditText height;
    Button colorLine;
    Button color;
    TextView label;
    int paintInt = Color.BLACK;
    boolean finishLine = false;
    float x;
    float y;
    Line line = new Line();
    Circle circle = new Circle();
    com.example.paint.shapes.Rect rect = new com.example.paint.shapes.Rect();
    String shape = "Line";
    String colorFor = "Color";
    Point point = new Point();
    TextView edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout layout = findViewById(R.id.paint);
        drawView = new DrawView(this);
        radius_width = findViewById(R.id.radius_width);
        lineWidth = findViewById(R.id.lineWidth);
        height = findViewById(R.id.height);
        color = findViewById(R.id.color);
        colorLine = findViewById(R.id.colorLine);
        edit = findViewById(R.id.edit);

        label = findViewById(R.id.label);
        layout.addView(drawView);
        radius_width.getText().toString();
        drawView.setOnTouchListener(this);


    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void save2(View view) {
        MediaStore.Images.Media.insertImage(getContentResolver(), drawView.getDrawingCache(), "gg", "gg");
    }


    public void save(View view) {



        drawView.setDrawingCacheEnabled(true);
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), drawView.getDrawingCache(),
                UUID.randomUUID().toString() + ".png", "drawing");
        if (imgSaved != null) {
            Toast savedToast = Toast.makeText(getApplicationContext(),
                    "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
            savedToast.show();
        } else {
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                    "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
            unsavedToast.show();
        }
        drawView.destroyDrawingCache();
    }


    public void clear(View view) {
        rects = new ArrayList<>();
        lines = new ArrayList<>();
        points = new ArrayList<>();
        circles = new ArrayList<>();
        drawView.invalidate();
    }

    public void switchLine(View view) {
        shape = "Line";
        edit.setText("Линия");
        label.setText("");
    }

    public void black(View view) {
        if (colorFor != "Color") {
            color.setBackgroundColor(Color.BLACK);
        } else {
            colorLine.setBackgroundColor(Color.BLACK);
        }
        paintInt = Color.BLACK;
    }

    public void yellow(View view) {
        if (colorFor != "Color") {
            color.setBackgroundColor(Color.YELLOW);
        } else {
            colorLine.setBackgroundColor(Color.YELLOW);
        }
        paintInt = Color.YELLOW;
    }

    public void red(View view) {
        if (colorFor != "Color") {
            color.setBackgroundColor(Color.RED);
        } else {
            colorLine.setBackgroundColor(Color.RED);
        }
        paintInt = Color.RED;
    }

    public void blue(View view) {


        if (colorFor != "Color") {
            color.setBackgroundColor(Color.BLUE);
        } else {
            colorLine.setBackgroundColor(Color.BLUE);
        }
        paintInt = Color.BLUE;
    }

    public void switchCircle(View view) {
        shape = "Circle";
        label.setText("Радиус");
        edit.setText("Круг");
    }

    public void switchRect(View view) {
        shape = "Rect";
        label.setText("Ширина Высота");
        edit.setText("Прямоугольник");
    }

    public void switchForColor(View view) {
        colorFor = "Color";
    }

    public void switchForColorLine(View view) {
        colorFor = "ColorLine";
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие


                switch (shape) {

                    case "Line":
                        try {
                            point.setxPos(x);
                            point.setyPos(y);
                            point.setThickness(Float.valueOf(lineWidth.getText().toString()));
                            point.setPaint(paintInt);

                            points.add(point);
                            point = new Point();

                            if (finishLine) {

                                line.setxPos2(x);
                                line.setyPos2(y);
                                line.setPaint(paintInt);
                                lines.add(line);
                                line.setThickness(Integer.valueOf(lineWidth.getText().toString()));
                                finishLine = false;
                                line = new Line();
                                System.out.println("2 " + x + " " + y);

                            } else {

                                line.setxPos1(x);
                                line.setyPos1(y);
                                System.out.println("1 " + x + " " + y);
                                finishLine = true;
                            }
                        } catch (Exception ex) {

                        }
                        break;


                    case "Circle":
                        try {
                            circle.setxPos(x);

                            circle.setyPos(y);
                            circle.setPaint(paintInt);
                            circle.setThickness(Float.valueOf(lineWidth.getText().toString()));
                            circle.setRadius(Float.valueOf(radius_width.getText().toString()));
                            circles.add(circle);
                            circle = new Circle();
                        } catch (Exception ex) {

                        }
                        break;

                    case "Rect":
                        try {
                            rect.setxPos1(x);
                            rect.setyPos1(y);
                            rect.setxPos2(x + Float.valueOf((radius_width.getText().toString())));
                            rect.setyPos2(y + Float.valueOf(height.getText().toString()));
                            rect.setPaint(paintInt);
                            rects.add(rect);
                            rect = new com.example.paint.shapes.Rect();

                        } catch (Exception ex) {

                        }
                        break;

                    default:
                        break;


                }


                drawView.invalidate();
                break;

            default:
                break;
        }
        System.out.println(x + " " + y);
        return true;
    }

    public class DrawView extends View {
        Canvas canvas = new Canvas();

        Rect r;

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            Paint p = new Paint();

            for (int i = 0; i < points.size(); i++) {
                p.setColor(points.get(i).getPaint());
                p.setStrokeWidth(points.get(i).getThickness());
                canvas.drawPoint(points.get(i).getxPos(), points.get(i).getyPos(), p);
            }

            for (int i = 0; i < lines.size(); i++) {
                p.setColor(lines.get(i).getPaint());
                p.setStrokeWidth(lines.get(i).getThickness());
                canvas.drawLine(lines.get(i).getxPos1(),
                        lines.get(i).getyPos1(), lines.get(i).getxPos2(),
                        lines.get(i).getyPos2(), p);

            }

            for (int i = 0; i < circles.size(); i++) {
                p.setColor(circles.get(i).getPaint());
                canvas.drawCircle(circles.get(i).getxPos(),
                        circles.get(i).getyPos(), circles.get(i).getRadius(),
                        p);

            }
            for (int i = 0; i < rects.size(); i++) {
                p.setColor(rects.get(i).getPaint());
                canvas.drawRect(rects.get(i).getxPos1(),
                        rects.get(i).getyPos1(), rects.get(i).getxPos2(), rects.get(i).getyPos2(),
                        p);

            }


        }

    }


}