package com.example.bulletbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private SlideAdapter adapter;
    private TabLayout tabLayout;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabbed);

        tabLayout = findViewById(R.id.tabLay);
        pager = findViewById(R.id.pager);
        adapter = new SlideAdapter(this);
        pager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setText("To Do");
                        break;
                    case 2:
                        tab.setText("Monthly Goals");
                        break;
                    case 3:
                        tab.setText("Mood Tracker");
                        break;
                    case 4:
                        tab.setText("Habit Tracker");
                }
            }
        }).attach();
    }

    public void addErrand(View view) {
        TextInputEditText nameET = this.findViewById(R.id.nameEditText);
        String name = Objects.requireNonNull(nameET.getText().toString());


        nameET.setText("");
        nameET.clearFocus();


        if ( !name.equals("")) {

            Errands errands = new Errands(name);
            ErrandListFragment listFragment = (ErrandListFragment) getSupportFragmentManager()
                    .findFragmentByTag(getString(R.string.errandList_FragmentTag));

            listFragment.addErrand(errands);
        }
    }


    private class SlideAdapter extends FragmentStateAdapter {

        public SlideAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                  return new HomeFragment();

                case 1:
                    return new ToDoFragment();

                case 2:
                    return new MonthlyGoalsFragment();

                case 3:
                    return new MoodTrackerFragment();

                default:
                    return new HabitTrackerFragment();

            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }

}