package com.all_the_best.knock_knock.infant.talk.view

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaRecorder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.all_the_best.knock_knock.R
import com.all_the_best.knock_knock.infant.cookie.view.InfantGetCookiePopupActivity
import com.all_the_best.knock_knock.infant.home.view.InfantHomeActivity
import com.all_the_best.knock_knock.parent.base.view.LoginActivity
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_infant_home.*
import kotlinx.android.synthetic.main.activity_infant_talk_start.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class InfantTalkStartActivity : AppCompatActivity() {

    private var bgSelect: Int = 1
    private var chSelect: Int = 0
    private var cookieCount: Int = 5
    private lateinit var fileName: String
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioUri:Uri
    private var state = false
    private val firebaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    //private val storageReference: StorageReference = firebaseStorage.getReferenceFromUrl("gs://knockknock-29f42.appspot.com");

    //gs://knockknock-29f42.appspot.com/audioFile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infant_talk_start)
        getToday()
        setOnBtnRecordClick()
        setOnBtnDownLoadRecordClick()
        //setOnBtnAudioUploadClick()
        bgSelect = intent.getIntExtra("bgSelect",1)
        chSelect = intent.getIntExtra("chSelect",0)
        cookieCount = intent.getIntExtra("cookieCount",5)

        setSelectCharacter()
        window.statusBarColor = Color.parseColor("#FCC364")

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_TIME

        when(current.format(formatter)){
            in "08:00:000".."13:59:999" -> {
                infant_talk_start.setBackgroundResource(R.drawable.img_infant_room_morning_bg)
            }
            in "14:00:000".."19:59:999" -> {
                infant_talk_start.setBackgroundResource(R.drawable.img_infant_room_after_bg)
            }
            in "20:00:00".."23:59:999" -> {
                infant_talk_start.setBackgroundResource(R.drawable.img_infant_room_night_bg)
            }
            !in "08:00:00".."23:59:999" -> {
                infant_talk_start.setBackgroundResource(R.drawable.img_infant_room_night_bg)
            }
        }

        val intent1 = Intent(this, InfantHomeActivity::class.java)
        infant_icon_out.setOnClickListener{
            // 쿠키 받는 팝업
            val cookiePopUp = Dialog(this)
            cookiePopUp?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            cookiePopUp.setContentView(R.layout.activity_infant_get_cookie_popup)
            cookiePopUp.show()
            cookieCount += 1
            intent1.putExtra("bgSelect", bgSelect)
            intent1.putExtra("chSelect",chSelect)
            intent1.putExtra("cookieCount",cookieCount)
            Handler(Looper.getMainLooper()).postDelayed ({
                startActivity(intent1)
                finish()
            }, 2000)
            overridePendingTransition(0, 0)
        }
    }
    private fun setSelectCharacter(){
        when(chSelect){
            0 -> talk_start_char_dam.setImageResource(R.drawable.img_char_dam)
            1 -> talk_start_char_dam.setImageResource(R.drawable.img_char_knock)
            2 -> talk_start_char_dam.setImageResource(R.drawable.img_char_timi)
        }
    }

    private fun getToday() {
        val currentTime: Date = Calendar.getInstance().getTime()
        val simpleDate = SimpleDateFormat("yyyy-MM-dd")
        val date = simpleDate.format(currentTime)
        fileName = date.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                1 -> {
                    val selectedAudio: Uri? = data.data
                    uploadAudioUri(selectedAudio!!)
                }
            }
        }
    }

    private fun setOnBtnRecordClick() {
        startRecordBtn.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //Permission is not granted
                val permissions = arrayOf(
                    android.Manifest.permission.RECORD_AUDIO,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                ActivityCompat.requestPermissions(this, permissions, 0)
            } else {
                //Log.d("record", "start")
                startRecording()
            }
            setOnBtnRecordStopClick()
        }
    }

    private fun setOnBtnRecordStopClick() {
        stopRecordBtn.setOnClickListener {
            stopRecording()
        }
    }

    private fun setOnBtnDownLoadRecordClick(){
        btnDownload.setOnClickListener{
           // downLoadRecording()
        }
    }


    @Suppress("DEPRECATION")
    private fun startRecording() {
        //config and create MediaRecorder Object
        val values = ContentValues(10)
        values.put(MediaStore.MediaColumns.TITLE, "Recorded")
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, fileName)
        values.put(MediaStore.Audio.Media.IS_RINGTONE, 1)
        values.put(MediaStore.Audio.Media.IS_MUSIC, 1)
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")
        values.put(MediaStore.Audio.Media.DATA, fileName)
        values.put(MediaStore.Audio.Media.RELATIVE_PATH, "Music/Recordings/");

        audioUri = contentResolver.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values)!!
        val file = audioUri?.let { getContentResolver().openFileDescriptor(it, "w") };

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource((MediaRecorder.AudioSource.MIC))
        mediaRecorder?.setOutputFormat((MediaRecorder.OutputFormat.MPEG_4))
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        if (file != null) {
            mediaRecorder?.setOutputFile(file.getFileDescriptor())
        }

        try {
            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "레코딩 시작되었습니다.", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (state) {
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            mediaRecorder?.release()
            state = false
            Toast.makeText(this, "중지 되었습니다. 업로드 되었습니다.", Toast.LENGTH_SHORT).show()
            uploadAudioUri(audioUri)

        } else {
            Toast.makeText(this, "레코딩 상태가 아닙니다.", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun setOnBtnAudioUploadClick() {
//        talk_start_char_dam.setOnClickListener {
//            val audioIntent =
//                Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
//            startActivityForResult(audioIntent, 1)
//        }
//    }

    private fun uploadAudioUri(file: Uri) {
        //val file = Uri.fromFile(file)
        firebaseStorage.reference.child("audioFile").child(fileName + ".mp4")
            .putFile(file).addOnCompleteListener {
                if (it.isSuccessful) {
                    //Log.d("storage", "upload success")
                }
            }
    }

//    private fun downLoadRecording() {
//        // Create a storage reference from our app
//        val storageRef = firebaseStorage.reference
//
//        // Create a reference with an initial file path and name
//        val pathReference = storageRef.child("audioFile/2021-03-29.mp4")
//
//        // Create a reference to a file from a Google Cloud Storage URI
//        val gsReference = firebaseStorage.getReferenceFromUrl("gs://knockknock-29f42.appspot.com/audioFile/2021-03-29.mp4")
//        var islandRef = storageRef.child("audioFile/2021-03-29.mp4")
//
//        val ONE_MEGABYTE: Long = 5246 * 5246
//        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
//            // Data for "images/island.jpg" is returned, use this as needed
//            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
//            // Handle any errors
//            Toast.makeText(this, "다운로드 실패", Toast.LENGTH_SHORT).show()
//        }
//
//
////        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
////        val storageDir: File = Environment
////            .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
////        val localFile = File.createTempFile("Record_${timeStamp}_", ".mp4", storageDir)
////        val photoRef = firebaseStorage.reference.child("audioFile/${fileName}.mp4")
////        photoRef.getFile(localFile).addOnSuccessListener {
////            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show()
////            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
////                val f = File(localFile.absolutePath)
////                mediaScanIntent.data = Uri.fromFile(f)
////                sendBroadcast(mediaScanIntent)
////            }
////        }
//    }
}