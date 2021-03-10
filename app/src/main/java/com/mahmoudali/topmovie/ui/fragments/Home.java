package com.mahmoudali.topmovie.ui.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudali.topmovie.Utils.Constants;
import com.mahmoudali.topmovie.adapter.HomeAdapter;
import com.mahmoudali.topmovie.adapter.ViewPagerAdapter;
import com.mahmoudali.topmovie.databinding.HomeLayoutBinding;
import com.mahmoudali.topmovie.model.Movie;
import com.mahmoudali.topmovie.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Home extends Fragment {

    private static final String TAG = "Home";
    private HomeViewModel viewModel;
    private HomeLayoutBinding binding;
    private ArrayList<Movie> currentMovies, popularMovies, topRatedMovies, upcomingMovies;
    private ViewPagerAdapter currentMoviesAdapter;
    private HomeAdapter upcomingAdapter, popularAdapter, topRatedAdapter;
    private HashMap<String, String> map = new HashMap<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = HomeLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(Home.this).get(HomeViewModel.class);


        map.put("api_key", Constants.API_KEY);
        map.put("page", "1");
        binding.progressBar.setVisibility(View.VISIBLE);
        observeData();
        getMoviesList();
        setUpRecyclerViewsAndViewPager();
        setUpOnclick();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStart() {
        super.onStart();
        observeData();
        binding.progressBar.setVisibility(View.VISIBLE);
        if (Constants.isNetworkAvailable(getActivity())) {
            getMoviesList();
        } else {
            observeData();
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeData() {
        viewModel.getCurrentlyShowingList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies.size() == 0 || movies == null){
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    binding.currentlyShowing.setVisibility(View.VISIBLE);
                    binding.viewAllCurrent.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.GONE);
                    currentMoviesAdapter.setMovieListResults(movies);
                }
            }
        });

        viewModel.getUpcomingMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                    binding.upcomingShowing.setVisibility(View.VISIBLE);
                    binding.viewAllUpcoming.setVisibility(View.VISIBLE);
                    upcomingAdapter.setMoviesList(movies);
            }
        });

        viewModel.getPopularMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                    binding.popular.setVisibility(View.VISIBLE);
                    binding.viewAllPopular.setVisibility(View.VISIBLE);
                    popularAdapter.setMoviesList(movies);
            }
        });

        viewModel.getTopRatedMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                    binding.topRated.setVisibility(View.VISIBLE);
                    binding.viewAllTopRated.setVisibility(View.VISIBLE);
                    topRatedAdapter.setMoviesList(movies);
            }
        });
    }

    private void getMoviesList() {
        viewModel.getCurrentlyShowingMovies(map);
        viewModel.getUpcomingMovies(map);
        viewModel.getTopRatedMovies(map);
        viewModel.getPopularMovies(map);
    }

    private void setUpRecyclerViewsAndViewPager() {
        currentMoviesAdapter = new ViewPagerAdapter(getContext(), currentMovies);
        binding.currentlyShowingViewPager.setAdapter(currentMoviesAdapter);

        binding.upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        upcomingAdapter = new HomeAdapter(getContext(), upcomingMovies);
        binding.upcomingRecyclerView.setAdapter(upcomingAdapter);

        binding.topRatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        topRatedAdapter = new HomeAdapter(getContext(), topRatedMovies);
        binding.topRatedRecyclerView.setAdapter(topRatedAdapter);

        binding.popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        popularAdapter = new HomeAdapter(getContext(), popularMovies);
        binding.popularRecyclerView.setAdapter(popularAdapter);
    }

    private void setUpOnclick() {
        binding.viewAllCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
                action.setMovieCategory(Constants.Current);
                Navigation.findNavController(v).navigate(action);
            }
        });
        binding.viewAllUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
                action.setMovieCategory(Constants.Upcoming);
                Navigation.findNavController(v).navigate(action);
            }
        });
        binding.viewAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
                action.setMovieCategory(Constants.Popular);
                Navigation.findNavController(v).navigate(action);
            }
        });
        binding.viewAllTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDirections.ActionHome2ToMovies action = HomeDirections.actionHome2ToMovies();
                action.setMovieCategory(Constants.TopRated);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}