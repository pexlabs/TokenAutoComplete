package com.tokenautocomplete.model;


import android.graphics.drawable.Drawable;
import android.net.Uri;

public interface ChipInterface {
    Object getId();
    Uri getAvatarUri();
    Drawable getAvatarDrawable();
    String getEmailAddress();
    String getPhoneNumber();
    String getDisplayName();
}
