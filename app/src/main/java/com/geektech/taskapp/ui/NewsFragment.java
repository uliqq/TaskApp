package com.geektech.taskapp.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.geektech.taskapp.R;
import com.geektech.taskapp.databinding.FragmentNewsBinding;
import com.geektech.taskapp.databinding.FragmentProfileBinding;
import com.geektech.taskapp.models.News;

import org.jetbrains.annotations.NotNull;

public class NewsFragment extends Fragment {


    private FragmentNewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText editText = binding.editText;
        Button button = binding.btnSave;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
News data = (News) getArguments().getSerializable("task");
if (data != null) {
    binding.editText.setText(data.getTitle());

}



    }

    private void sendData() {
        String text = binding.editText.getText().toString();
        News news = new News(text);
        news.setCreatedAt(System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable("news", news);
        getParentFragmentManager().setFragmentResult("rk_news", bundle);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}