package open.gs.com.spyfallgs;

import open.gs.com.spyfallgs.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Random;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SpyFallActivity extends Activity {



    Button addPlayer, shuffle, removePlayer;
    int count = 0;
    private String name_Text = "";
    int spy = 0, player = 0;
    final int MAXPLAYERS = 13;
    Button Player[] = new Button[MAXPLAYERS];
    Random rand = new Random();
    String location;

    String[] locations = { "DRIVING RANGE", "OCEAN LINER", "SPACE STATION", "MOVIE STUDIO", "RESTAURANT",
            "AIRPLANE", "BANK", "BEACH", "BROADWAY THEATRE", "CASINO", "CATHEDRAL", "CIRCUS TENT" , "CORPORATE PARTY", "CRUSADER ARMY", "DAY SPA",
            "EMBASSY", "HOSPITAL", "HOTEL", "MILITARY BASE", "PASSENGER TRAIN", "PIRATE SHIP", "POLAR STATION", "POLICE STATION", "SCHOOL",
            "SERVICE STATION", "SUBMARINE", "SUPERMARKET", "UNIVERSITY","PARKING LOT","SUN", "SKYTOWER",
            "MUNOZ, CLSU", "HOUSE", "CEILING", "ROOFTOP", "PARK", "CAR", "STADIUM", "PRISON", "SPACE", "VOLCANO", "KELLY TARLTONS", "TENNIS COURT",
            "DAIRY", "CAVE", "DESERT", "STRIP CLUB", "EIFFEL TOWER", "HARBOUR BRIDGE", "ZOO", "PYRAMID", "ROTORUA", "BUS", ""

    };



    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = false;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spy_fall);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        removePlayer = (Button) findViewById(R.id.removePlayerButton);
        removePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count == 0){
                    Toast.makeText(SpyFallActivity.this, "No Players to remove", Toast.LENGTH_SHORT).show();
                }else {
                    Player[count].setVisibility(View.GONE);
                    count--;
                }
            }
        });


        addPlayer = (Button) findViewById(R.id.addPlayerButton);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count == (MAXPLAYERS-1)) {
                    Toast.makeText(SpyFallActivity.this,"Max Number of Players Reached",Toast.LENGTH_SHORT).show();

                } else {
                    dialogButtonPress();
                    count++;
                }


            }
        });

        shuffle = (Button) findViewById(R.id.shuffleButton);
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    Toast.makeText(SpyFallActivity.this,"No Players to Shuffle", Toast.LENGTH_SHORT);

                }else{
                int i = rand.nextInt(locations.length);
                try {
                    location = locations[i + 1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.getStackTrace();
                } catch (IllegalArgumentException e1) {
                    e1.getStackTrace();
                }


                player = count;
                spy = rand.nextInt(player) + 1;

                System.out.println("spy " + spy + " players " + player);

                for (int j = 1; j < count + 1; j++) {
                    int g[] = new int[count + 1];
                    g[j] = j;
                    if (g[j] == spy) {
                        Player[g[j]].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(SpyFallActivity.this, "You are a Spy!", Toast.LENGTH_SHORT).show();
                                System.out.println("spy");
                            }
                        });

                    } else {
                        Player[g[j]].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                System.out.println("not spy");
                                playerCard();
                            }
                        });

                    }
                }
            }
            }
        });






    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void dialogButtonPress(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter name");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name_Text = input.getText().toString();
                addPlayerB();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                count--;
                dialog.cancel();
            }
        });

        builder.show();



    }

    public void addPlayerB(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.fullscreen_content_controls);
        layout.setOrientation(LinearLayout.VERTICAL);
            Player[count] = new Button(this);
            Player[count].setText(name_Text);


        layout.addView(Player[count]);



    }


    public void playerCard(){
        System.out.println("Location: " + location);
        Toast.makeText(SpyFallActivity.this,"The location is "+location,Toast.LENGTH_SHORT).show();
    }
}
