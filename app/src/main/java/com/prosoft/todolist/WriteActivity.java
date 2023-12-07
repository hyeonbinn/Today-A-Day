package com.prosoft.todolist;//package com.prosoft.todolist;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import androidx.appcompat.app.AppCompatDelegate;
//
//
//public class WriteActivity extends AppCompatActivity {
//
//    TextView writeDay;
//    EditText edtToDoList;
//
//    String fileName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // 추가
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.active_write);
//        setTitle("할 일 작성");
//
//        String year, monthday;
//        String fnames = "";
//
//        edtToDoList = findViewById(R.id.edtDiary);
//
//        writeDay = findViewById(R.id.writeDay);
//        Intent intent = getIntent();
//        year = intent.getStringExtra("years");
//        monthday = intent.getStringExtra("monthdays");
//        writeDay.setText(year + " " + monthday);
//        // 파일 이름을 그대로 사용하도록 수정
//        fileName = writeDay.getText().toString() + ".txt";
//        String str = readToDoList(fileName);
//        edtToDoList.setText(str);
//    }
//
//    String readToDoList(String fName) {
//        String ToDoListStr = null;
//        FileInputStream inFs;
//        try {
//            inFs = openFileInput(fName);
//            byte[] txt = new byte[inFs.available()];
//            inFs.read(txt);
//
//            ToDoListStr = new String(txt).trim();
//            inFs.close();
//            if (ToDoListStr.equals("")) {
//                // 할 일이 없을 때
//            } else {
//                // 할 일이 있을 때
//                setTitle("할 일 수정");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ToDoListStr;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater mInflater = getMenuInflater();
//        mInflater.inflate(R.menu.save_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.saveMenu:
//                saveDiary(fileName);
//                return true;
//        }
//        return false;
//    }
//
//    // 할 일 저장하는 메소드
//    private void saveDiary(String fileName) {
//        FileOutputStream fos = null;
//
//        try {
//            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
//            String content = edtToDoList.getText().toString();
//
//            fos.write(content.getBytes());
//            fos.flush();
//            fos.close();
//
//            Toast.makeText(getApplicationContext(), "할 일을 저장했습니다.", Toast.LENGTH_SHORT).show();
//            // 현재 액티비티를 종료하고 이전 액티비티로 돌아가도록 수정
//            finish();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "오류가 발생하였습니다!", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

//import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {

    TextView writeDay;
    EditText edtToDoList;
    CheckBox checkBoxTodo; // 체크박스 참조 변수 추가

    String fileName;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // 추가
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_write);
        setTitle("할 일 작성");

        String year, monthday;

        edtToDoList = findViewById(R.id.edtDiary);
        checkBoxTodo = findViewById(R.id.checkBoxTodo); // 체크박스 참조 변수 초기화

        writeDay = findViewById(R.id.writeDay);
        Intent intent = getIntent();
        year = intent.getStringExtra("years");
        monthday = intent.getStringExtra("monthdays");
        writeDay.setText(year + " " + monthday);
        // 파일 이름을 그대로 사용하도록 수정
        fileName = writeDay.getText().toString() + ".txt";
        String str = readToDoList(fileName);
        edtToDoList.setText(str);
    }

    String readToDoList(String fName) {
        String ToDoListStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[inFs.available()];
            inFs.read(txt);

            ToDoListStr = new String(txt).trim();
            inFs.close();
            if (ToDoListStr.equals("")) {
                // 할 일이 없을 때
                checkBoxTodo.setChecked(false); // 체크박스를 체크하지 않은 상태로 설정
            } else {
                // 할 일이 있을 때
                checkBoxTodo.setChecked(true); // 체크박스를 체크한 상태로 설정
                setTitle("할 일 수정");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ToDoListStr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveMenu:
                saveDiary(fileName);
                return true;
        }
        return false;
    }

    // 할 일 저장하는 메소드
    private void saveDiary(String fileName) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            String content = edtToDoList.getText().toString();

            fos.write(content.getBytes());
            fos.flush();
            fos.close();

            Toast.makeText(getApplicationContext(), "할 일을 저장했습니다.", Toast.LENGTH_SHORT).show();
            // 현재 액티비티를 종료하고 이전 액티비티로 돌아가도록 수정
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류가 발생하였습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}