package com.example.mobilehomework;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private int[] mList;
    private Context mContext;

    private AdapterMeasureHelper mCardAdapterHelper = new AdapterMeasureHelper();

    public MyAdapter(Context ctx, int[] mList) {
        this.mList = mList;
        this.mContext = ctx;
    }

    public MyAdapter(Context applicationContext, List<String> parentList, Map<String, List<String>> map) {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());

        holder.iv_big.setImageResource(mList[position]);
        holder.iv_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GalleryActivity g = new GalleryActivity();
                switch (position) {
                    case 0:
                        GalleryActivity.instance.show("你选择的是柠檬", "所含热量为37kcal/100g");
                        break;
                    case 1:
                        GalleryActivity.instance.show("你选择的是西红柿","所含热量为18kcal/100g");
                        break;
                    case 2:
                        GalleryActivity.instance.show("你选择的是葡萄","所含热量为45kcal/100g");
                        break;
                    case 3:
                        GalleryActivity.instance.show("你选择的是巧克力","所含热量为574kcal/100g");
                        break;
                    case 4:
                        GalleryActivity.instance.show("你选择的是橘子","所含热量为44kcal/100g");
                        break;
                    case 5:
                        GalleryActivity.instance.show("你选择的是玉米","所含热量为112kcal/100g");
                        break;
                    case 6:
                        GalleryActivity.instance.show("你选择的是啤酒","所含热量为43kcal/100g");
                        break;
                        default:
                            GalleryActivity.instance.show("你选择的是葡萄","所含热量为45kcal/100g");
                            break;
                }
                }
            });
        }

        @Override
        public int getItemCount () {
            return mList.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_big;

            public ViewHolder(final View view) {
                super(view);
                iv_big = (ImageView) view.findViewById(R.id.iv_big);
            }
        }
    }
