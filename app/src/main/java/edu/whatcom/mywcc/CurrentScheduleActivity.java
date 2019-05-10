package edu.whatcom.mywcc;

import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import edu.whatcom.mywcc.models.AcademicQuarter;
import edu.whatcom.mywcc.models.Course;
import edu.whatcom.mywcc.models.StudentProfile;
import edu.whatcom.mywcc.models.Weekday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrentScheduleActivity extends AppCompatActivity {
    private StudentProfile profile;
    private AcademicQuarter qtr;

    /**
     * The {@link androidx.viewpager.widget.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_schedule);

        profile = new StaticBackend().getStudentProfile();
        qtr = profile.quarterlyEnrollments.keySet().iterator().next(); // ugh, just pick one

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class WeekdayFragment extends Fragment {
        private static final String ARG_PROFILE = "profile";
        private static final String ARG_QUARTER = "quarter";
        private static final String ARG_WEEKDAY = "weekday";

        private StudentProfile profile;

        public WeekdayFragment() {

        }

        private class Entry implements Comparable<Entry> {
            public Course course;
            public Course.Schedule schedule;

            public Entry(Course c, Course.Schedule s) {
                course = c;
                schedule = s;
            }

            @Override
            public int compareTo(Entry o) {
                return schedule.compareTo(o.schedule);
            }
        }

        public static WeekdayFragment newInstance(StudentProfile profile, AcademicQuarter qtr, Weekday weekday) {
            WeekdayFragment fragment = new WeekdayFragment();
            Bundle args = new Bundle();
            args.putParcelable(ARG_PROFILE, profile);
            args.putParcelable(ARG_QUARTER, qtr);
            args.putParcelable(ARG_WEEKDAY, weekday);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_current_schedule, container, false);

            StudentProfile profile = getArguments().getParcelable(ARG_PROFILE);
            AcademicQuarter qtr = getArguments().getParcelable(ARG_QUARTER);
            Weekday weekday = getArguments().getParcelable(ARG_WEEKDAY);

            RecyclerView recycler = (RecyclerView) rootView.findViewById(R.id.schedule_recycler);
            RecyclerView.LayoutManager lm = new RecyclerLayoutManager();
            recycler.setLayoutManager(lm);

            List<Course> courses = profile.quarterlyEnrollments.get(qtr);
            List<Entry> schedulesToday = new ArrayList<>();
            for(Course c : courses) {
                for(Course.Schedule s : c.schedule) {
                    if(s.days.contains(weekday)) {
                        schedulesToday.add(new Entry(c, s));
                    }
                }
            }
            Collections.sort(schedulesToday);
            recycler.setAdapter(new RecyclerAdapter(schedulesToday));

            return rootView;
        }

        private class RecyclerAdapter extends RecyclerView.Adapter {
            private List<Entry> schedulesToday;

            public RecyclerAdapter(List<Entry> schedulesToday) {
                this.schedulesToday = schedulesToday;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_schedule_item, parent, false);
                return new RecyclerViewHolder(v);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((RecyclerViewHolder) holder).bind(schedulesToday.get(position));
            }

            @Override
            public int getItemCount() {
                return schedulesToday.size();
            }
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            private TextView textTitle;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                textTitle = itemView.findViewById(R.id.sched_title);
            }

            public void bind(Entry e) {
                textTitle.setText(e.course.title);
            }
        }

        private class RecyclerLayoutManager extends RecyclerView.LayoutManager {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ));
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WeekdayFragment.newInstance(profile, qtr, Weekday.values()[position]);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
