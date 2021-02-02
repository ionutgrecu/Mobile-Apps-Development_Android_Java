package com.example.chatbeuca;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;


public class LoginActivity extends AppCompatActivity {
    final static String APP_ID = "290BE9BF-1434-40CD-86D1-0EEE96844F3D"; // Aplicatia app
    private Button mConnectButton;
    private Button mMainActivity;
    private TextInputEditText mUserIdEditText, mUserNicknameEditText;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPrefs = getSharedPreferences("label", 0);
        mConnectButton = (Button) findViewById(R.id.button_login);
        mMainActivity =(Button) findViewById(R.id.button_add);
        mUserIdEditText = (TextInputEditText) findViewById(R.id.edit_text_login_user_id);
        mUserNicknameEditText = (TextInputEditText) findViewById(R.id.edit_text_login_user_nickname);
        String savedUserNickname = "mihai";
        String savedUserID = mPrefs.getString("userId", "mihai");
        mUserIdEditText.setText(savedUserID);
        mUserNicknameEditText.setText(savedUserNickname);
        SendBird.init(APP_ID, this.getApplicationContext());
        mMainActivity.setOnClickListener(LaunchRegForm());


        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = mUserIdEditText.getText().toString();
                userId = userId.replaceAll("\\s", "");

                String userNickname = mUserNicknameEditText.getText().toString();
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("userId", userId).apply();
                mEditor.putString("userNickName", userNickname).commit();
                connectToSendBird(userId, userNickname);
            }
        });

    }
    private View.OnClickListener LaunchRegForm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deschidere activitate
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(intent);
            }
        };
    }
    private View.OnClickListener LaunchCVForm() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deschidere activitate
                Intent intent = new Intent(getApplicationContext(), BNRActivity.class);
                startActivity(intent);
            }
        };
    }


    /**
     * Date conectare utlizator SendBird API.
     * @param userId : mihai/andrei
     * @param userNickname : mihai/andrei
     *  Ce rol are porecla (nickname-ul)? - este numele ce va aparea deasupra mesajului.
     */
    private void connectToSendBird(final String userId, final String userNickname) {
        mConnectButton.setEnabled(false);

        SendBird.connect(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    mConnectButton.setEnabled(true);
                    return;
                }

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);

                Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                intent.putExtra("userID", userId);
                intent.putExtra("userNickname", userNickname);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Actualizam nick-ul
     * @param userNickname noul nick.
     */
    private void updateCurrentUserInfo(String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            LoginActivity.this, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                }

            }
        });
    }

}