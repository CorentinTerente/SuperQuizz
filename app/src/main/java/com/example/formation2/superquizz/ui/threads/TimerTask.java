package com.example.formation2.superquizz.ui.threads;

import android.graphics.Bitmap;
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
                if(isCancelled()){
                    break;
                } else {
                    SystemClock.sleep(1000);
                    count++;
                    publishProgress(count * 5);
                }
            }

        return "Complete";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        listener.onProgressTask(values[0]);
    }

    @Override
    protected void onPostExecute(String result){
        listener.onFinishTask();
    }


    public interface OnDelayTaskListener {
        void onProgressTask(int progress);
        void onFinishTask();
        void onStartingTask();
    }

}