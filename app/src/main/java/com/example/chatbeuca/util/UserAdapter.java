package com.example.chatbeuca.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

import com.example.chatbeuca.R;
import com.example.chatbeuca.database.model.User;

public class UserAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final int resource;
    private final List<User> users;
    private final LayoutInflater inflater;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.users = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        User user = users.get(position);
        if (user != null) {
            addDate(view, user.getDate());
            addCategory(view, user.getCategory());
            addPrice(view, user.getTime());
        }
        return view;
    }

    private void addDate(View view, Date date) {
        TextView textView = view.findViewById(R.id.tv_lv_users_row_date);
        addTextViewContent(textView, DateConverter.fromDate(date));
    }

    private void addCategory(View view, String category) {
        TextView textView = view.findViewById(R.id.tv_lv_users_row_category);
        addTextViewContent(textView, category);
    }

    private void addPrice(View view, Double price) {
        TextView textView = view.findViewById(R.id.tv_lv_users_row_tine);
        String value = null;
        if (price != null) {
            value = context.getString(R.string.lv_user_row_amount_value, price.toString());
        }
        addTextViewContent(textView, value);
    }

    private void addTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_user_row_amount);
        }
    }
}
