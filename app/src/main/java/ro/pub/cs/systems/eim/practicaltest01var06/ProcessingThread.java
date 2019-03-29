package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context;
    boolean startedThread;

    public ProcessingThread(Context context) {
        startedThread = true;
        this.context = context;
    }

    @Override
    public void run() {
        while(startedThread) {
            Log.d("Practical", "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());
            String message = String.valueOf(Math.abs(new Random().nextInt() % 10));
            sendMessage(message);
            sleep();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        Intent intent = new Intent();
        intent.setAction("action.practicaltest01");
        intent.putExtra("data", message);

        context.sendBroadcast(intent);
    }

    public void stopThread() {
        startedThread = false;
    }
}