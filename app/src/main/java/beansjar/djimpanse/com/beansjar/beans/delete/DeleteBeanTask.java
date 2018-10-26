package beansjar.djimpanse.com.beansjar.beans.delete;


import android.content.Context;
import android.os.AsyncTask;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.data.BeanDao;


/**
 * AsyncTask to delete a single {@link Bean} from the database.
 */
public class DeleteBeanTask extends AsyncTask<Void, Void, Boolean> {

    private final Context context;
    private final Bean bean;
    private final DeleteCallback mDeleteListener;

    public DeleteBeanTask(Context context, Bean bean, DeleteCallback listener) {
        this.bean = bean;
        this.context = context;
        this.mDeleteListener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        BeanDao beanDao = AppDatabase.getInstance(context).beanDao();
        beanDao.delete(bean);
        return bean.getImage().delete();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO handle result?
        mDeleteListener.beanDeleted();
    }

}
