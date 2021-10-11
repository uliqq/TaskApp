package com.geektech.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;
import com.geektech.taskapp.databinding.FragmentHomeBinding;
import com.geektech.taskapp.models.News;
import com.geektech.taskapp.ui.NewsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment implements NewsAdapter.onItemClick {

    private FragmentHomeBinding binding;
    private NewsAdapter adapter;
    private boolean isTaskChanged = false;
    private int pos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewsAdapter();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(null);
                isTaskChanged = false;
            }
        });

        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("news");
                Log.e("Home", "text = " + news.getTitle());
                if (isTaskChanged) {
                    adapter.updateItem(pos, news);
                } else {

                    adapter.addItem(news);
                }
            }
        });
        initList();
    }

    private void initList() {

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

    }

    private void openFragment(News news) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", news);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.taskFragment, bundle);
    }


    @Override
    public void onLongClick(int position) {

        AlertDialog.Builder deleteBox = new AlertDialog.Builder(requireActivity());

        deleteBox.setTitle("Delete");
        deleteBox.setMessage("Do you want to delete?");
        deleteBox.setIcon(R.drawable.ic_baseline_delete_24);
        deleteBox.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.removeItem(position);
                dialog.dismiss();
                Toast.makeText(deleteBox.getContext(), "Item has been deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        deleteBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .show();
    }

    @Override
    public void onClick(int position) {
        isTaskChanged = true;
        pos = position;
        News news = adapter.getItem(position);
        openFragment(news);

    }
}