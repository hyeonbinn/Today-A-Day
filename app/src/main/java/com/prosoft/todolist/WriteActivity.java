package com.prosoft.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {

    TextView writeDay;
    EditText feedback;
    LinearLayout checkListLayout;

    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_write);
        setTitle("할 일 작성");

        String year, monthday;

        feedback = findViewById(R.id.feedback);
        checkListLayout = findViewById(R.id.checkListLayout);

        writeDay = findViewById(R.id.writeDay);
        Intent intent = getIntent();
        year = intent.getStringExtra("years");
        monthday = intent.getStringExtra("monthdays");
        writeDay.setText(year + " " + monthday);
        fileName = writeDay.getText().toString() + ".txt";
        String str = readToDoList(fileName);
        feedback.setText(str);

        // 할 일을 동적으로 추가
        findViewById(R.id.btnAddTodo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });
    }

    private void addTodo() {
        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        taskLayout.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox checkBox = new CheckBox(this);
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        taskLayout.addView(checkBox);

        // EditText 추가
        EditText todoEditText = new EditText(this);
        todoEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        todoEditText.setHint("할 일을 입력하세요.");
        todoEditText.setTextSize(20);
        taskLayout.addView(todoEditText);

        // 생성된 레이아웃을 기존의 할 일 목록 레이아웃에 추가
        checkListLayout.addView(taskLayout);
    }

    private String readToDoList(String fName) {
        String ToDoListStr = null;
        FileInputStream inFs;
        try {
            inFs = openFileInput(fName);
            byte[] txt = new byte[inFs.available()];
            inFs.read(txt);

            ToDoListStr = new String(txt).trim();
            inFs.close();

            // 파일에서 읽은 내용을 기반으로 체크박스 상태 및 작업 텍스트를 설정
            String[] lines = ToDoListStr.split("\n");
            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String isChecked = parts[0];
                    String task = parts[1];

                    LinearLayout taskLayout = new LinearLayout(this);
                    taskLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    taskLayout.setOrientation(LinearLayout.HORIZONTAL);

                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    checkBox.setChecked("1".equals(isChecked));
                    taskLayout.addView(checkBox);

                    EditText todoEditText = new EditText(this);
                    todoEditText.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    todoEditText.setHint("할 일을 입력하세요.");
                    todoEditText.setTextSize(20);
                    todoEditText.setText(task);
                    taskLayout.addView(todoEditText);

                    checkListLayout.addView(taskLayout);
                }
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

    private void saveDiary(String fileName) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(fileName, MODE_PRIVATE);
            StringBuilder content = new StringBuilder();

            // 텍스트를 한 줄로 저장
            for (int i = 0; i < checkListLayout.getChildCount(); i++) {
                LinearLayout taskLayout = (LinearLayout) checkListLayout.getChildAt(i);
                CheckBox checkBox = (CheckBox) taskLayout.getChildAt(0);
                EditText taskEditText = (EditText) taskLayout.getChildAt(1);

                String task = taskEditText.getText().toString();

                // 체크박스에 내용이 있을 때만 저장
                if (!task.isEmpty()) {
                    content.append(task).append("\n");
                } else {
                    // 내용이 비어있으면 해당 체크박스를 레이아웃에서 제거
                    checkListLayout.removeView(taskLayout);
                    i--;  // 제거 후에 인덱스가 하나씩 땡겨져서 현재 위치를 다시 확인

                    // 연관된 데이터를 삭제 (이 부분을 적절히 구현해야 합니다)
                    // 예를 들어, 파일에서 해당 라인을 삭제하는 등의 작업이 필요합니다.
                }
            }

            // 하루 피드백 추가
            content.append("\n").append(feedback.getText().toString());

            fos.write(content.toString().getBytes());

            fos.flush();
            fos.close();

            Toast.makeText(getApplicationContext(), "할 일 및 피드백을 저장했습니다.", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "오류가 발생하였습니다!", Toast.LENGTH_SHORT).show();
        }
    }

}
