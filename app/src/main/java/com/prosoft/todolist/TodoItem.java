package com.prosoft.todolist;
    public class TodoItem {
        private String task;
        private String time;

        public TodoItem(String task, String time) {
            this.task = task;
            this.time = time;
        }

        // Getters and Setters
        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }