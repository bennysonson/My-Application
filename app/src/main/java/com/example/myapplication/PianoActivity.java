package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Piano with two octaves. Standard grand piano sound, up to 6 notes can be layered.
 * Current issue with lower octave playing with static.
 */
public class PianoActivity extends AppCompatActivity {

    private final static int MAX_STREAMS = 6;
    private SoundPool sp;
    private int middleCNote, highCNote, middleCSharpNote, highCSharpNote, middleDNote, highDNote, middleDSharpNote, highDSharpNote, middleENote,
            highENote, middleFNote, highFNote, middleFSharpNote, highFSharpNote, middleGNote, highGNote, middleGSharpNote, highGSharpNote,
            middleANote, highANote, middleASharpNote, highASharpNote, middleBNote, highBNote;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piano);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes at = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            sp = new SoundPool.Builder()
                    .setMaxStreams(MAX_STREAMS)
                    .setAudioAttributes(at)
                    .build();
        } else {
            sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        middleCNote = sp.load(this, R.raw.cmiddle, 1);
        middleCSharpNote = sp.load(this, R.raw.csharpmiddle, 1);
        middleDNote = sp.load(this, R.raw.dmiddle, 1);
        middleDSharpNote = sp.load(this, R.raw.dsharpmiddle, 1);
        middleENote = sp.load(this, R.raw.emiddle, 1);
        middleFNote = sp.load(this, R.raw.fmiddle, 1);
        middleFSharpNote = sp.load(this, R.raw.fsharpmiddle, 1);
        middleGNote = sp.load(this, R.raw.gmiddle, 1);
        middleGSharpNote = sp.load(this, R.raw.gsharpmiddle, 1);
        middleANote = sp.load(this, R.raw.amiddle, 1);
        middleASharpNote = sp.load(this, R.raw.asharpmiddle, 1);
        middleBNote = sp.load(this, R.raw.bmiddle, 1);
        highCNote = sp.load(this, R.raw.chigh, 1);
        highCSharpNote = sp.load(this, R.raw.csharphigh, 1);
        highDNote = sp.load(this, R.raw.dhigh, 1);
        highDSharpNote = sp.load(this, R.raw.dsharphigh, 1);
        highENote = sp.load(this, R.raw.ehigh, 1);
        highFNote = sp.load(this, R.raw.fhigh, 1);
        highFSharpNote = sp.load(this, R.raw.fsharphigh, 1);
        highGNote = sp.load(this, R.raw.ghigh, 1);
        highGSharpNote = sp.load(this, R.raw.gsharphigh, 1);
        highANote = sp.load(this, R.raw.ahigh, 1);
        highASharpNote = sp.load(this, R.raw.asharphigh, 1);
        highBNote = sp.load(this, R.raw.bhigh, 1);

    }

    public void playSound(View v) {
        switch (v.getId()) {
            case R.id.middleCKey:
                sp.play(middleCNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleCSharpKey:
                sp.play(middleCSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleDKey:
                sp.play(middleDNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleDSharpKey:
                sp.play(middleDSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleEKey:
                sp.play(middleENote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleFKey:
                sp.play(middleFNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleFSharpKey:
                sp.play(middleFSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleGKey:
                sp.play(middleGNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleGSharpKey:
                sp.play(middleGSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleAKey:
                sp.play(middleANote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleASharpKey:
                sp.play(middleASharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.middleBKey:
                sp.play(middleBNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highCKey:
                sp.play(highCNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highCSharpKey:
                sp.play(highCSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highDKey:
                sp.play(highDNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highDSharpKey:
                sp.play(highDSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highEKey:
                sp.play(highENote, 1, 1, 0, 0, 1);
                break;
            case R.id.highFKey:
                sp.play(highFNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highFSharpKey:
                sp.play(highFSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highGKey:
                sp.play(highGNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highGSharpKey:
                sp.play(highGSharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highAKey:
                sp.play(highANote, 1, 1, 0, 0, 1);
                break;
            case R.id.highASharpKey:
                sp.play(highASharpNote, 1, 1, 0, 0, 1);
                break;
            case R.id.highBKey:
                sp.play(highBNote, 1, 1, 0, 0, 1);
                break;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        sp.release();
        sp = null;
    }
}
