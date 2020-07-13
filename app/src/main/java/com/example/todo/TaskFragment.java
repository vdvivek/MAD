package com.example.todo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener taskListener;
    private EditText text;
    private LinearLayout layout;
    private List<View> cards = new ArrayList<>();
    private List<String> checkboxes = new ArrayList<>();
    Contracts.ContractDBHelper dbHelper;
    private Button add_btn;


    public TaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        text = v.findViewById(R.id.task_name);
        layout = v.findViewById(R.id.l_layout);
        text.setOnClickListener(this);
        add_btn = v.findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTask();
            }
        });
        dbHelper = new Contracts.ContractDBHelper(getContext());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                Contracts.TasksContract.task_name,
                Contracts.TasksContract.isChecked};

        Cursor cursor = db.query(
                Contracts.TasksContract.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Integer> l = new ArrayList<>();
        while (cursor.moveToNext())
        {
            String s = cursor.getString(cursor.getColumnIndexOrThrow(Contracts.TasksContract.task_name));
            checkboxes.add(s);
            int a = cursor.getInt(cursor.getColumnIndexOrThrow(Contracts.TasksContract.isChecked));
            l.add(a);
        }
        cursor.close();

        for(int i = 0; i < checkboxes.size(); i++)
        {
            LayoutInflater inflater= getLayoutInflater();
            View v = inflater.inflate(R.layout.cards,layout, false);
            CheckBox cb = v.findViewById(R.id.cbox);
            cb.setText(checkboxes.get(i));
            cb.setTextSize(20);
            cb.setOnClickListener(this);

            if(l.get(i) == 1) {
                onClick(cb);
                cb.setChecked(true);
            }
            v.findViewById(R.id.del_btn).setOnClickListener(this);
            layout.addView(v);
            cards.add(v);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            taskListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + "must implement TaskFragment Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        taskListener = null;
    }

    public void AddTask()
    {

        String str = text.getText().toString();
        if(!str.isEmpty())
        {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.cards,layout,false);
            CheckBox cb = v.findViewById(R.id.cbox);
            cb.setTextSize(20);
            cb.setText(str);
            cb.setOnClickListener(this);
            v.findViewById(R.id.del_btn).setOnClickListener(this);
            layout.addView(v);
            cards.add(v);
            text.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CheckBox)
            ((CheckBox) v).setPaintFlags(((CheckBox) v).getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
        else if (v instanceof Button) {
            for(int i = 0; i < cards.size(); i++)
                if(cards.get(i).findViewById(R.id.del_btn) == v)
                {
                    layout.removeView(cards.get(i));
                    cards.remove(i);
                    break;
                }
        }
    }

    @Override
    public void onStop() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Contracts.TasksContract.TABLE_NAME, null, null);
        ContentValues values = new ContentValues();
        for (int i = 0; i < cards.size(); i++){
            CheckBox cb = cards.get(i).findViewById(R.id.cbox);
            int checked = cb.isChecked()?1:0;
            values.put(Contracts.TasksContract.isChecked, checked);
            values.put(Contracts.TasksContract.task_name,  cb.getText().toString());
            long a = db.insert(Contracts.TasksContract.TABLE_NAME, null, values);
            Log.d("Fragger", "a = " + a);
        }
        db.close();
        super.onStop();
    }

    public interface OnFragmentInteractionListener {
    }
}
