package org.elis.jp4application;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter {


    private LayoutInflater mInflter;
    private ArrayList<Food> data;



    private OnQuantityChange onQuantityChange;


    public interface OnQuantityChange{
        public void onItemAdded(float price);
        public void onItemRemoved(float price);

    }

    public FoodListAdapter(Context context){
        mInflter = LayoutInflater.from((context));
    }

    public void setOnQuantityChange(OnQuantityChange onQuantityChange) {
        this.onQuantityChange = onQuantityChange;
    }



    public FoodListAdapter(Context context, ArrayList<Food> data){

        this.data = data;
        mInflter = LayoutInflater.from(context);


    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = mInflter.inflate(R.layout.row_item,viewGroup,false);
        return new FoodViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FoodViewHolder foodViewHolder = (FoodViewHolder)viewHolder;
        Food currentFood = data.get(i);
        foodViewHolder.productName.setText(currentFood.getName());
        foodViewHolder.productPrice.setText(String.valueOf(currentFood.getPrice()));


        foodViewHolder.productQty.setText(String.valueOf(currentFood.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void addItem(){

    }

    private void removeItem(){


    }


    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView productName,productPrice,productQty;
        public Button addBtn,removeBtn;


        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.item_name);
            productPrice = itemView.findViewById(R.id.item_price);


            productQty = itemView.findViewById(R.id.item_picker)
                    .findViewById(R.id.quantity_tv);

            addBtn = itemView.findViewById(R.id.item_picker).
                    findViewById(R.id.add_btn);

            removeBtn = itemView.findViewById(R.id.item_picker)
                    .findViewById(R.id.remove_btn);

            addBtn.setOnClickListener(this);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.add_btn){
                Food food = data.get(getAdapterPosition());
                food.increaseQuantity();
                notifyItemChanged(getAdapterPosition());

                onQuantityChange.onItemAdded(food.getPrice());


            }else if(view.getId() == R.id.remove_btn){
                Food food = data.get(getAdapterPosition());
                food.decreaseQuantity();
                notifyItemChanged(getAdapterPosition());

                onQuantityChange.onItemRemoved(food.getPrice());


            }

        }
    }
}