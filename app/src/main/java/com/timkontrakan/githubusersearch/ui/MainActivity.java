package com.timkontrakan.githubusersearch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.timkontrakan.githubusersearch.R;
import com.timkontrakan.githubusersearch.adapter.UserAdapter;
import com.timkontrakan.githubusersearch.databinding.ActivityMainBinding;
import com.timkontrakan.githubusersearch.model.Users;
import com.timkontrakan.githubusersearch.viewmodel.UsersViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserAdapter userAdapter;
    private ActivityMainBinding binding;
    private UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(UsersViewModel.class);
        String titleSearch = getString(R.string.titleSearch);



        binding.info.setText(R.string.search_something);
        binding.info2.setText(R.string.search_your_favorite_github_developer);

        Objects.requireNonNull(getSupportActionBar()).setTitle(titleSearch);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showLoading(false);
        binding.btnSearchUser.setOnClickListener(this);

        usersViewModel.getUsersList().observe(this, list -> {

            userAdapter = new UserAdapter();
            userAdapter.setUsersList(list);
            binding.recyclerView.setAdapter(userAdapter);
            showLoading(false);

            userAdapter.setOnItemClickCallback((Users data) -> {
                showLoading(true);
                Intent intent = new Intent(MainActivity.this,DetailUserActivity.class);
                intent.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(intent);
            });
        });

    }

    private void showLoading(boolean state) {
        if (state) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        showLoading(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSearchUser) {
            showLoading(true);
            binding.searchSomething.setVisibility(View.GONE);
            if (Objects.requireNonNull(binding.editQuery.getText()).toString().isEmpty()) {
                binding.editQuery.setError("Username Required");
                binding.editQuery.setFocusable(true);
                showLoading(false);
            } else {
                String username = binding.editQuery.getText().toString();
                usersViewModel.setListUsers(username, getApplicationContext());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.change_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}