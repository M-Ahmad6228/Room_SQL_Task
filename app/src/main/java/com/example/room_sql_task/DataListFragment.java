package com.example.room_sql_task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DataListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Employee> dataList;
    DataAdapter dataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datalist, container, false);

        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {
        recyclerView = view.findViewById(R.id.dataList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        dataList = LoginFragment.db.employeeDAO().getAllEmployees();
        dataAdapter = new DataAdapter(dataList);
        recyclerView.setAdapter(dataAdapter);
    }


}
