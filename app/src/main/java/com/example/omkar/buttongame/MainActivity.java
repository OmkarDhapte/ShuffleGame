package com.example.omkar.buttongame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.example.omkar.buttongame.adapter.ButtonAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    public ArrayList<String> adjacent;
    private GridView gridView;
    public TextView textView;
    public static  int moves=0;
    ButtonAdapter buttonAdapter;
    String row[]={"","",""};
    String column[]={"","","",""};

    public String[][] arr={{"0","4","2"},
            {"3","5","1"},
            {"9","10","7"},
            {"8","6",""}};

    String arr1[][]={{"0","1","2"},
            {"3","4","5"},
            {"6","7","8"},
            {"9","10",""}};
    int xrowpos=0,xcolpos=0;
    String xLeft="",xRight="";
    public String clickText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adjacent=new ArrayList<String>();

        gridView=(GridView)findViewById(R.id.gridView);
        textView=(TextView)findViewById(R.id.textView);
        findAdjacent();

        buttonAdapter=new ButtonAdapter(MainActivity.this,this,arr);
        gridView.setAdapter(buttonAdapter);
    }

    @Override
    public void onBackPressed() {
        moves=0;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void shuffle(String[][] arr, int columns, Random rnd) {
        int size = arr.length * columns;
        Log.e("Array Length",""+arr.length);
        for (int i = size; i > 1; i--)
            swapShuffle(arr, columns, i - 1, rnd.nextInt(i));
    }

    /**
     * Swaps two entries in a 2D array, where i and j are 1-dimensional indexes, looking at the
     * array from left to right and top to bottom.
     */
    public void swapShuffle(String[][] arr, int columns, int i, int j) {
        String tmp = arr[i / columns][i % columns];
        arr[i / columns][i % columns] = arr[j / columns][j % columns];
        arr[j / columns][j % columns] = tmp;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.shuffleButton){
            shuffle(arr, 3, new Random());
            buttonAdapter.nofityChange(arr);
            adjacent.removeAll(adjacent);
            /*buttonAdapter = new ButtonAdapter(MainActivity.this, this,arr);
            gridView.setAdapter(buttonAdapter);
            adjacent=new ArrayList<String>();*/
            findAdjacent();
            checkWin();
            moves=0;
            textView.setText("Moves : "+moves);

        }
        else {    //Set Number

            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    arr[i][j] = arr1[i][j];
                }
            }
            buttonAdapter.nofityChange(arr);
            adjacent.removeAll(adjacent);
            findAdjacent();
            checkWin();
            moves=0;
            textView.setText("Moves : "+moves);
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkWin() {
        if(Arrays.deepEquals(arr, arr1)){
            Log.e("You Win!!", " You Win !!");
            AlertDialog.Builder alBuilder=new AlertDialog.Builder(this);
            alBuilder.setTitle("Suffle Game");
            alBuilder.setMessage("Congratulation ! You Win");
            alBuilder.setIcon(R.drawable.ic_123);
            alBuilder.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    shuffle(arr, 3, new Random());
                    buttonAdapter.nofityChange(arr);
                    adjacent.removeAll(adjacent);
                    findAdjacent();
                    checkWin();
                    moves = 0;
                    textView.setText("Moves : " + moves);
                }
            });
            alBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alBuilder.show();
        }
    }

    public void Swap(){

        String temp;
        int clickbtnrowpos=0,clickbtncolpos=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<3;j++) {
                    if(arr[i][j].equals(clickText)){
                        clickbtnrowpos=i;
                        clickbtncolpos=j;
                    }
                }
            }
            temp=arr[xrowpos][xcolpos];
            arr[xrowpos][xcolpos]=arr[clickbtnrowpos][clickbtncolpos];
            arr[clickbtnrowpos][clickbtncolpos]=temp;
   }


    public void findAdjacent() {


        //Find Position of X
        for(int i=0;i<4;i++) {
            for(int j=0;j<3;j++) {
                if(arr[i][j].equals("")) {
                    xrowpos=i;
                    xcolpos=j;
                    break;
                }
            }
        }

        // Fill Row Element of X
        for(int i=0;i<row.length;i++) {
            row[i]=arr[xrowpos][i];
        }
        //Fill Column Element of X
        for(int i=0;i<column.length;i++) {
            column[i]=arr[i][xcolpos];
        }

        //Adjacent Element of X in Row
        for(int i=0;i<row.length;i++) {
            if(row[i]=="") {
                if(i-1>=0) {
                    xLeft = row[i - 1];
                    adjacent.add(xLeft);
                }
                if(i+1<row.length) {
                    xRight = row[i + 1];
                    adjacent.add(xRight);
                }
            }
        }
        //Adjacent Element of X in Column
        for(int i=0;i<column.length;i++) {
            if(column[i]=="") {
                if(i-1>=0){
                    xLeft=column[i-1];
                    adjacent.add(xLeft);

                }
                if(i+1<column.length) {
                    xRight = column[i + 1];
                    adjacent.add(xRight);
                }
            }
        }
    }
}
