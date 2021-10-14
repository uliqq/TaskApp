package com.geektech.taskapp.ui.board;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geektech.taskapp.R;
import com.geektech.taskapp.databinding.FragmentBoardBinding;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import org.jetbrains.annotations.NotNull;


public class BoardFragment extends Fragment implements BoardAdapter.OnClick {

    private FragmentBoardBinding binding;
    private DotsIndicator dot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initViewPager(view);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });

        binding.skipBtn.setOnClickListener(v -> {
            skip();
        });
    }

    private void skip() {
        NavController navController = Navigation.findNavController((Activity) requireContext(),R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    private void initViewPager(View view){
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();
        adapter.initListener(this);
        viewPager.setAdapter(adapter);
        dot = binding.dotsIndicator;
        dot.setViewPager2(viewPager);
    }
    

    @Override
    public void OnClick() {
        skip();
    }
}