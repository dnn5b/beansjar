package beansjar.djimpanse.com.beansjar.beans.list;


import beansjar.djimpanse.com.beansjar.beans.data.Bean;


public interface BeanClickedCallback {

    void onLongClick(Bean bean);

    void onClick(Bean bean);

}
