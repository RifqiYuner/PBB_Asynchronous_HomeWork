package com.example.asynchronoushomework;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Random;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private WeakReference<TextView> mResultTextView;
    private static final int CHUNK_SIZE = 20; //besaran progress bar yang akan diisi (20 artinya akan mengisi setiap 5% pada setiap looping)

    public SimpleAsyncTask(TextView tv, ProgressBar progressbar,TextView result) {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(progressbar);
        mResultTextView = new WeakReference<>(result);
    }

    @Override
    protected String doInBackground(Void... voids) {

        Random r = new Random();
        int n = r.nextInt(11);

        int s = n * 350;
        int chunksize = s/CHUNK_SIZE; //menentukan besaran thread sleep yang akan dilakukan selama looping yang besaran nya adalah
        // waktu sleep setelah dihitung lalu dibagi chunksize




        for (int i = 0; i < CHUNK_SIZE; i++){
            try {
                Thread.sleep(chunksize);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
            publishProgress(((i + 1) * 100 / CHUNK_SIZE));
            Log.d(TAG, "doInBackground: " + (i + 1) * 100 / CHUNK_SIZE + " with the size of"+ chunksize);
        }


        return "Awake at last after sleeping for " + s + " milliseconds!";
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]); //memunculkan ui update yang memberitahu berapa lama thread itu tidur dalam bentuk progress bar
        mResultTextView.get().setText("Current Progress: " + values[0] + "%"); //memunculkan text persentase
    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }



}

