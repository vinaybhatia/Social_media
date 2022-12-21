package com.polaroid.universalapp;

import static com.polaroid.universalapp.AppConstant.REQUEST_CODE_FB_ALBUMS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ly.kite.socialmedia.common.Album;
import ly.kite.socialmedia.common.Constant;
import ly.kite.socialmedia.common.Photo;
import ly.kite.socialmedia.facebookphotopicker.FacebookAgent;
import ly.kite.socialmedia.facebookphotopicker.FacebookPhotoPickerActivity;
import ly.kite.socialmedia.instagramphotopicker.InstagramPhotoPicker;

public class SecondActivity extends AppCompatActivity implements FacebookAgent.IPhotosCallback {
    AppCompatButton btn_Facebook;
    private boolean hasMoreFbImage = false;
    private String socialMediaAlbumName = "";
    private String selectedAlbumName = "";
    private PhoneAlbum selectedAlbum;
    private ArrayList<Album> fbAlbumList;
    RecyclerView albumRV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_Facebook = findViewById(R.id.facebook);
        albumRV = findViewById(R.id.albumRV);

        btn_Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasMoreFbImage = false;
                String fbAccessToken = InstagramPhotoPicker.getFbAccessToken(SecondActivity.this);
                // addPaginationForGooglePhotos();
                //   if (NetworkUtils.isInternetAvailable(getActivity())) {
                if (fbAccessToken != null) {

                    //fbAlbumName = AppConstant.FACEBOOK;
                    socialMediaAlbumName = AppConstant.FACEBOOK;
                    selectedAlbumName = AppConstant.FACEBOOK;
                    selectedAlbum = new PhoneAlbum();
                    selectedAlbum.setId(98765);
//                 textViewAllPhotos.setText(selectedAlbumName);

                /*ArrayList<Album> fbAlbumList = (ArrayList<Album>) data.getSerializableExtra(Constant.FACEBOOK_ALBUMS);
                setFacebookAlbumAdater(fbAlbumList, false);*/
                    Intent intent = new Intent(SecondActivity.this, FacebookPhotoPickerActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_FB_ALBUMS);
                } else {
                    showFacebookLoginAlert();
                }
//                } else {
//                    Global.showDialog(getActivity(), getString(R.string.please_connect_to_internet));
//                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {


                case REQUEST_CODE_FB_ALBUMS: {
                    callFbCallOnActivityResultReponse(data);
                    break;


                }


                case Constant.REQUEST_GOOGLE_PHOTOS: {

                    break;
                }
            }
        } else {
            switch (requestCode) {

//                case REQUEST_CODE_FB_PHOTOS:
//
//                    break;
            }
        }
    }

    @Override
    public void facOnError(@Nullable Exception exception) {

    }

    @Override
    public void facOnCancel() {

    }

    @Override
    public void facOnPhotosSuccess(@Nullable ArrayList<Photo> photoList, boolean morePhotos, long id) {
        if (photoList != null) {

        }
        ArrayList<Gallery> photoUris = prepareFBGalleryList(photoList);
    }

    private ArrayList<Gallery> prepareFBGalleryList(List<Photo> imgList) {
        ArrayList<Gallery> galleryList = new ArrayList<>();
        int count = 0;
        if (!AppUtil.isCollectionEmpty(imgList)) {
            for (Photo photo : imgList) {
                Gallery gallery = new Gallery();
                if (!galleryList.contains(photo.getFullURL()))
                    gallery.setFilePath(photo.getFullURL());
                gallery.setImg_id(AppUtil.getUniqueId());
                gallery.setStaggeredStatus(false);
                gallery.setPosition(count);

                galleryList.add(gallery);
                count++;
            }
        }
        return galleryList;
    }

    private void showFacebookLoginAlert() {
        AlertDialog dialog = new AlertDialog.Builder(SecondActivity.this)
                .setMessage(getString(R.string.connect_to_your_facebook))
                .setPositiveButton(R.string.label_connect, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //socialMediaAlbumName = AppConstant.FACEBOOK;
                        selectedAlbumName = AppConstant.FACEBOOK;
                        Intent intent = new Intent(SecondActivity.this, FacebookPhotoPickerActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_FB_ALBUMS);

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
//                        bucketName = "All Photos";
//                        socialAlbumIdTosend = 0;
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }

    private void callFbCallOnActivityResultReponse(Intent data) {
        //   isFbAlbumShowing = true;
        fbAlbumList = (ArrayList<Album>) data.getSerializableExtra(Constant.FACEBOOK_ALBUMS);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        albumRV.setLayoutManager(gridLayoutManager);
        FacebookAlbumAdapter ratingAdapter = new FacebookAlbumAdapter(fbAlbumList,this);
        albumRV.setAdapter(ratingAdapter);


//        Copilot.getInstance().Report.logEvent(new ConnectAlbumEvent(AppConstant.FACEBOOK));
//        if (!SharePreference.getBoolean(getActivity(), AppConstant.IS_FB_LOGIN)) {
//            Copilot.getInstance().Report.logEvent(new UserEnrichmentEvent(AppConstant.FACEBOOK, getFbUserIdFromPreferences(), getFbUserNameFromPreferences()));
//            SharePreference.putBoolean(getActivity(), AppConstant.IS_FB_LOGIN, true);
//        }
//        if (!AppUtil.isCollectionEmpty(fbAlbumList)) {
//            setFacebookAlbumAdater(fbAlbumList, false, true);
//            SharePreference.putInt(getActivity(), AppConstant.FACEBOOK_PHOTOS_COUNT, fbAlbumList.size());
//        }


    }
}
