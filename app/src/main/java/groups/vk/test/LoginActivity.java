package groups.vk.test;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import groups.vk.test.api.model.request.VKUserObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    public static final int VK_AUTH_ACTIVITY_INTENT_CODE = 1111;

    private ActionBar mActionBar = null;

    private AppCompatButton mAppCompatButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log.i(TAG, "VK SHA1 : " + Arrays.toString(VKUtil.getCertificateFingerprint(getApplicationContext(), LoginActivity.this.getPackageName())));

        setContentView(R.layout.activity_login);

        try {
            mActionBar = getSupportActionBar();
        } catch (NullPointerException e){
            Log.e(TAG, "Exeption : " + e.getMessage());
        }

        if(mActionBar != null){
            mActionBar.setTitle(getApplicationContext().getResources().getString(R.string.login_activity_title));
        }

        this.mAppCompatButton = (AppCompatButton) findViewById(R.id.btn_vk_login);

        Account[] accounts = AccountManager.get(this).getAccountsByType("com.vkontakte.account");

        if (accounts.length > 0) {
            String buttonText = getString(R.string.vk_button, accounts[0].name);

            mAppCompatButton.setText(buttonText);
            mAppCompatButton.setVisibility(View.VISIBLE);
            mAppCompatButton.setOnClickListener(onClickVKListener);
        }
    }

    View.OnClickListener onClickVKListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent("com.vkontakte.android.action.SDK_AUTH", null)
                    .putExtra("version", "5")
                    .putExtra("client_id", 5316702)
                    .putExtra("scope", "friends, groups");

            startActivityForResult(intent, VK_AUTH_ACTIVITY_INTENT_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == VK_AUTH_ACTIVITY_INTENT_CODE && data != null && data.hasExtra("access_token")) {
            String accessToken = data.getStringExtra("access_token");

            int userId = data.getIntExtra("user_id", 0);

            Log.i(TAG, "AccessToken: " + accessToken + "\nuserId: " + userId);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(MainActivity.EXTRA_VK_USER_OBJECT, new VKUserObject(userId, accessToken));

            startActivity(intent);

            LoginActivity.this.finish();
        }
    }
}
