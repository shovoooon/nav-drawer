package com.shovon.navigationdrawer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.media.MediaPlayer;
import android.os.Build.VERSION;
import android.os.CountDownTimer;
import android.provider.Settings.System;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.gomido.FragmentPack.FragmentClass;
import com.gomido.FragmentPack.FragmentFavorite;
import com.gomido.Utils.AdmobAds;
import com.gomido.Utils.AppMet;
import com.gomido.Utils.PrefMemory;
import com.gomido.Utils.RingtonesManager;
import com.gomido.Utils.ShareSound;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    public static int adCounter;
    public static CountDownTimer mCountDownTimer;
    public static MediaPlayer mediaPlayer;
    public static int static_position;
    String TAG_NAME;
    Boolean bolCountDown = Boolean.valueOf(false);
    Context context;

    /* renamed from: i */
    int f43i = 0;
    int[] sound;
    String[] soundArray;

    public class ViewHolder {
        ImageView img_circle;
        public ImageView img_fav;
        ImageView img_pplay;
        ImageView img_setringtone;
        ImageView img_share;
        ProgressBar progressBar;
        TextView tvTitle;

        ViewHolder(View view) {
            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            this.img_fav = (ImageView) view.findViewById(R.id.img_fav);
            this.img_share = (ImageView) view.findViewById(R.id.img_share);
            this.img_setringtone = (ImageView) view.findViewById(R.id.img_setringtone);
            this.img_pplay = (ImageView) view.findViewById(R.id.img_pplay);
            this.img_circle = (ImageView) view.findViewById(R.id.img_circle);
        }
    }

    public CustomAdapter(Context context2, String[] soundArray2, int[] sound2, String TAG_NAME2) {
        super(context2, R.layout.mylistview, R.id.tvTitle, soundArray2);
        this.context = context2;
        this.soundArray = soundArray2;
        this.sound = sound2;
        this.TAG_NAME = TAG_NAME2;
    }

    @SuppressLint("WrongConstant")
    @NonNull
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = null;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.mylistview, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTitle.setText(this.soundArray[position]);
        viewHolder.progressBar.getProgressDrawable().setColorFilter(this.context.getResources().getColor(C0489R.color.progres_color), Mode.SRC_IN);
        String str = "a";
        if (this.TAG_NAME.equals("f")) {
            if (FragmentFavorite.soundNick[position].contains(str)) {
                viewHolder.img_circle.setImageDrawable(this.context.getResources().getDrawable(C0489R.C0490drawable.f44a1));
            }
            viewHolder.img_fav.setImageDrawable(this.context.getResources().getDrawable(C0489R.C0490drawable.ico_fav_ok));
        } else if (this.TAG_NAME.equals(str)) {
            viewHolder.img_circle.setImageDrawable(this.context.getResources().getDrawable(C0489R.C0490drawable.f44a1));
            PrefMemory.FavSetterAdapter(this.context, this.TAG_NAME, position, viewHolder, this.sound);
        }
        final ViewHolder finalViewHolder = viewHolder;
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                int i = CustomAdapter.adCounter;
                Boolean valueOf = Boolean.valueOf(false);
                if (i >= 3) {
                    CustomAdapter.adCounter = 0;
                    if (CustomAdapter.this.bolCountDown.booleanValue()) {
                        CustomAdapter.mCountDownTimer.cancel();
                        CustomAdapter customAdapter = CustomAdapter.this;
                        customAdapter.f43i = 0;
                        customAdapter.bolCountDown = valueOf;
                        return;
                    }
                    return;
                }
                finalViewHolder.progressBar.setProgress(0);
                if (CustomAdapter.mediaPlayer != null) {
                    CustomAdapter.mediaPlayer.stop();
                    CustomAdapter.mediaPlayer.release();
                }
                CustomAdapter.mediaPlayer = MediaPlayer.create(CustomAdapter.this.context, CustomAdapter.this.sound[position]);
                int intMedia = CustomAdapter.mediaPlayer.getDuration() / 100;
                if (CustomAdapter.this.bolCountDown.booleanValue()) {
                    CustomAdapter.mCountDownTimer.cancel();
                    CustomAdapter customAdapter2 = CustomAdapter.this;
                    customAdapter2.f43i = 0;
                    customAdapter2.bolCountDown = valueOf;
                }
                finalViewHolder.img_pplay.setImageDrawable(CustomAdapter.this.context.getResources().getDrawable(C0489R.C0490drawable.ic_action_pause_circle_outline));
                CustomAdapter.this.CountDownTimer(finalViewHolder.progressBar, intMedia, finalViewHolder);
                CustomAdapter.adCounter++;
                CustomAdapter.mediaPlayer.start();
            }
        });
        viewHolder.img_setringtone.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final String share;
                if (!CustomAdapter.WriteSettingsCheck(MainActivity.pub_activity)) {
                    AppMet.WritingSettingsDialog(MainActivity.pub_activity);
                } else if (VERSION.SDK_INT < 23) {
                    FragmentManager fragmentManager = MainActivity.fragmentManager;
                    MenuParams menuParams = new MenuParams();
                    menuParams.setActionBarSize(180);
                    menuParams.setMenuObjects(CustomAdapter.this.getMenuObjects());
                    menuParams.setClosableOutside(true);
                    ContextMenuDialogFragment contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
                    contextMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                    StringBuilder sb = new StringBuilder();
                    sb.append(CustomAdapter.this.TAG_NAME);
                    sb.append(String.valueOf(position));
                    final String share2 = sb.toString();
                    contextMenuDialogFragment.setItemClickListener(new OnMenuItemClickListener() {
                        public void onMenuItemClick(View view, int i) {
                            if (i == 0) {
                                try {
                                    RingtonesManager.RingtoneManager(share2, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, false, true, false, 3);
                                } catch (Exception e) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            } else if (i == 1) {
                                try {
                                    RingtonesManager.RingtoneManager(share2, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, false, false, true, 2);
                                } catch (Exception e2) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            } else if (i == 2) {
                                try {
                                    RingtonesManager.RingtoneManager(share2, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, true, false, false, 1);
                                    AdmobAds.ShowInterstitialAds();
                                } catch (Exception e3) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            }
                        }
                    });
                } else if (ActivityCompat.checkSelfPermission(CustomAdapter.this.context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ActivityCompat.checkSelfPermission(CustomAdapter.this.context, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                    FragmentManager fragmentManager2 = MainActivity.fragmentManager;
                    MenuParams menuParams2 = new MenuParams();
                    menuParams2.setActionBarSize(180);
                    menuParams2.setMenuObjects(CustomAdapter.this.getMenuObjects());
                    menuParams2.setClosableOutside(true);
                    ContextMenuDialogFragment contextMenuDialogFragment2 = ContextMenuDialogFragment.newInstance(menuParams2);
                    contextMenuDialogFragment2.show(fragmentManager2, ContextMenuDialogFragment.TAG);
                    if (CustomAdapter.this.TAG_NAME.equals("f")) {
                        share = FragmentFavorite.soundNick[CustomAdapter.static_position];
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(CustomAdapter.this.TAG_NAME);
                        sb2.append(String.valueOf(position));
                        share = sb2.toString();
                    }
                    contextMenuDialogFragment2.setItemClickListener(new OnMenuItemClickListener() {
                        public void onMenuItemClick(View view, int i) {
                            if (i == 0) {
                                try {
                                    RingtonesManager.RingtoneManager(share, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, false, true, false, 3);
                                } catch (Exception e) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            } else if (i == 1) {
                                try {
                                    RingtonesManager.RingtoneManager(share, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, false, false, true, 2);
                                } catch (Exception e2) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            } else if (i == 2) {
                                try {
                                    RingtonesManager.RingtoneManager(share, position, CustomAdapter.this.context, CustomAdapter.this.soundArray, true, false, false, 1);
                                    AdmobAds.ShowInterstitialAds();
                                } catch (Exception e3) {
                                    Toast.makeText(CustomAdapter.this.context, CustomAdapter.this.context.getResources().getString(C0489R.string.oops), 1).show();
                                }
                            }
                        }
                    });
                } else {
                    new MainActivity().CheckPermission(MainActivity.pub_activity);
                }
            }
        });
        viewHolder.img_share.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CustomAdapter.this.bolCountDown.booleanValue()) {
                    CustomAdapter.mCountDownTimer.cancel();
                    CustomAdapter customAdapter = CustomAdapter.this;
                    customAdapter.f43i = 0;
                    customAdapter.bolCountDown = Boolean.valueOf(false);
                }
                CustomAdapter.static_position = position;
                String str = "f";
                if (VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(CustomAdapter.this.context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0 || ActivityCompat.checkSelfPermission(CustomAdapter.this.context, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                        new MainActivity().CheckPermission(MainActivity.pub_activity);
                    } else if (CustomAdapter.this.TAG_NAME.equals(str)) {
                        ShareSound.ShareSound(CustomAdapter.this.context, FragmentFavorite.soundNick[CustomAdapter.static_position]);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(CustomAdapter.this.TAG_NAME);
                        sb.append(String.valueOf(position));
                        ShareSound.ShareSound(CustomAdapter.this.context, sb.toString());
                    }
                } else if (CustomAdapter.this.TAG_NAME.equals(str)) {
                    ShareSound.ShareSound(CustomAdapter.this.context, FragmentFavorite.soundNick[CustomAdapter.static_position]);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(CustomAdapter.this.TAG_NAME);
                    sb2.append(String.valueOf(position));
                    ShareSound.ShareSound(CustomAdapter.this.context, sb2.toString());
                }
            }
        });
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.img_fav.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (CustomAdapter.this.TAG_NAME.equals("f")) {
                    int parseInt = Integer.parseInt(String.valueOf(CustomAdapter.this.sound[position]));
                    PrefMemory.RemovePreferences(CustomAdapter.this.context, FragmentFavorite.soundNick[position]);
                    finalViewHolder2.img_fav.setImageDrawable(CustomAdapter.this.context.getResources().getDrawable(C0489R.C0490drawable.ico_fav));
                    String str = "arraySize";
                    PrefMemory.WritePrefernces(CustomAdapter.this.context, str, PrefMemory.ReadArraySize(CustomAdapter.this.context, str) - 1);
                    FragmentClass.FragmentFavorite(MainActivity.fragmentManager);
                    return;
                }
                PrefMemory.FavSetterButton(CustomAdapter.this.context, CustomAdapter.this.TAG_NAME, position, finalViewHolder2, CustomAdapter.this.sound);
            }
        });
        return view;
    }

    public void CountDownTimer(ProgressBar progressBar, int intMedia, ViewHolder viewHolder) {
        progressBar.setProgress(this.f43i);
        final ProgressBar progressBar2 = progressBar;
        final ViewHolder viewHolder2 = viewHolder;
        C04885 r1 = new CountDownTimer((long) (intMedia * 100), (long) intMedia) {
            public void onTick(long millisUntilFinished) {
                CustomAdapter.this.f43i++;
                progressBar2.setProgress(CustomAdapter.this.f43i);
                CustomAdapter.this.bolCountDown = Boolean.valueOf(true);
            }

            public void onFinish() {
                CustomAdapter.this.f43i = 0;
                progressBar2.setProgress(100);
                viewHolder2.img_pplay.setImageDrawable(CustomAdapter.this.context.getResources().getDrawable(C0489R.C0490drawable.ic_action_play_circle_outline));
                CustomAdapter.this.bolCountDown = Boolean.valueOf(false);
            }
        };
        mCountDownTimer = r1;
        mCountDownTimer.start();
    }

    /* access modifiers changed from: private */
    public List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject ringtone = new MenuObject(this.context.getResources().getString(C0489R.string.set_ringtone));
        ringtone.setResource(C0489R.C0490drawable.ic_action_phonelink_ring);
        ringtone.setBgColor(this.context.getResources().getColor(C0489R.color.colorPrimary));
        ringtone.setDividerColor(C0489R.color.white);
        MenuObject notify = new MenuObject(this.context.getResources().getString(C0489R.string.set_as_notif));
        notify.setBgColor(this.context.getResources().getColor(C0489R.color.colorPrimary));
        notify.setResource(C0489R.C0490drawable.ic_action_notifications_active);
        notify.setDividerColor(C0489R.color.white);
        MenuObject alarmtone = new MenuObject(this.context.getResources().getString(C0489R.string.set_alarm));
        alarmtone.setBgColor(this.context.getResources().getColor(C0489R.color.colorPrimary));
        alarmtone.setResource(C0489R.C0490drawable.ic_action_alarm);
        alarmtone.setDividerColor(C0489R.color.white);
        menuObjects.add(alarmtone);
        menuObjects.add(notify);
        menuObjects.add(ringtone);
        return menuObjects;
    }

    public static boolean WriteSettingsCheck(Activity context2) {
        if (VERSION.SDK_INT >= 23) {
            return System.canWrite(context2);
        }
        return ContextCompat.checkSelfPermission(context2, "android.permission.WRITE_SETTINGS") == 0;
    }
}
