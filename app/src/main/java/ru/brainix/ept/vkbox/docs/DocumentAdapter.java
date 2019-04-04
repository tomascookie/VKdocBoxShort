package ru.brainix.ept.vkbox.docs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.brainix.ept.vkbox.MainActivity;
import ru.brainix.ept.vkbox.R;


public class DocumentAdapter  extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {


        private LayoutInflater inflater;
        private List<DocumentModel> docs;

        public DocumentAdapter(Context context, List<DocumentModel> docs) {

            this.docs = docs;
            this.inflater = LayoutInflater.from(context);

        }


        @Override
        public DocumentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.item_doclist, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(DocumentAdapter.ViewHolder holder, int position) {

            DocumentModel docModel = docs.get(position);

            holder.imageFileType.setText(docModel.getImageFileType());
            holder.textFileName.setText(docModel.getTitleDoc());
            holder.textFileSize.setText(String.valueOf(docModel.getSizeDoc()));
            holder.textFileDate.setText(String.valueOf(docModel.getDateDoc()));

        }


        @Override
        public int getItemCount() {
             return docs.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            final TextView textFileName, textFileDate, textFileSize, imageFileType;

            ViewHolder(View view) {

                super(view);
                view.setOnClickListener(this);


                imageFileType = view.findViewById(R.id.imageFileIcon);
                textFileName =  view.findViewById(R.id.textFileName);
                textFileDate =  view.findViewById(R.id.textFileDate);
                textFileSize =  view.findViewById(R.id.textFileSize);

            }

            @Override
            public void onClick(View view) {

               //Получаем данные из модели и ставим их в список
               DocumentPopUpWindow window = new DocumentPopUpWindow(
                       docs.get(getPosition()).getTitleDoc(),
                       docs.get(getPosition()).getSizeDoc(),
                       docs.get(getPosition()).getDateDoc(),
                       docs.get(getPosition()).getImageFileType(),
                       String.valueOf(docs.get(getPosition()).getOwnerId()),
                       docs.get(getPosition()).getIdDoc(),
                       docs.get(getPosition()).getUrl());

               //Вызываем всплывающие окно
               window.getPopUp(view);

            }
    }


}