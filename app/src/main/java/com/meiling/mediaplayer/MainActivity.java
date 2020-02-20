package com.meiling.mediaplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SurfaceView preview;
    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSurface();
    }

    private void initView() {
        preview = findViewById(R.id.preview);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        //todo 刚进来数据尚未缓冲好，是不能够进行播放的
//        start.setEnabled(false);
        stop.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    private MediaPlayer mediaPlayer;
    private SurfaceHolder mHolder;

    private int videoTimes = 0;
    private int videoWidth;
    private int videoHeight;

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startPlayer();//需要等Surfacec创建成功后，再进行调用比较好，否则将会出现The surface has been released的异常，因为surface尚未被创建，就调用的mediaplayer来显示对应的图像数据
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null) {
                return;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private void initSurface() {
        mHolder = preview.getHolder();
        mHolder.addCallback(mCallback);
        // setType必须设置，要不出错.
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void startPlayer() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
            }
            mediaPlayer.setDataSource("https://api.udfs.one:15009/ipfs/QmUTQacrTeCorEGPeAaUkQUTnd5DdeWbLos7TF9T1K3bmw?token=ucopyrights:IV_WpaHjXydwEhp1sz2bCxUGcHA=:eyJ2ZXIiOjAsImV4cGlyZWQiOiAxNTgyMTkwNTg3LCJleHQiOnt9fQ==");//todo 放网络媒体文件的URL
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDisplay(mHolder);//todo 对于视频可以调用这个来显示对应的画面
//            mediaPlayer.prepare();//todo 这个同步方法会造成阻塞的情况

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {//todo 准备完成，可以进行播放
                    Log.e(Ulog.TAG, "准备完成");
                    Toast.makeText(MainActivity.this, "准备完成", Toast.LENGTH_SHORT).show();
                    start.setEnabled(true);
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {//todo 播放完成
                    Log.e(Ulog.TAG, "播放完");
                    Toast.makeText(MainActivity.this, "播放完", Toast.LENGTH_SHORT).show();
                    stop();
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {// todo 播放出错，如有必要，则进行相关的错误提示
//                    MediaPlayer.MEDIA_ERROR_IO
                    Log.e(Ulog.TAG, "出错---what>" + what + "<---extra>" + extra + "<---");
                    Toast.makeText(MainActivity.this, "出错", Toast.LENGTH_SHORT).show();
                    stop();
                    return false;
                }
            });

            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {//todo 监听缓冲的状态
                    Log.e(Ulog.TAG, String.format("缓冲百分比？%d", percent));
                }
            });

            mediaPlayer.prepareAsync();
            Log.e(Ulog.TAG, "异步准备");
            /**
             * todo 下面这三个调用对于网络资源不适用（仅适合本地资源）
             */
//            videoTimes = mediaPlayer.getDuration();
//            videoWidth = mediaPlayer.getVideoWidth();
//            videoHeight = mediaPlayer.getVideoHeight();
//            Log.e(Ulog.TAG, "播放时间(毫秒):" + videoTimes);
//            Log.e(Ulog.TAG, "播放视频宽度:" + videoWidth);
//            Log.e(Ulog.TAG, "播放视频高度:" + videoHeight);
            /**
             * todo 针对网络媒体文件，可以使用MediaPlayer进行播放，似乎无法获取到文件的总长度（时间长度）
             */
            /*
02-20 15:44:29.533 27847-27847/? E/_AndroidRuntime: 异步准备
02-20 15:44:32.701 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？100
02-20 15:44:32.702 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 准备完成
02-20 15:44:41.422 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？19
02-20 15:44:42.422 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？25
02-20 15:44:43.424 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？28
02-20 15:44:44.427 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？32
02-20 15:44:45.427 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？37
02-20 15:44:46.428 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？41
02-20 15:44:46.866 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？43
02-20 15:44:47.859 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？38
02-20 15:44:48.859 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？38
02-20 15:44:49.859 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？38
02-20 15:44:50.860 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？43
02-20 15:44:51.859 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？48
02-20 15:44:52.860 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？52
02-20 15:44:53.861 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？56
02-20 15:44:54.861 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？60
02-20 15:44:55.202 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？78
02-20 15:44:56.192 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？76
02-20 15:44:57.192 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？75
02-20 15:44:58.192 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？75
02-20 15:44:59.194 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？78
02-20 15:45:00.195 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？93
02-20 15:45:01.195 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？93
02-20 15:45:02.195 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？91
02-20 15:45:03.197 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？91
02-20 15:45:04.196 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？100
02-20 15:45:05.195 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？100
02-20 15:45:06.197 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 缓冲百分比？100
02-20 15:45:12.882 27847-27847/com.meiling.mediaplayer E/_AndroidRuntime: 播放完
             */
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Ulog.TAG, "出错：" + e.getMessage());
        }
    }

    private void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            start.setEnabled(false);
            stop.setEnabled(true);
        }
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            start.setEnabled(true);
            stop.setEnabled(false);
        }
    }
}
