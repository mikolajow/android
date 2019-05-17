package com.example.lab6;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.icu.text.UnicodeSetSpanner;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //todo ucinanie fragmentow ciszy - LinkedBlockingQueue jako kolejka FIFO -> runOnUiThread - cosik do wielowatkowosci
    //todo badanie głośności -> progres bar (opcjonalnie)

    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;



    private ArrayList<RecordingData> recordings;
    Type recordingType;

    private boolean recordingStopped;
    private String temporaryFilepath;
    private String temporaryFileName;


    private SharedPreferences sharedPref;
    private SharedPreferences.Editor prefEditor;
    private Gson gson;




    private AudioRecord recorder;
    private Thread recordingThread;
    private boolean isRecording = false;

    private int bytesPerElement = 2;        // 2 bytes in 16bit format
    private int minimumBufferSize;        // 2 bytes in 16bit format


    private Button startButton;
    private Button stopButton;
    private Button saveButton;
    private Button resetButton;

    private EditText titleInput;
    private EditText nameInput;
    private EditText surnameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startButton = (Button) findViewById(R.id.start_button);
        this.stopButton = (Button) findViewById(R.id.stop_button);
        this.saveButton = (Button) findViewById(R.id.save_button);
        this.resetButton = (Button) findViewById(R.id.delete_button);

        this.titleInput = (EditText) findViewById(R.id.title_edit_text);
        this.nameInput = (EditText) findViewById(R.id.name_edit_text);
        this.surnameInput = (EditText) findViewById(R.id.surrname_edit_text);

        this.saveButton.setEnabled(false);
        this.stopButton.setEnabled(false);
        this.resetButton.setEnabled(false);

        this.minimumBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);

        this.recordingStopped = false;

        this.sharedPref = this.getSharedPreferences(getString(R.string.preferences), MODE_PRIVATE);
        this.prefEditor = sharedPref.edit();

        this.gson = new Gson();
        ArrayList<String> defaultList = new ArrayList<>();
        String jsonDefaultList = gson.toJson(defaultList);


        this.recordingType = new TypeToken<ArrayList<RecordingData>>(){}.getType();


        this.recordings = gson.fromJson(sharedPref.getString(getString(R.string.recirding_list_emblem),jsonDefaultList), recordingType);
    }

    public void onButtonsClicked(View view) {
        switch(view.getId()) {
            case R.id.start_button: {
                startRecording();
                break;
            }
            case R.id.stop_button: {
                stopRecording();
                break;
            }
            case R.id.save_button: {
                saveRecording();
                break;
            }
            case R.id.delete_button: {
                deleteTemporaryRecording();
                break;
            }
            case R.id.list_button: {
                requestReadExternalStoragePermission();
                startActivity(new Intent(this, List.class));
                break;
            }
        } // switch
    }

    private void startRecording() {

        requestRecordAudioPermission();

        this.recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, minimumBufferSize * bytesPerElement);

        startButton.setEnabled(false);
        startButton.setBackgroundColor(getColor(R.color.green));
        stopButton.setEnabled(true);
        stopButton.setBackgroundColor(getColor(R.color.red));

        this.recorder.startRecording();
        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    private void writeAudioDataToFile() {
        // Write the output audio in byte
        short sData[] = new short[minimumBufferSize];

        int fileMode = MODE_PRIVATE;
        if(recordingStopped) {
            fileMode = MODE_APPEND;
        } else {
            this.temporaryFilepath = getNewFilePath();
        }


        try(FileOutputStream os = openFileOutput(this.temporaryFileName + ".pcm", fileMode)) {

            while (isRecording) {
                // gets the voice output from microphone to byte format
                recorder.read(sData, 0, minimumBufferSize, AudioRecord.READ_BLOCKING);
                byte bData[] = short2byte(sData);
                os.write(bData, 0, minimumBufferSize * bytesPerElement);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void stopRecording() {
        // stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
            recordingStopped = true;
        }
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        resetButton.setEnabled(true);
        saveButton.setEnabled(true);

        startButton.setBackgroundResource(android.R.drawable.btn_default);;
        stopButton.setBackgroundResource(android.R.drawable.btn_default);;
    }

    private void deleteTemporaryRecording() {
        recordingStopped = false;
        resetButton.setEnabled(false);
        saveButton.setEnabled(false);
        File pcm = new File(this.temporaryFilepath + ".pcm");
        pcm.delete();
    }

    private void saveRecording() {
        recordingStopped = false;
        resetButton.setEnabled(false);
        saveButton.setEnabled(false);
        saveWav();
    }

    private void saveWav() {
        String pcmFilepath = this.temporaryFilepath + ".pcm";
        String wacFilepath = this.temporaryFilepath + ".wav";

        File rawData = new File(pcmFilepath);
        File wavFile = new File(wacFilepath);
        try {
            rawToWave(rawData, wavFile);

            String newTitle = titleInput.getText().toString();
            String newName = nameInput.getText().toString() + " " + surnameInput.getText().toString();
            String newDate = getCurrentDate();
            String newWavPath = wacFilepath;

            RecordingData newRec = new RecordingData(newTitle, newName, newDate, newWavPath);

            rawData.delete();

            recordings.add(newRec);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> defaultList = new ArrayList<>();
        String jsonDefaultList = gson.toJson(defaultList);
        this.recordings = gson.fromJson(sharedPref.getString(getString(R.string.recirding_list_emblem),jsonDefaultList), recordingType);
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefEditor.clear();
        String jsonRecordingsList = gson.toJson(this.recordings);

        prefEditor.putString(getString(R.string.recirding_list_emblem), jsonRecordingsList);
        prefEditor.commit();
    }

    private String getNewFilePath() {
        String currentDate = getCurrentDateForPath();

        String filePath = this.getFilesDir().getAbsolutePath() + "/nota"
                + Integer.toString(recordings.size()) + "_" + currentDate;

        this.temporaryFileName ="nota" + Integer.toString(recordings.size()) + "_" + currentDate;
        return filePath;
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'godz' HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    private String getCurrentDateForPath() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    private void rawToWave(final File rawFile, final File waveFile) throws IOException {

        byte[] rawData = new byte[(int) rawFile.length()];
        DataInputStream input = null;
        try {
            input = new DataInputStream(new FileInputStream(rawFile));
            input.read(rawData);
        } finally {
            if (input != null) {
                input.close();
            }
        }

        DataOutputStream output = null;
        try {
            output = new DataOutputStream(new FileOutputStream(waveFile));
            // WAVE header
            // see http://ccrma.stanford.edu/courses/422/projects/WaveFormat/
            writeString(output, "RIFF"); // chunk id
            writeInt(output, 36 + rawData.length); // chunk size
            writeString(output, "WAVE"); // format
            writeString(output, "fmt "); // subchunk 1 id
            writeInt(output, 16); // subchunk 1 size
            writeShort(output, (short) 1); // audio format (1 = PCM)
            writeShort(output, (short) 1); // number of channels
            writeInt(output, 44100); // sample rate
            writeInt(output, RECORDER_SAMPLERATE * 2); // byte rate
            writeShort(output, (short) 2); // block align
            writeShort(output, (short) 16); // bits per sample
            writeString(output, "data"); // subchunk 2 id
            writeInt(output, rawData.length); // subchunk 2 size
            // Audio data (conversion big endian -> little endian)
            short[] shorts = new short[rawData.length / 2];
            ByteBuffer.wrap(rawData).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(shorts);
            ByteBuffer bytes = ByteBuffer.allocate(shorts.length * 2);
            for (short s : shorts) {
                bytes.putShort(s);
            }

            output.write(fullyReadFileToBytes(rawFile));
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    byte[] fullyReadFileToBytes(File f) throws IOException {
        int size = (int) f.length();
        byte bytes[] = new byte[size];
        byte tmpBuff[] = new byte[size];
        FileInputStream fis= new FileInputStream(f);
        try {

            int read = fis.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = fis.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        }  catch (IOException e){
            throw e;
        } finally {
            fis.close();
        }

        return bytes;
    }

    private void writeInt(final DataOutputStream output, final int value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
        output.write(value >> 16);
        output.write(value >> 24);
    }
    private void writeShort(final DataOutputStream output, final short value) throws IOException {
        output.write(value >> 0);
        output.write(value >> 8);
    }
    private void writeString(final DataOutputStream output, final String value) throws IOException {
        for (int i = 0; i < value.length(); i++) {
            output.write(value.charAt(i));
        }
    }
    //convert short to byte
    private byte[] short2byte(short[] sData) {
        int shortArrsize = sData.length;
        byte[] bytes = new byte[shortArrsize * 2];
        for (int i = 0; i < shortArrsize; i++) {
            bytes[i * 2] = (byte) (sData[i] & 0x00FF);
            bytes[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return bytes;
    }
    private void requestRecordAudioPermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Toast.makeText(this, "nie wiem", Toast.LENGTH_SHORT).show();
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
            }
        }
    }

    private void requestReadExternalStoragePermission() {
        //check API version, do nothing if API version < 23!
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion > android.os.Build.VERSION_CODES.LOLLIPOP){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Toast.makeText(this, "nie wiem", Toast.LENGTH_SHORT).show();
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}































