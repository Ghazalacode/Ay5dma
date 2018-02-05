package com.example.agh.grad.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ToggleChip;



public class MyChipCloud extends ChipCloud {
        private ViewGroup layout;


    public MyChipCloud(Context context, ViewGroup layout, ChipCloudConfig config) {
            super(context, layout, config);
            this.layout = layout;
        }



        public List<Integer> getSelectedTagIndex() {
            List<Integer> index = new ArrayList<>();
            for (int i = 0; i < layout.getChildCount(); i++) {
                ToggleChip chip = (ToggleChip) this.layout.getChildAt(i);
                if (chip.isChecked()) {
                    index.add(i);
                }
            }
            return index;
        }

        private int getIndexByView(View view) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = this.layout.getChildAt(i);
                if (child == view) {
                    return i;
                }
            }
            return -1;
        }

        private boolean keepLastOneCheck(View view) {
            List<Integer> selectedTagIndex = getSelectedTagIndex();
            return !(selectedTagIndex.size() == 1 && selectedTagIndex.contains(getIndexByView(view)));
        }

        @Override
        public void onClick(View view) {
            if (!keepLastOneCheck(view)) {
                return;
            }
            super.onClick(view);
        }

      public void deleteChips(){

          layout.removeAllViews();
      }

    public int getChildsCount(){

       return layout.getChildCount();
    }


    }
