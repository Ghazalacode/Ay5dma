package com.example.agh.grad;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CatgoryTagActivity extends AppCompatActivity {
    Spinner spinnerCatgoy;
    ArrayAdapter<String> arrayAdapterCatgory;
    String catgory;
    Spinner spinnerTag;
    ArrayAdapter<String> arrayAdapterTagHealth;
    ArrayAdapter<String> arrayAdapterTagFood;
    ArrayAdapter<String> arrayAdapterTagBuying;
    ArrayAdapter<String> arrayAdapterTagNewest1;
    ArrayAdapter<String> arrayAdapterTagNewest2;
    ArrayAdapter<String> arrayAdapterTagNewest3;

    RecyclerView recyclerViewListTagChossen;
    recylerTagAdapter recylerTagAdapter;
    ArrayList<String> tagChossen = new ArrayList<String>();

    Button btnAddNewTag, btnSubmmit, btnSubmmitDialogAddingNewTagOrCatgory, btnAddNewCatgory;
    EditText edAddNewTag;

    CharSequence newTag;
    CharSequence newCatgory;
    ArrayList<String> newCatgories = new ArrayList<>();

    String[] tagNewest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catgory_tag);


        spinnerCatgoy = (Spinner) findViewById(R.id.spinnerCatgory);
        spinnerTag = (Spinner) findViewById(R.id.spinnerTag);
        recyclerViewListTagChossen = (RecyclerView) findViewById(R.id.recyclerViewListTagChossen);
        btnAddNewTag = (Button) findViewById(R.id.btnAddNewTag);
        btnAddNewCatgory = (Button) findViewById(R.id.btnAddNewCatgory);
        btnSubmmit = (Button) findViewById(R.id.btnSubmmit);

        recyclerViewListTagChossen.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        final String[] catgories = getResources().getStringArray(R.array.Catgory);

        arrayAdapterCatgory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(catgories)));
        arrayAdapterCatgory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCatgoy.setAdapter(arrayAdapterCatgory);


        String[] tagHealth = getResources().getStringArray(R.array.TagHealth);
        arrayAdapterTagHealth = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagHealth)));
        arrayAdapterTagHealth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final String[] tagFood = getResources().getStringArray(R.array.TagFood);
        arrayAdapterTagFood = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagFood)));
        arrayAdapterTagFood.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        String[] tagBuying = getResources().getStringArray(R.array.TagBuying);
        arrayAdapterTagBuying = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagBuying)));
        arrayAdapterTagBuying.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        tagNewest = new String[]{"Select Tag"};

        arrayAdapterTagNewest1 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagNewest)));
        arrayAdapterTagNewest1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        arrayAdapterTagNewest2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagNewest)));
        arrayAdapterTagNewest2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        arrayAdapterTagNewest3 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, new ArrayList(Arrays.asList(tagNewest)));
        arrayAdapterTagNewest3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCatgoy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spinnerTag.setVisibility(View.INVISIBLE);
                } else {

                    if (parent.getItemAtPosition(position).equals("Health")) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagHealth);

                    } else if (parent.getItemAtPosition(position).equals("Food")) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagFood);
                    } else if (parent.getItemAtPosition(position).equals("حرف")) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagBuying);
                    } else if (parent.getItemAtPosition(position).equals(newCatgories.get(0).toString())) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagNewest1);

                    } else if (parent.getItemAtPosition(position).equals(newCatgories.get(1).toString())) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagNewest2);
                    } else if (parent.getItemAtPosition(position).equals(newCatgories.get(2).toString())) {
                        spinnerTag.setVisibility(View.VISIBLE);
                        spinnerTag.setAdapter(arrayAdapterTagNewest3);
                    }
                    catgory = parent.getSelectedItem().toString();
                    spinnerTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                            } else {
                                if (!tagChossen.contains(catgory + ":" + parent.getSelectedItem().toString())) {
                                    tagChossen.add(catgory + ":" + parent.getSelectedItem().toString());
                                    // Toast.makeText(getApplicationContext(),tagChossen.toString(),Toast.LENGTH_LONG).show();
                                    recylerTagAdapter = new recylerTagAdapter(tagChossen);
                                    recyclerViewListTagChossen.setAdapter(recylerTagAdapter);
                                } else {
                                    Toast.makeText(getApplicationContext(), "You added this Tag before....", Toast.LENGTH_LONG).show();
                                }

                            }
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
        btnAddNewTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogAddNewTag = new Dialog(CatgoryTagActivity.this);
                dialogAddNewTag.setContentView(R.layout.add_new_tag_dialog_layout);

                dialogAddNewTag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogAddNewTag.setCancelable(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialogAddNewTag.create();
                }
                dialogAddNewTag.setCanceledOnTouchOutside(true);


                edAddNewTag = (EditText) dialogAddNewTag.findViewById(R.id.edAddNewTag);
                edAddNewTag.setHint("Enter Tag you wanna to add...");
                btnSubmmitDialogAddingNewTagOrCatgory = (Button) dialogAddNewTag.findViewById(R.id.btnSubmmitDialogAddingNewTagOrCatgory);


                btnSubmmitDialogAddingNewTagOrCatgory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newTag = "" + edAddNewTag.getText();
                        if (spinnerTag.getVisibility() == View.VISIBLE) {


                            if (catgory.equals("Health")) {
                                arrayAdapterTagHealth.add((String) newTag);
                                arrayAdapterTagHealth.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();
                            } else if (catgory.equals("Food")) {
                                arrayAdapterTagFood.add((String) newTag);
                                arrayAdapterTagFood.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();
                            } else if (catgory.equals("حرف")) {
                                arrayAdapterTagBuying.add((String) newTag);
                                arrayAdapterTagBuying.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();
                            } else if (catgory.equals(newCatgories.get(0).toString())) {
                                arrayAdapterTagNewest1.add((String) newTag);
                                arrayAdapterTagNewest1.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();
                            } else if (catgory.equals(newCatgories.get(1).toString())) {
                                arrayAdapterTagNewest2.add((String) newTag);
                                arrayAdapterTagNewest2.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();


                            } else if (catgory.equals(newCatgories.get(2).toString())) {
                                arrayAdapterTagNewest3.add((String) newTag);
                                arrayAdapterTagNewest3.notifyDataSetChanged();
                                dialogAddNewTag.dismiss();


                            }

                        } else {
                            dialogAddNewTag.dismiss();
                            Toast.makeText(dialogAddNewTag.getContext(), "Please, Select ctagory First....", Toast.LENGTH_LONG).show();
                        }
                    }
                });


                dialogAddNewTag.show();
            }
        });

        btnAddNewCatgory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialogAddNewTag = new Dialog(CatgoryTagActivity.this);
                dialogAddNewTag.setContentView(R.layout.add_new_tag_dialog_layout);

                dialogAddNewTag.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialogAddNewTag.setCancelable(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialogAddNewTag.create();
                }
                dialogAddNewTag.setCanceledOnTouchOutside(true);
                edAddNewTag = (EditText) dialogAddNewTag.findViewById(R.id.edAddNewTag);
                edAddNewTag.setHint("Enter Catgory you wanna to add...");

                btnSubmmitDialogAddingNewTagOrCatgory = (Button) dialogAddNewTag.findViewById(R.id.btnSubmmitDialogAddingNewTagOrCatgory);
                btnSubmmitDialogAddingNewTagOrCatgory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            newCatgory = "" + edAddNewTag.getText();
                            arrayAdapterCatgory.add((String) newCatgory);
                            arrayAdapterCatgory.notifyDataSetChanged();

                            dialogAddNewTag.dismiss();


                        if (newCatgories.size() >= 3) {
                            arrayAdapterCatgory.remove((String) newCatgory);
                            Toast.makeText(getBaseContext(), "You Cant add more than three Catgories", Toast.LENGTH_LONG).show();
                        }
                        newCatgories.add(newCatgory.toString());
                    //    Toast.makeText(dialogAddNewTag.getContext(), newCatgories.toString(), Toast.LENGTH_LONG).show();

                    }
                });
                dialogAddNewTag.show();
            }
        });
Intent  i = getIntent();
        if ( i.getClass().equals(AddService.class)){
            btnSubmmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CatgoryTagActivity.this, AddService.class);
                    intent.putExtra("tagSelected", tagChossen);
                    startActivity(intent);
                }
            });
        }
if (i.getStringExtra("class").equals("SearchServices")) {
    btnSubmmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(CatgoryTagActivity.this, SearchServices.class);
            intent.putExtra("tagSelected", tagChossen);
            startActivity(intent);
        }
    });
}
        if (i.getStringExtra("class").equals("AddService")) {
            btnSubmmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CatgoryTagActivity.this, AddService.class);
                    intent.putExtra("tagSelected", tagChossen);
                    startActivity(intent);
                }
            });
        }
    }

}
