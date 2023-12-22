package com.prosoft.todolist;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        loadTodayTasks(); // 오늘의 할 일 로드 및 표시
    }

    private void loadTodayTasks() {
        String todayDate = getTodayDate();
        // TODO: 오늘 날짜(todayDate)에 해당하는 할 일 데이터 로드
        // TODO: 로드된 데이터를 화면에 표시
    }

    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}