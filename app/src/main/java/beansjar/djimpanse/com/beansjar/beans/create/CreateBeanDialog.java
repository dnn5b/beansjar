package beansjar.djimpanse.com.beansjar.beans.create;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.widget.EditText;

import java.util.concurrent.Executors;

import beansjar.djimpanse.com.beansjar.AppDatabase;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;
import beansjar.djimpanse.com.beansjar.beans.overview.OverviewActivity;


/**
 * Created by Dennis Jonietz on 02.10.2018.
 */
public class CreateBeanDialog {

    private final CreateCallback callback;
    private Context context;

    public CreateBeanDialog(final Context ctx, CreateCallback callback) {
        this.callback = callback;
        this.context = ctx;

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Neues Event");

        // Set up the input
        final EditText input = new EditText(ctx);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createBean(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void createBean(final String beanEvent) {
        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Bean bean = new Bean();
                bean.setEvent(beanEvent);

                AppDatabase.getInstance(context).beanDao().insertAll(bean);
                callback.beanCreated();
            }
        });
    }

}
