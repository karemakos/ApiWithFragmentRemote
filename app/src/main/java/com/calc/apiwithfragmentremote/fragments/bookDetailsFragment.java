package com.calc.apiwithfragmentremote.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.calc.apiwithfragmentremote.R;
import com.calc.apiwithfragmentremote.models.bookModel.*;
import com.squareup.picasso.Picasso;

public class bookDetailsFragment extends Fragment {

    private View view;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView BookAuthor;
    private TextView BookDate;
    private TextView bookDesc;

    private static listData LD;

    public bookDetailsFragment(listData LD) {
        this.LD = LD;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.book_details_fragment, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        getData();
    }

    private void initViews() {
        bookImage= view.findViewById(R.id.book_imageTv);
        bookTitle= view.findViewById(R.id.book_titleTv);
        BookAuthor= view.findViewById(R.id.book_authorsTv);
        BookDate= view.findViewById(R.id.book_publishedDateTv);
        bookDesc= view.findViewById(R.id.book_descriptionTv);
    }

    private void getData() {

        String title = LD.getVolumeInfo().getTitle();
        String date = LD.getVolumeInfo().getPublishedDate();
        String desc = LD.getVolumeInfo().getDescription();
        String author = LD.getVolumeInfo().getAuthors().get(0);
        listData.volumeInfo.images photos = LD.getVolumeInfo().getImageLinks();

        bookTitle.setText(title);
        BookAuthor.setText(author);
        BookDate.setText(date);
        bookDesc.setText(desc);

        // to check the object itselft
        if (photos != null) {
            String image = photos.getThumbnail();

            // to check the photos inside the thumbnail
            if (image != null) {
                Picasso.get()
                        .load(image + ".jpg")
                        .placeholder(R.drawable.ic_library_books_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(bookImage);

            }


        }
    }
}
