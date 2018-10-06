package beansjar.djimpanse.com.beansjar.beans.delete;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import java.time.format.DateTimeFormatter;

import beansjar.djimpanse.com.beansjar.R;
import beansjar.djimpanse.com.beansjar.beans.data.Bean;


public class ConfirmDeleteBeanDialog extends Dialog {

    private final Context context;
    private final Bean bean;

    public ConfirmDeleteBeanDialog(Context context, Bean bean) {
        super(context);
        this.context = context;
        this.bean = bean;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style
                .Theme_Material_Dialog_Alert);
        builder.setMessage(getMessage(bean)).setPositiveButton(context.getString(R.string
                .beans_delete_confirm_dialog_delete_btn), (d, i) -> deleteConfirmed())
                .setNegativeButton(context.getString(R.string
                        .beans_delete_confirm_dialog_cancel_btn), (d, i) -> dismiss());
        builder.create().show();
    }

    private String getMessage(Bean bean) {
        String formattedDate = bean.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return String.format(context.getString(R.string.beans_delete_confirm_dialog_text), bean
                .getEvent(), formattedDate);
    }

    private void deleteConfirmed() {
        new DeleteBeanTask(context, bean).execute();
    }
}
