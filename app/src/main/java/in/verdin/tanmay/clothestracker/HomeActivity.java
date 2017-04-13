package in.verdin.tanmay.clothestracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public void addItemButton(View view){
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);

    }

    public void statusButton(View view){
        Intent intent = new Intent(this, CurrentStatus.class);
        startActivity(intent);
    }
}
