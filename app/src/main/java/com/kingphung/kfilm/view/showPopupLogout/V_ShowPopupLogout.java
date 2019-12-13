package com.kingphung.kfilm.view.showPopupLogout;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.kingphung.kfilm.R;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class V_ShowPopupLogout implements View.OnClickListener {
    private Context context;
    private V_I_ShowPopupLogout v_i_showPopupLogout;
    String userName, userImageUrl;

    //UI
    PopupWindow popupWindow;
    CircularImageView ivUserImage;
    TextView tvUserName;
    Button btStay;
    ImageButton btLeave;

    public V_ShowPopupLogout(Context context, V_I_ShowPopupLogout v_i_showPopupLogout, String userName, String userImageUrl){
        this.context = context;
        this.v_i_showPopupLogout = v_i_showPopupLogout;
        this.userName = userName;
        this.userImageUrl = userImageUrl;
    }

    public void show(){
        initUI();
        mapDataToUI();
        setOnClick();
    }

    private void initUI() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewLayoutPopupWindowDelete = inflater.inflate(R.layout.popupwindow_logout, null);
        popupWindow = new PopupWindow(
                viewLayoutPopupWindowDelete,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        ivUserImage = viewLayoutPopupWindowDelete.findViewById(R.id.ivUserImageLogout);
        tvUserName = viewLayoutPopupWindowDelete.findViewById(R.id.tvUserNameLogout);

        btStay = viewLayoutPopupWindowDelete.findViewById(R.id.btStay);
        btLeave = viewLayoutPopupWindowDelete.findViewById(R.id.btLeave);

        //show popup window
        popupWindow.showAtLocation(viewLayoutPopupWindowDelete, Gravity.CENTER,0,0);

        //set dim behind when popup window show up
        View container = popupWindow.getContentView().getRootView();
        if(container != null) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.5f;
            if (wm != null) {
                wm.updateViewLayout(container, p);
            }
        }
    }
    private void mapDataToUI() {
        Picasso.get().load(userImageUrl).into(ivUserImage);
        tvUserName.setText(userName);
    }
    private void setOnClick() {
        btStay.setOnClickListener(this);
        btLeave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btStay:
                handle_StayButtonClicked();
                break;
            case R.id.btLeave:
                handleLeaveButtonClicked();
                break;
        }
    }

    private void handleLeaveButtonClicked() {
        v_i_showPopupLogout.onCompleteSeleted(true);
        popupWindow.dismiss();
    }
    private void handle_StayButtonClicked() {
        popupWindow.dismiss();
    }

}
