package com.example.formation2.superquizz.ui.threads;

import android.os.AsyncTask;
import android.os.SystemClock;


public class TimerTask extends AsyncTask<Void, Integer, String> {
    int count = 0;
    private OnDelayTaskListener listener;

    public TimerTask(OnDelayTaskListener listener){
        super();
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onStartingTask();
    }

    @Override
    protected String doInBackground(Void... params) {
        while (count < 60) {
            SystemClock.sleep(1000);
            count++;
            publishProgress(count * 5);
        }
        listener.onFinishTask();
        return "Complete";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onProgressTask(values[0]);
    }


    public interface OnDelayTaskListener {
        void onProgressTask(int progress);
        void onFinishTask();
        void onStartingTask();
    }

}