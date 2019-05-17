package com.example.lab6;


import android.content.SharedPreferences;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class List extends AppCompatActivity implements MyRecycleViewAdapter.OnRecordingItemClickListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
MyMergeAlertDialog.MergeAlertDialogListener{

    //TODO odtwarzanie przy kliknięciu -> guziki pauzy, wznowienia, zakonczenia


    private MediaPlayer player;


    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private int minimumBufferSize;

    private RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;


    private ArrayList<RecordingData> recordings;
    Type recordingType;


    private int mainRecordingPosition;
    private int subRecordingPosition;
    private TextView mainTitleTextView;
    private TextView subTitleTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        this.mainTitleTextView = (TextView) findViewById(R.id.main_to_merge);
        this.subTitleTextView = (TextView) findViewById(R.id.second_to_merge);

        this.minimumBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);

        this.sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        this.prefEditor = sharedPref.edit();
        this.gson = new Gson();

        ArrayList<String> defaultList = new ArrayList<>();
        String jsonDefaultList = gson.toJson(defaultList);


        this.recordingType = new TypeToken<ArrayList<RecordingData>>(){}.getType();
        this.recordings = gson.fromJson(sharedPref.getString(getString(R.string.recirding_list_emblem),jsonDefaultList), recordingType);


        this.recyclerView = findViewById(R.id.recycle_view);
        this.adapter = new MyRecycleViewAdapter(this.recordings, this, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MySwipeToDeleteCallback(adapter, getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    } // consctuctor

    @Override
    protected void onPause() {
        super.onPause();
        prefEditor.clear();
        String jsonRecordingsList = gson.toJson(this.recordings);

        prefEditor.putString(getString(R.string.recirding_list_emblem), jsonRecordingsList);
        prefEditor.commit();
    }

    public void onMergeButton(View v) {
        MyMergeAlertDialog alertDialog = new MyMergeAlertDialog();
        alertDialog.show(getSupportFragmentManager(), "merge_alert_dialog");
    }

    @Override
    public void merge() {
        CombineWaveFile();
    }

    private void CombineWaveFile() {
        FileInputStream in1 = null, in2 = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = RECORDER_SAMPLERATE;
        int channels = 1;
        int RECORDER_BPP = 16;
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;
        byte[] data = new byte[minimumBufferSize];

        try {



            String path0 = recordings.get(mainRecordingPosition).wavPath;
            String path1 = recordings.get(subRecordingPosition).wavPath;
            String newPath = getNewFilePath() + ".wav" ;
            File ffile0 = new File(path0);
            File ffile1 = new File(path1);

            ffile0.setReadable(true,false);
            ffile1.setReadable(true,false);

            in1 = new FileInputStream(ffile0);
            in2 = new FileInputStream(ffile1);

            File result = new File(newPath);






            out = new FileOutputStream(result);
            totalAudioLen = in1.getChannel().size() + in2.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);
            while (in1.read(data) != -1) {

                out.write(data);

            }
            while (in2.read(data) != -1) {

                out.write(data);
            }
            out.close();
            in1.close();
            in2.close();



            ffile0.delete();
            ffile1.delete();







            RecordingData mainRecrding = recordings.get(mainRecordingPosition);

            mainRecrding.wavPath = newPath;

            recordings.remove(subRecordingPosition);

            adapter.notifyDataSetChanged();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getNewFilePath() {
        String currentDate = getCurrentDateForPath();
        String filePath = this.getFilesDir().getAbsolutePath() + "/nota"
                + Integer.toString(recordings.size()) + "_" + currentDate;
        return filePath;
    }
    private String getCurrentDateForPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,
                                     long totalDataLen, long longSampleRate, int channels, long byteRate)
            throws IOException {
        int RECORDER_BPP = 16;
        byte[] header = new byte[44];

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte)(totalDataLen & 0xff);
        header[5] = (byte)((totalDataLen >> 8) & 0xff);
        header[6] = (byte)((totalDataLen >> 16) & 0xff);
        header[7] = (byte)((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte)(longSampleRate & 0xff);
        header[25] = (byte)((longSampleRate >> 8) & 0xff);
        header[26] = (byte)((longSampleRate >> 16) & 0xff);
        header[27] = (byte)((longSampleRate >> 24) & 0xff);
        header[28] = (byte)(byteRate & 0xff);
        header[29] = (byte)((byteRate >> 8) & 0xff);
        header[30] = (byte)((byteRate >> 16) & 0xff);
        header[31] = (byte)((byteRate >> 24) & 0xff);
        header[32] = (byte)(2 * 16 / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte)(totalAudioLen & 0xff);
        header[41] = (byte)((totalAudioLen >> 8) & 0xff);
        header[42] = (byte)((totalAudioLen >> 16) & 0xff);
        header[43] = (byte)((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }









    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public void pause(View v) {
        if (player != null) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    public void onRecordingClick(int position) {
        if (player == null) {
            String path = recordings.get(position).wavPath;
            File audioFile = new File(path);

            audioFile.setReadable(true, false);

            Log.d("audioFileLog", "File exists: " + audioFile.exists() + ", can read: " + audioFile.canRead());
            Log.d("audioFileLog", "File size: " + audioFile.length());

            player = new MediaPlayer();
            try {
                player.setDataSource(path);
                //player = MediaPlayer.create(this, R.raw.nota1);
                Log.d("audioFileLog", "Data Source Changed");
                player.setOnPreparedListener(this);
                Log.d("audioFileLog", "Listener Set, before prepare method");
                player.setOnCompletionListener(this);

                //todo cos nie tak z metoda prepare - prepare failed status=0x1 - przez foramt wav?
                player.prepare();
                Log.d("audioFileLog", "after prepare method");
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d("audioFileLog", "IOException when setting data source");
            }
            player.start();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Log.d("audioFileLog", "after start method");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        stopPlayer();
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@












    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 121:
                this.mainRecordingPosition = item.getGroupId();
                this.mainTitleTextView.setText(recordings.get(mainRecordingPosition).title);
                return true;
            case 122:
                this.subRecordingPosition = item.getGroupId();
                this.subTitleTextView.setText(recordings.get(subRecordingPosition).title);
                return true;
            default:
                return super.onContextItemSelected(item);
        }// switch
    }


} // class


























