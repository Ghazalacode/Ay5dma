package com.example.agh.grad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.agh.grad.Utils.MyChipCloud;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipDeletedListener;
import fisk.chipcloud.ChipListener;

public class ChooseTags extends AppCompatActivity {


    @BindView(R.id.flexCategory)
    FlexboxLayout flexCategory;
    @BindView(R.id.flexTags)
    FlexboxLayout flexTags;
    @BindView(R.id.flexChoosenTags)
    FlexboxLayout flexChoosenTags;
    @BindView(R.id.submitTags)
    Button submitTags;

    private String TAG = "ChooseTags.this";

    private String choosenCat = "";
    Context mContext = ChooseTags.this;

    DatabaseReference dbRefernce = FirebaseDatabase.getInstance().getReference("Categories");

    HashMap<String, ArrayList<String>> caTag = new HashMap<>();




    MyChipCloud chipCloudCategory;
    MyChipCloud chipCloudTags;
     MyChipCloud chipCloudChoosen;

    ArrayList<String> categoriesList;

    ArrayList<String> choosenTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_tags);
        ButterKnife.bind(this);


        Query query = dbRefernce;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                new AsyncTask<Void, Void, Void>() {
          /*  AsyncTask  after calling firebase fetching data method
           because it starts its own thread
           which  would get onPostExecute called
           even if doInBackground haven"t finished its all code(working on the other thread)*/

                    @Override
                    protected Void doInBackground(Void... params) {


                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                            Log.e(" singleSnapshot", singleSnapshot.toString());
                            caTag.put(singleSnapshot.getKey().replaceAll("\"", "")
                                    , new ArrayList<String>(((HashMap<String, Boolean>) singleSnapshot.getValue()).keySet()));
                            ///   Log.e("onDataChange: ", caTag.get(singleSnapshot.getKey()).toString());

                        }
                        categoriesList = new ArrayList<String>(caTag.keySet());
                        Log.e("Categories", categoriesList.toString());

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        ChipCloudConfig config = new ChipCloudConfig()
                                .selectMode(ChipCloud.SelectMode.single)
                                .checkedChipColor(ContextCompat.getColor(mContext, R.color.yellowGenie))
                                .checkedTextColor(ContextCompat.getColor(mContext, R.color.WhiteDeg))
                                .uncheckedChipColor(Color.parseColor("#efefef"))
                                .uncheckedTextColor(Color.parseColor("#666666"))
                                .useInsetPadding(true)
    /*.typeface(someCustomeTypeface)*/;


                        chipCloudCategory = new MyChipCloud(mContext, flexCategory, config);
                        chipCloudCategory.addChips(categoriesList);


                        final ArrayList<String> list = new ArrayList<String>();
                        list.add("A");
                        list.add("B");
                        list.add("C");


                        chipCloudCategory.setListener(new ChipListener() {
                            @Override
                            public void chipCheckedChange(int index, boolean checked, boolean userClick) {

                                if (checked) {
   /*  animate tags  on*/
   /*fetch this cat  tags from database*/
                                    flexTags.setVisibility(View.VISIBLE);

                                    Log.e("chipCheckedChange: ", chipCloudCategory.getLabel(index));
                                    choosenCat = chipCloudCategory.getLabel(index);

                                    Log.e("getChildCount: ", String.valueOf(flexTags.getChildCount()));
                                    if (flexTags.getChildCount() > 0) {
                                        chipCloudTags.deleteChips();
                                        chipCloudTags.addChips(caTag.get(choosenCat));

                                    } else {
                                        chipCloudTags.addChips(caTag.get(choosenCat));

                                    }

                                } else {
 /*fetch this cat  tags from database*/
                                    flexTags.setVisibility(View.INVISIBLE);

                                }

                                Log.e("checked: ", String.valueOf(checked));

                            }
                        }, true);


                        chipCloudTags = new MyChipCloud(mContext, flexTags, new ChipCloudConfig()
                                .selectMode(ChipCloud.SelectMode.multi)
                                .checkedChipColor(ContextCompat.getColor(mContext, R.color.yellowGenie))
                                .checkedTextColor(ContextCompat.getColor(mContext, R.color.WhiteDeg))
                                .uncheckedChipColor(Color.parseColor("#efefef"))
                                .uncheckedTextColor(Color.parseColor("#666666"))
                                .useInsetPadding(true));


                        chipCloudChoosen = new MyChipCloud(mContext, flexChoosenTags, new ChipCloudConfig()
                                .selectMode(ChipCloud.SelectMode.none)
                                .checkedChipColor(Color.parseColor("#ddaa00"))
                                .checkedTextColor(Color.parseColor("#ffffff"))
                                .uncheckedChipColor(ContextCompat.getColor(mContext, R.color.yellowGenie))
                                .uncheckedTextColor(ContextCompat.getColor(mContext, R.color.WhiteDeg))
                                .showClose(ContextCompat.getColor(mContext, R.color.WhiteDeg), 500));

                        chipCloudTags.setListener(new ChipListener() {
                            @Override
                            public void chipCheckedChange(int index, boolean checked, boolean userClick) {

                                if (checked) {
   /*  animate tags  on*/
   /*fetch this cat  tags from database*/
                                    if (flexChoosenTags.getVisibility() == View.INVISIBLE) {
                                        flexChoosenTags.setVisibility(View.VISIBLE);

                                    }

                                    Log.e("chipCheckedChange: ", chipCloudTags.getLabel(index));
                                    chipCloudChoosen.addChip(choosenCat + " : " + chipCloudTags.getLabel(index));
                                } else {
                                    if (chipCloudTags.getSelectedTagIndex().size() == 0) {
                                        flexChoosenTags.setVisibility(View.INVISIBLE);
                                    }

                                }

                                Log.e("checked: ", String.valueOf(checked));

                            }
                        }, true);


                        chipCloudChoosen.setDeleteListener(new ChipDeletedListener() {
                            @Override
                            public void chipDeleted(int index, String label) {
                                Log.d(TAG, String.format("chipDeleted at index: %d label: %s", index, label));

                                final String[] tokens = label.split(Pattern.quote(" : "));
                                Log.e("chipDeleted: ", tokens[1]);
                                List<Integer> selectedIndexes = chipCloudTags.getSelectedTagIndex();
                                for (int i = 0; i < selectedIndexes.size(); i++) {

                                    if (chipCloudTags.getLabel(i).equals(tokens[1])) {

                                        chipCloudTags.deselectIndex(i);
                                    }
                                }
                            }
                        });
                    }
                }.execute();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


    @OnClick(R.id.submitTags)
    public void onViewClicked() {

        choosenTags = new ArrayList<String>();
        Log.e("size: ", String.valueOf(chipCloudChoosen.getChildsCount()));
        for (int i = 0; i < chipCloudChoosen.getChildsCount() ; i++) {
            choosenTags.add(chipCloudChoosen.getLabel(i).replaceAll("\"", ""));
            Log.e("onViewClicked: ", chipCloudChoosen.getLabel(i));
        }
        Log.e("choosenTags: ", choosenTags.toString());

        Intent  i = getIntent();
        Intent intent;
        if (i.getStringExtra("class").equals("SearchServices")) {

                   intent = new Intent(mContext, SearchServices.class);
            intent.putExtra("tagSelected", choosenTags);
            startActivity(intent);
        }
        if (i.getStringExtra("class").equals("AddService")) {

                   intent = new Intent(mContext, AddService.class);
            intent.putExtra("tagSelected", choosenTags);
            startActivity(intent);
        }


    }
}
