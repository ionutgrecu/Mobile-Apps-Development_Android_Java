package com.example.chatbeuca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.asyncTask.Callback;
import com.example.chatbeuca.database.model.User;
import com.example.chatbeuca.database.service.UserService;
import com.example.chatbeuca.util.UserAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    private static final int ADD_USER_REQUEST_CODE = 201;
    private static final int UPDATE_USER_REQUEST_CODE = 222;


    private ListView lvUsers;
    private FloatingActionButton fabAddUser;
    private FloatingActionButton fabProfile;
    private FloatingActionButton fabProfileDB;

    private final List<User> users = new ArrayList<>();

    private UserService userService;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initComponents();
        userService = new UserService(getApplicationContext());
        userService.getAll(getAllCallback());
    }

    private Callback<List<User>> getAllCallback() {
        return new Callback<List<User>>() {
            @Override
            public void runResultOnUiThread(
                    List<User> result) {
                if (result != null) {
                    users.clear();
                    users.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<User> insertCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if (result != null) {
                    users.add(result);
                    notifyAdapter();
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.insert_failed_message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private Callback<User> updateCallback() {
        return new Callback<User>() {
            @Override
            public void runResultOnUiThread(User result) {
                if (result != null) {
                    for (User user : users) {
                        if (user.getId() == result.getId()) {
                            user.setDescription(result.getDescription());
                            user.setTime(result.getTime());
                            user.setCategory(result.getCategory());
                            user.setDate(result.getDate());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Integer> deleteCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    users.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_USER_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            User user = (User) data.getSerializableExtra(AddUserActivity.USER_KEY);
            userService.insert(user, insertCallback());
            //            if (user != null) {
//                users.add(user);
//                notifyAdapter();
//            }
        } else if (requestCode == UPDATE_USER_REQUEST_CODE
                && resultCode == RESULT_OK
                && data != null) {
            User user = (User) data
                    .getSerializableExtra(AddUserActivity.USER_KEY);
            userService.update(user, updateCallback());
        }
    }

    private void initComponents() {
        lvUsers = findViewById(R.id.lv_main_users);
        fabAddUser = findViewById(R.id.fab_main_add_users);
        fabProfile = findViewById(R.id.fab_main_profile);
        fabProfileDB = findViewById(R.id.fab_main_profile_db);
        addAdapter();
        fabAddUser.setOnClickListener(addUserEventListener());
        fabProfileDB.setOnClickListener(addUserDBListener());
        lvUsers.setOnItemClickListener(updateUserEventListener());
        lvUsers.setOnItemLongClickListener(deleteUserEventListener());

        fabProfile.setOnClickListener(profileEventListener());

        sharedPreferences = getSharedPreferences(ProfileActivity.PROFILE_SHARED, MODE_PRIVATE);
        displayMessage();
    }

    private void displayMessage() {
        String name = sharedPreferences.getString(ProfileActivity.NAME, null);
        if (name != null) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.display_param_message, name),
                    Toast.LENGTH_SHORT)
                    .show();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity3.this)
                    .setTitle(R.string.main_my_title)
                    .setMessage(getString(R.string.display_param_message, name))
                    .create();
            dialog.show();
        }
    }

    private View.OnClickListener profileEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ProfileActivity.class);
                startActivity(intent);
            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteUserEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           int position,
                                           long id) {
                userService.delete(users.get(position),
                        deleteCallback(position));
                return true;
            }
        };
    }

    private AdapterView.OnItemClickListener updateUserEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(),
                        AddUserActivity.class);
                intent.putExtra(AddUserActivity.USER_KEY,
                        users.get(position));
                startActivityForResult(intent, UPDATE_USER_REQUEST_CODE);

            }
        };
    }

    private View.OnClickListener addUserEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivityForResult(intent, ADD_USER_REQUEST_CODE);
            }
        };
    }
    private View.OnClickListener addUserDBListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        };
    }

    private void addAdapter() {
//        ArrayAdapter<User> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, expens);
        UserAdapter adapter = new UserAdapter(getApplicationContext(), R.layout.lv_user_row,
                users, getLayoutInflater());
        lvUsers.setAdapter(adapter);
    }

    private void notifyAdapter() {
        UserAdapter adapter = (UserAdapter) lvUsers.getAdapter();
//        ArrayAdapter<User> adapter = (ArrayAdapter<User>) lvExpenses.getAdapter();
        adapter.notifyDataSetChanged();
    }
}