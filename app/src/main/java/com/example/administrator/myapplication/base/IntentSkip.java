package com.example.administrator.myapplication.base;

import android.app.Activity;
import android.content.Intent;
import com.example.administrator.myapplication.R;
import java.io.Serializable;

/**
 * Intent 跳转
 * */
public class IntentSkip {

    public static final String INTENT_BUILD = "intent_build";

    public static void startIntent(Activity mainInterface , Activity targetActivity , Serializable build){
        Intent intent = new Intent(mainInterface , targetActivity.getClass());
        if (build != null){
            intent.putExtra(INTENT_BUILD , build);
        }
        mainInterface.startActivity(intent);
        mainInterface.overridePendingTransition(R.anim.slide_in_right , R.anim.slide_in_right);
    }

}
