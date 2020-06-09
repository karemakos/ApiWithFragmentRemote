package com.calc.apiwithfragmentremote.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.calc.apiwithfragmentremote.R;
import com.calc.apiwithfragmentremote.models.bookModel;
import com.calc.apiwithfragmentremote.models.bookModel.*;
import com.calc.apiwithfragmentremote.network.remote.retrofitClint;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bookMainFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private EditText searchFiled;
    private ImageView searchBtn;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, null);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView();
        // this method to check the internet before getting the data,
        // thats why we put get data down inside the method private void checkConnection(){}
        refresh();
        // this method will appear once when we will open to make the user search from the begning
        // if we dont want it and want to show the recyclerview then we will put here checkConnection();
        setSearch();

        smartSearch();
    }

    private void setSearch() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchFiled.getText().toString();
                if (query.isEmpty())
                {
                    Toast.makeText(getContext(), "Please Put valid data", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                checkConnection(query);

            }
        });
    }

    // to have smart search
    private void smartSearch() {
        searchFiled.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String q= s.toString().trim();

                progressBar.setVisibility(View.VISIBLE);
                getData(q);

                if(q.isEmpty())
                {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void refresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
              //  checkConnection(query);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    // 1 - we sent here String query as argument to get data from user to search
    private void checkConnection(String query)
    {
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = (activeNetwork != null) &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected)
        {
            // 2- we sent here String query as argument to get data from user to search
            getData(query);
        } else
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Please Check your internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }

    }
    // 3 - we sent here String query as argument to get data from user to search
    private void getData(String q)
    {

        Call<bookModel> call =retrofitClint.getInstance().getBooks(q);
        call.enqueue(new Callback<bookModel>() {
            @Override
            public void onResponse(Call<bookModel> call, Response<bookModel> response)
            {
                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(new booksAdapter(response.body().getItems()));
                }
            }

            @Override
            public void onFailure(Call<bookModel> call, Throwable t) {

            }
        });
    }

    private void recyclerView()
    {
        recyclerView= view.findViewById(R.id.recycler_user);
        progressBar= view.findViewById(R.id.progress_circular);
        swipeRefreshLayout= view.findViewById(R.id.books_refresh);
        searchFiled= view.findViewById(R.id.search_filed);
        searchBtn=view.findViewById(R.id.search_btn);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    class booksAdapter extends RecyclerView.Adapter<booksAdapter.VH>
    {
        List<listData> list;

        public booksAdapter(List<listData> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             view= LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
          final listData model = list.get(position);
            String title=  model.getVolumeInfo().getTitle();
            String date=  model.getVolumeInfo().getPublishedDate();
           // String desc=  model.getVolumeInfo().getDescription();

            String author;
            if (model.getVolumeInfo().getAuthors()!=null)
            {  author= model.getVolumeInfo().getAuthors().get(0);
            } else
            {
                author="No author found!";
            }

           listData.volumeInfo.images photos = model.getVolumeInfo().getImageLinks();

            // to check the object itselft
            if (photos !=null)
            {
                String image=photos.getThumbnail();

                // to check the photos inside the thumbnail
               if (image !=null)
            {
                Picasso.get()
                        .load(image+".jpg")
                        .placeholder(R.drawable.ic_library_books_black_24dp)
                        .error(R.drawable.ic_error_outline_black_24dp)
                        .into(holder.bookImage);
            }
            }

            holder.bookTitle.setText(title);
            holder.BookDate.setText("Date: "+date);
            holder.BookAuthor.setText(author);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new bookDetailsFragment(model))
                           .addToBackStack(null)
                           .commit();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class  VH extends RecyclerView.ViewHolder
        {
            ImageView bookImage;
            TextView bookTitle,BookAuthor,BookDate;


            public VH(@NonNull View itemView) {
                super(itemView);
                bookImage =itemView.findViewById(R.id.book_image);
                bookTitle =itemView.findViewById(R.id.book_title);
                BookAuthor=itemView.findViewById(R.id.book_author);
                BookDate= itemView.findViewById(R.id.book_date);
            }
        }
    }
}
