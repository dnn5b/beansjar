package beansjar.djimpanse.com.beansjar.beans.delete;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;


/**
 * AsyncTask to delete a single {@link Bean} from the database.
 */
public class DeleteBeanTask extends AsyncTask<Void, Void, Boolean> {

    private final Context context;
    private final Bean bean;

    public DeleteBeanTask(Context context, Bean bean) {
        this.bean = bean;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        BeanDao beanDao = AppDatabase.getInstance(context).beanDao();
        beanDao.delete(bean);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
    }

}
