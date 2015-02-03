package com.yx.zhihu.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.yx.zhihu.APP;
import com.yx.zhihu.R;
import com.yx.zhihu.view.photoview.PhotoView;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class ImageActivity extends BaseActivity {
	private PhotoView iv_photo;
	private SmoothProgressBar spb;
	private MediaScannerConnection msc = null;
	private String insertImage;
	protected Bitmap loadImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		initView();
		initData();
		
	}

	private void initView() {
		iv_photo = (PhotoView) findViewById(R.id.iv_photo);
		spb = (SmoothProgressBar) findViewById(R.id.spb);
		
	}

	private void initData() {
		Intent intent = getIntent();
		if(intent != null){
			String imgUrl = intent.getStringExtra("imageUrl");
			ImageLoader.getInstance().loadImage(imgUrl, APP.getApp().getDefalutOptions(), new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					
				}
				
				@Override
				public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
					
				}
				
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					loadImage = loadedImage;
					iv_photo.setImageBitmap(loadedImage);
					spb.setVisibility(View.GONE);
				}
				
				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					
				}
			});
		}
	}
	/**
	 * 将图片保存至系统图库
	 * 
	 * @param context
	 * @param imagePath
	 * @return
	 */
	private boolean saveImage2Gallery(Bitmap bmp) {
		
		boolean result = true;
		
	    try {
	    	insertImage = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", "");   
	        // 通知图库更新 4.4广播出问题
//		    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory() + insertImage)));
		    //scanner 保险
		    msc = new MediaScannerConnection(this, new MediaScannerConnectionClient() {
				public void onMediaScannerConnected() {
				   msc.scanFile(insertImage, "image/jpeg");
				}
				public void onScanCompleted(String path, Uri uri) {

				  msc.disconnect();
				}
			}); 
	    } catch (Exception e) {
	        e.printStackTrace();
	        result = false;
	    }
	    
	    return result;
	} 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		return super.onCreateOptionsMenu(menu);
	}
}
