package com.example.omkar.buttongame.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.omkar.buttongame.MainActivity;
import com.example.omkar.buttongame.R;

/**
 * Created by Omkar on 10-Jul-15.
 */
public class ButtonAdapter extends BaseAdapter {

    Context context;
    MainActivity mainActivity;
    String gridContent[][];
    int i=0,j=0;
    int count=0;

    public ButtonAdapter(MainActivity mainActivity,Context context,String arr[][]){
        this.context=context;
        this.mainActivity=mainActivity;
        copyArray(arr);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Button button=null;

        if (convertView==null)
            button=new Button(context);
        else
            button=(Button)convertView;

        int ypos=position%gridContent[0].length;
        int xpos= (position-ypos)/gridContent[0].length;
        button.setTextSize(40);
        button.setBackgroundResource(R.drawable.button_cust);

        if (gridContent[xpos][ypos].equals("")) {
            button.setBackgroundResource(R.drawable.x_button);
        }
        for (i=0;i<mainActivity.adjacent.size();i++){
            if (gridContent[xpos][ypos].equals(mainActivity.adjacent.get(i))){
                button.setBackgroundResource(R.drawable.adjacent);
            }
        }
        button.setText(gridContent[xpos][ypos]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button button1 = (Button) v;
                int flag = 0;
                mainActivity.clickText = button1.getText().toString();
                for (i = 0; i < mainActivity.adjacent.size(); i++) {
                    if (mainActivity.clickText.equals(mainActivity.adjacent.get(i))) {
                        flag = 1;
                    }
                }
                if (flag == 1) {
                    mainActivity.moves++;
                    mainActivity.textView.setText("Moves : " + MainActivity.moves);
                    mainActivity.Swap();         //Swap Button with x
                    nofityChange(mainActivity.arr);
                    mainActivity.adjacent.removeAll(mainActivity.adjacent);

                    mainActivity.findAdjacent();
                    mainActivity.checkWin();
                }
            }
        });
        return button;
    }
    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void nofityChange(String arr[][]){
        copyArray(arr);
        notifyDataSetChanged();
    }

    private void copyArray(String arr[][]){
        count=0;
        gridContent = new String[arr.length][arr[0].length];
        for (i = 0; i < gridContent.length; i++)
        {
            for (j = 0; j < gridContent[i].length; j++)
            {
                gridContent[i][j] = arr[i][j];
                count++;
            }
        }
    }
}
