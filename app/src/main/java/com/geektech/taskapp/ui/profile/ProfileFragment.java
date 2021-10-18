package com.geektech.taskapp.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.geektech.taskapp.Prefs;
import com.geektech.taskapp.R;
import com.geektech.taskapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLocalContent();
        initEditTextListeners();

        binding.avatarIv.setOnClickListener(v -> {
            pickImage();
        });
        setImage();

        binding.btnSingOut.setOnClickListener(v -> {
signOut();
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(requireActivity(),"Signed Out", Toast.LENGTH_SHORT).show();
    }

    private void initEditTextListeners() {
        binding.nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Prefs prefs = new Prefs(requireContext());
                prefs.saveName(editable.toString());
                Toast.makeText(requireContext(), editable.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLocalContent() {
        Prefs prefs = new Prefs(requireContext());
        String uri = prefs.getImage();
        String name = prefs.getName();

        Glide.with(requireActivity())
                .load(uri)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.avatarIv);

        binding.nameEt.setText(name);
    }

    private void setImage() {
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Glide.with(requireActivity())
                                .load(uri)
                                .circleCrop()
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .into(binding.avatarIv);
                        Prefs prefs = new Prefs(requireContext());
                        prefs.saveAvatarImage(uri);
                    }
                });
    }

    private void pickImage() {
        mGetContent.launch("image/*");
    }
}