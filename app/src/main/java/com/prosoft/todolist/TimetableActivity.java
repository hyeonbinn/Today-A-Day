package com.prosoft.todolist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TimetableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        ArrayList<TodoItem> todayTasks = loadTodayTasks(); // 오늘의 할 일 데이터 로드
        if (todayTasks != null) {
            displayTasksOnTimetable(todayTasks); // 할 일을 타임테이블에 표시
        }
    }


    private ArrayList<TodoItem> loadTodayTasks() {
        String todayDate = getTodayDate();
        String fileName = todayDate + ".txt"; // 'yyyy-MM-dd.txt' 형식
        // TODO: 오늘 날짜(todayDate)에 해당하는 할 일 데이터 로드
        // TODO: 로드된 데이터를 화면에 표시
        return null;
    }

    private String getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private void displayTasksOnTimetable(ArrayList<TodoItem> tasks) {
        for (TodoItem task : tasks) {
            String time = roundToNearestHalfHour(task.getTime()); // 반올림 또는 절사
            TextView timeSlot = findTimeSlotView(time);
            if (timeSlot != null) {
                String existingText = timeSlot.getText().toString();
                timeSlot.setText(existingText + "\n" + task.getTask());
            }
        }
    }
    private String roundToNearestHalfHour(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        if (minutes < 15) {
            minutes = 0;
        } else if (minutes < 45) {
            minutes = 30;
        } else {
            minutes = 0;
            hour++; // 다음 시간으로 넘어감
        }

        // 24시간 형식 유지
        if (hour == 24) hour = 0;

        return String.format(Locale.getDefault(), "%02d:%02d", hour, minutes);
    }

    private TextView findTimeSlotView(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int minutes = Integer.parseInt(time.split(":")[1]);

        String viewId = "time_" + (hour < 10 ? "0" + hour : hour) + (minutes == 0 ? "00" : "30");
        int resId = getResources().getIdentifier(viewId, "id", getPackageName());
        return findViewById(resId);
    }

}