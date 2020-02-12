package ru.brainix.ept.vkbox.docs.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.brainix.ept.vkbox.R;
import ru.brainix.ept.vkbox.docs.fragment.DocOptionsWindow;


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
               DocOptionsWindow window = new DocOptionsWindow(
                       docs.get(getAdapterPosition()).getTitleDoc(),
                       docs.get(getAdapterPosition()).getSizeDoc(),
                       docs.get(getAdapterPosition()).getDateDoc(),
                       docs.get(getAdapterPosition()).getImageFileType(),
                       String.valueOf(docs.get(getAdapterPosition()).getOwnerId()),
                       docs.get(getAdapterPosition()).getIdDoc(),
                       docs.get(getAdapterPosition()).getUrl());

               //Вызываем всплывающие окно
               window.getPopUp(view);

            }
    }


}