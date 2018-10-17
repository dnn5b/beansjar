package beansjar.djimpanse.com.beansjar.beans.create;


import android.app.Activity;
import android.os.AsyncTask;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;


public class CreateBeanTask extends AsyncTask<Void, Void, Void> {

    private Activity activity;
    private Bean bean;
    private CreateCallback callback;

    public CreateBeanTask(Activity activity, Bean bean, CreateCallback callback) {
        this.activity = activity;
        this.bean = bean;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... params) {
        AppDatabase.getInstance(activity).beanDao().insertAll(bean);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (activity == null) {
            return;
        }

        callback.beanCreated();
    }
}
